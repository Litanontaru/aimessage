package org.aim.aimessage.core.service;

import org.aim.aimessage.core.model.Account;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AccountService {
    Account find(Long id);
    Account find(String login);

    void create(Account account);
    void update(Account account);
    void remove(Long id);
}
