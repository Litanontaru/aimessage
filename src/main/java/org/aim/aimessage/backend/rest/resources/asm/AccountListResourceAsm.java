package org.aim.aimessage.backend.rest.resources.asm;

import org.aim.aimessage.backend.rest.AccountController;
import org.aim.aimessage.backend.rest.resources.AccountListResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountListResource.AccountList, AccountListResource> {
    public AccountListResourceAsm() {
        super(AccountController.class, AccountListResource.class);
    }

    @Override
    public AccountListResource toResource(AccountListResource.AccountList accountList) {
        AccountListResource result = new AccountListResource();
        result.setAccounts(new AccountResourceAsm().toResources(accountList.getAccounts()));
        return result;
    }
}
