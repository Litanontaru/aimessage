package org.aim.aimessage.backend.rest.resources.asm;

import org.aim.aimessage.backend.rest.ChatController;
import org.aim.aimessage.backend.rest.resources.ChatListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class ChatListResourceAsm extends ResourceAssemblerSupport<ChatListResource.ChatList, ChatListResource> {
    public ChatListResourceAsm() {
        super(ChatController.class, ChatListResource.class);
    }

    @Override
    public ChatListResource toResource(ChatListResource.ChatList chatList) {
        ChatListResource result = new ChatListResource();
        result.setChats(new ChatResourceAsm().toResources(chatList.getChats()));
        return result;
    }
}
