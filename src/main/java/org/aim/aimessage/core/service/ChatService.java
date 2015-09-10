package org.aim.aimessage.core.service;

import org.aim.aimessage.core.model.Chat;
import org.aim.aimessage.core.model.ChatEntry;
import org.aim.aimessage.core.service.util.ChatEntryVO;

import java.util.List;

public interface ChatService {
    List<Chat> find4Account(Long accountId);
    Chat find(Long chatId);
    void create(Chat chat, Long accountId);
    Chat remove(Long id);

    List<ChatEntryVO> getAll(Long chatId);
}
