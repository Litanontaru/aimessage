package org.aim.aimessage.rest.resources.asm;

import org.aim.aimessage.rest.mvc.ChatController;
import org.aim.aimessage.rest.resources.ChatListResource;
import org.aim.aimessage.rest.resources.ChatResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

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
