package org.aim.aimessage.backend.rest.resources.asm;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.backend.rest.AccountController;
import org.aim.aimessage.backend.rest.resources.AccountResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class AccountResourceAsm extends ResourceAssemblerSupport<Account, AccountResource> {
    public AccountResourceAsm() {
        super(AccountController.class, AccountResource.class);
    }

    @Override
    public AccountResource toResource(Account account) {
        AccountResource res = new AccountResource();
        res.setLogin(account.getLogin());
        res.setPassword(account.getPassword());
        res.setName(account.getName());
        res.add(linkTo(methodOn(AccountController.class).find(account.getId())).withSelfRel());
        return res;
    }
}
