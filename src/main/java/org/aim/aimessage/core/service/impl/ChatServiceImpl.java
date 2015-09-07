package org.aim.aimessage.core.service.impl;

import org.aim.aimessage.core.model.Chat;
import org.aim.aimessage.core.repository.ChatRepo;
import org.aim.aimessage.core.repository.SequenceRepo;
import org.aim.aimessage.core.service.ChatService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    private final SequenceRepo sequenceRepo;
    private final ChatRepo chatRepo;

    @Inject
    public ChatServiceImpl(SequenceRepo sequenceRepo, ChatRepo chatRepo) {
        this.sequenceRepo = sequenceRepo;
        this.chatRepo = chatRepo;
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
            List<Long> result = new ArrayList<Long>(1);
            result.add(accountId);
            return result;
        } else if (!accounts.contains(accountId)) {
            accounts.add(accountId);
        }
        return accounts;
    }
}
