package org.aim.aimessage.core.service.impl;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.model.Chat;
import org.aim.aimessage.core.repository.AccountRepo;
import org.aim.aimessage.core.repository.ChatRepo;
import org.aim.aimessage.core.repository.SequenceRepo;
import org.aim.aimessage.core.service.ChatService;
import org.aim.aimessage.core.service.exception.AccountExistsException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
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
    public void create(Chat chat) {
        chat.setId(sequenceRepo.getNextSequenceId(Chat.COLLECTION_NAME));
        chatRepo.save(chat);
    }
}
