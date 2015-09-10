package org.aim.aimessage.backend.security;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.service.AccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    private final AccountService service;

    @Inject
    public UserDetailServiceImpl(AccountService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = service.find(name);
        if(account == null) {
            throw new UsernameNotFoundException("no user found with " + name);
        }
        return new AccountUserDetails(account);
    }
}
