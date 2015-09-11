package org.aim.aimessage.core.service.impl;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.model.Chat;
import org.aim.aimessage.core.model.ChatEntry;
import org.aim.aimessage.core.repository.AccountRepo;
import org.aim.aimessage.core.repository.ChatEntryRepo;
import org.aim.aimessage.core.repository.ChatRepo;
import org.aim.aimessage.core.repository.SequenceRepo;
import org.aim.aimessage.core.service.ChatService;
import org.aim.aimessage.core.service.util.SpeakMessage;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

@Service
public class ChatServiceImpl implements ChatService {
    private static final String UNKNOWN_USER = "anonymous";

    private final SequenceRepo sequenceRepo;
    private final AccountRepo accountRepo;
    private final ChatRepo chatRepo;
    private final ChatEntryRepo chatEntryRepo;

    @Inject
    public ChatServiceImpl(SequenceRepo sequenceRepo, AccountRepo accountRepo, ChatRepo chatRepo, ChatEntryRepo chatEntryRepo) {
        this.sequenceRepo = sequenceRepo;
        this.accountRepo = accountRepo;
        this.chatRepo = chatRepo;
        this.chatEntryRepo = chatEntryRepo;
    }

    @Override
    public List<Chat> find4Account(Long accountId) {
        return chatRepo.find(accountId);
    }

    @Override
    public Chat find(Long chatId) {
        return chatRepo.get(chatId);
    }

    @Override
    public void create(Chat chat, Long accountId) {
        chat.setId(sequenceRepo.getNextSequenceId(Chat.COLLECTION_NAME));
        List<Long> accounts = chat.getAccounts();
        chat.setAccounts(addIfNotContains(accountId, accounts));
        chatRepo.save(chat);
    }

    private List<Long> addIfNotContains(Long accountId, List<Long> accounts) {
        if (accounts == null) {
            List<Long> result = new ArrayList<>(1);
            result.add(accountId);
            return result;
        } else if (!accounts.contains(accountId)) {
            accounts.add(accountId);
        }
        return accounts;
    }

    @Override
    public Chat remove(Long id) {
        return chatRepo.remove(id);
    }

    @Override
    public List<SpeakMessage> getHistory(Long chatId) {
        List<ChatEntry> entries = chatEntryRepo.getAll(chatId);
        List<SpeakMessage> result = new ArrayList<>(entries.size());
        Map<Long, String> accountNameMapring = new HashMap<>();
        for (ChatEntry entry : entries) {
            Long accountId = entry.getAccountId();
            String name;
            if (accountNameMapring.containsKey(accountId)) {
                name = accountNameMapring.get(accountId);
            } else {
                Account account = accountRepo.get(accountId);
                name = account == null ? UNKNOWN_USER : account.getName();
                accountNameMapring.put(accountId, name);
            }
            result.add(new SpeakMessage(name, entry.getUpdated(), entry.getDt(), entry.getPhrase()));
        }
        return result;
    }

    @Override
    public SpeakMessage newMessage(Long chatId, String accountLogin, String phrase) {
        Date dt = new Date();
        Account account = accountRepo.find(accountLogin);
        Chat chat = chatRepo.get(chatId);
        if (chat == null) {
            //todo add logging
        } else {
            ChatEntry entry = new ChatEntry();
            entry.setChatId(chat.getId());
            if (account != null) {
                entry.setAccountId(account.getId());
            }
            entry.setUpdated(false);
            entry.setDt(dt);
            entry.setPhrase(phrase);
            chatEntryRepo.save(entry);
        }
        return new SpeakMessage(account == null ? UNKNOWN_USER : account.getName(), false, dt, phrase);
    }
}
