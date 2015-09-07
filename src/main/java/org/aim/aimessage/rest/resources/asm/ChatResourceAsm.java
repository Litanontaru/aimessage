package org.aim.aimessage.rest.resources.asm;

import org.aim.aimessage.core.model.Chat;
import org.aim.aimessage.rest.mvc.ChatController;
import org.aim.aimessage.rest.resources.ChatResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ChatResourceAsm extends ResourceAssemblerSupport<Chat, ChatResource> {
    public ChatResourceAsm() {
        super(ChatController.class, ChatResource.class);
    }

    @Override
    public ChatResource toResource(Chat chat) {
        ChatResource res = new ChatResource();
        res.setName(chat.getName());
        res.setAccounts(chat.getAccounts());

        res.add(linkTo(methodOn(ChatController.class).find(chat.getId())).withSelfRel());
        res.add(linkTo(methodOn(ChatController.class).remove(chat.getId())).withRel("rm"));
        return res;
    }
}
