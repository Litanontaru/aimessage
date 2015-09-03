package org.aim.aimessage.rest.resources;

import org.aim.aimessage.core.model.Account;
import org.springframework.hateoas.ResourceSupport;

import java.util.ArrayList;
import java.util.List;

public class AccountListResource extends ResourceSupport {
    private List<AccountResource> accounts = new ArrayList<AccountResource>();

    public List<AccountResource> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountResource> accounts) {
        this.accounts = accounts;
    }

    //------------------------------------------------------------------------------------------------------------------

    public static class AccountList {
        private List<Account> accounts = new ArrayList<Account>();

        public AccountList(List<Account> list) {
            this.accounts = list;
        }

        public List<Account> getAccounts() {
            return accounts;
        }

        public void setAccounts(List<Account> accounts) {
            this.accounts = accounts;
        }
    }
}
