package org.aim.aimessage.core.service;

import org.aim.aimessage.core.model.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> find4Account(Long accountId);
    Chat find(Long chatId);
    void create(Chat chat, Long accountId);
}
