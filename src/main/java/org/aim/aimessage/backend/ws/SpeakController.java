package org.aim.aimessage.backend.ws;

import org.aim.aimessage.core.service.ChatService;
import org.aim.aimessage.core.service.util.SpeakMessage;
import org.aim.aimessage.core.service.util.SpeakMessageList;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import java.security.Principal;
import java.util.List;

@Controller
public class SpeakController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    @Inject
    public SpeakController(SimpMessageSendingOperations messagingTemplate, ChatService chatService) {
        this.messagingTemplate = messagingTemplate;
        this.chatService = chatService;
    }

    @MessageMapping("/history/{chatId}")
    public void history(@DestinationVariable("chatId") String chatId, Principal principal) {
        List<SpeakMessage> history = chatService.getAll(Long.parseLong(chatId));
        messagingTemplate.convertAndSendToUser(
                principal.getName(),
                "/queue/history/" + chatId,
                new SpeakMessageList(history)
        );
    }
}
