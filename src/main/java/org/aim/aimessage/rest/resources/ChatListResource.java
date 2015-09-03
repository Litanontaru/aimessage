package org.aim.aimessage.rest.resources;

import org.aim.aimessage.core.model.Chat;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class ChatListResource extends ResourceSupport {
    private List<ChatResource> chats = new ArrayList<ChatResource>();

    public List<ChatResource> getChats() {
        return chats;
    }

    public void setChats(List<ChatResource> chats) {
        this.chats = chats;
    }

    //------------------------------------------------------------------------------------------------------------------

    public static class ChatList {
        private List<Chat> chats = new ArrayList<Chat>();

        public ChatList(List<Chat> chats) {
            this.chats = chats;
        }

        public List<Chat> getChats() {
            return chats;
        }

        public void setChats(List<Chat> chats) {
            this.chats = chats;
        }
    }
}
