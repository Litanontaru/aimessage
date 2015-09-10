package org.aim.aimessage.core.service.impl;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.model.Chat;
import org.aim.aimessage.core.model.ChatEntry;
import org.aim.aimessage.core.repository.AccountRepo;
import org.aim.aimessage.core.repository.ChatEntryRepo;
import org.aim.aimessage.core.repository.ChatRepo;
import org.aim.aimessage.core.repository.SequenceRepo;
import org.aim.aimessage.core.service.ChatService;
import org.aim.aimessage.core.service.util.ChatEntryVO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements ChatService {
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
    public List<ChatEntryVO> getAll(Long chatId) {
        List<ChatEntry> entries = chatEntryRepo.getAll(chatId);
        List<ChatEntryVO> result = new ArrayList<>(entries.size());
        Map<Long, String> accountNameMapring = new HashMap<>();
        for (ChatEntry entry : entries) {
            Long accountId = entry.getAccountId();
            String name;
            if (accountNameMapring.containsKey(accountId)) {
                name = accountNameMapring.get(accountId);
            } else {
                Account account = accountRepo.get(accountId);
                name = account == null ? "unknown" : account.getName();
                accountNameMapring.put(accountId, name);
            }
            result.add(new ChatEntryVO(name, entry.getUpdated(), entry.getDt(), entry.getPhrase()));
        }
        return result;
    }
}
