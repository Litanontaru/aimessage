package org.aim.aimessage.backend.rest.resources;

import org.aim.aimessage.core.model.Chat;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class ChatResource extends ResourceSupport {
    private String name;
    private List<Long> accounts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Long> accounts) {
        this.accounts = accounts;
    }

    public Chat toChat() {
        Chat result = new Chat();
        result.setName(name);
        result.setAccounts(accounts == null ? new ArrayList<Long>() : new ArrayList<Long>(accounts));
        return result;
    }
}
