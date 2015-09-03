package org.aim.aimessage.rest.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.aim.aimessage.core.model.Account;
import org.springframework.hateoas.ResourceSupport;

public class AccountResource extends ResourceSupport {
    private String login;
    private String password;
    private String name;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Account toAccount() {
        Account account = new Account();
        account.setLogin(login);
        account.setPassword(password);
        account.setName(name);
        return account;
    }
}
