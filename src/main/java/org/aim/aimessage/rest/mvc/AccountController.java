package org.aim.aimessage.rest.mvc;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.service.AccountService;
import org.aim.aimessage.core.service.exception.AccountExistsException;
import org.aim.aimessage.rest.exception.ConflictException;
import org.aim.aimessage.rest.resources.AccountListResource;
import org.aim.aimessage.rest.resources.AccountResource;
import org.aim.aimessage.rest.resources.asm.AccountListResourceAsm;
import org.aim.aimessage.rest.resources.asm.AccountResourceAsm;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.util.*;

@Controller
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @Inject
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //--- LOGIN AND REGISTER -------------------------------------------------------------------------------------------

    @RequestMapping(method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountResource> register(@RequestBody AccountResource sentAccount) {
        try {
            Account account = sentAccount.toAccount();
            accountService.create(account);
            AccountResource result = new AccountResourceAsm().toResource(account);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(result.getLink("self").getHref()));
            return new ResponseEntity<AccountResource>(result, headers, HttpStatus.CREATED);
        } catch(AccountExistsException exception) {
            throw new ConflictException(exception);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountListResource> requestLogin(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "password", required = false) String password) {
        Account account = accountService.find(name);
        List<Account> list;
        if (account != null && Objects.equals(password, account.getPassword())) {
            list = Collections.singletonList(account);
        } else {
            list = Collections.emptyList();
        }
        return new ResponseEntity<AccountListResource>(
                new AccountListResourceAsm().toResource(
                        new AccountListResource.AccountList(list)
                ),
                HttpStatus.OK
        );
    }

    //--- EXIST ACCOUNT OPERATIONS -------------------------------------------------------------------------------------

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ResponseEntity<AccountResource> find(@PathVariable Long accountId) {
        Account account = accountService.find(accountId);
        if (account != null) {
            return new ResponseEntity<AccountResource>(new AccountResourceAsm().toResource(account), HttpStatus.OK);
        } else {
            return new ResponseEntity<AccountResource>(HttpStatus.NOT_FOUND);
        }
    }
}