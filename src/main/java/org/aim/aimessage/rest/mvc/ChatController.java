package org.aim.aimessage.rest.mvc;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.model.Chat;
import org.aim.aimessage.core.service.AccountService;
import org.aim.aimessage.core.service.ChatService;
import org.aim.aimessage.rest.exception.ForbiddenException;
import org.aim.aimessage.rest.resources.ChatListResource;
import org.aim.aimessage.rest.resources.ChatResource;
import org.aim.aimessage.rest.resources.asm.ChatListResourceAsm;
import org.aim.aimessage.rest.resources.asm.ChatResourceAsm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final AccountService accountService;

    @Inject
    public ChatController(ChatService chatService, AccountService accountService) {
        this.chatService = chatService;
        this.accountService = accountService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<ChatListResource> list() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Account account = accountService.find(user.getUsername());
            if (account == null) {
                throw new ForbiddenException();
            } else {
                List<Chat> chats = chatService.find4Account(account.getId());
                return new ResponseEntity<ChatListResource>(
                        new ChatListResourceAsm().toResource(
                                new ChatListResource.ChatList(chats)
                        ),
                        HttpStatus.OK
                );
            }
        } else {
            throw new ForbiddenException();
        }
    }

    @RequestMapping(value = "/{chatId}", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<ChatResource> find(@PathVariable Long chatId) {
        Chat chat = chatService.find(chatId);
        if (chat != null) {
            return new ResponseEntity<ChatResource>(new ChatResourceAsm().toResource(chat), HttpStatus.OK);
        } else {
            return new ResponseEntity<ChatResource>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public ResponseEntity<ChatResource> createChat(@RequestBody ChatResource sentChat) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) principal;
            Account account = accountService.find(user.getUsername());
            if (account == null) {
                throw new ForbiddenException();
            } else {
                Chat chat = sentChat.toChat();
                chatService.create(chat, account.getId());
                try {
                    return new ResponseEntity<ChatResource>(new ChatResourceAsm().toResource(chat), HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity<ChatResource>(HttpStatus.NOT_FOUND);
                }
            }
        } else {
            throw new ForbiddenException();
        }
    }
}
