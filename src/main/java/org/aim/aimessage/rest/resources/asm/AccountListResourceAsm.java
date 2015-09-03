package org.aim.aimessage.rest.resources.asm;

import org.aim.aimessage.rest.mvc.AccountController;
import org.aim.aimessage.rest.resources.AccountListResource;
import org.aim.aimessage.rest.resources.AccountResource;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

public class AccountListResourceAsm extends ResourceAssemblerSupport<AccountListResource.AccountList, AccountListResource> {
    public AccountListResourceAsm() {
        super(AccountController.class, AccountListResource.class);
    }

    @Override
    public AccountListResource toResource(AccountListResource.AccountList accountList) {
        List<AccountResource> resList = new AccountResourceAsm().toResources(accountList.getAccounts());
        AccountListResource finalRes = new AccountListResource();
        finalRes.setAccounts(resList);
        return finalRes;
    }
}
