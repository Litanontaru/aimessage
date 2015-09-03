package org.aim.aimessage.core.service.impl;

import org.aim.aimessage.core.repository.AccountRepo;
import org.aim.aimessage.core.repository.SequenceRepo;
import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.service.AccountService;
import org.aim.aimessage.core.service.exception.AccountExistsException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AccountServiceImpl implements AccountService {
    private final SequenceRepo sequenceRepo;
    private final AccountRepo accountRepo;

    @Inject
    public AccountServiceImpl(SequenceRepo sequenceRepo, AccountRepo accountRepo) {
        this.sequenceRepo = sequenceRepo;
        this.accountRepo = accountRepo;
    }

    @Override
    public Account find(Long id) {
        return accountRepo.get(id);
    }

    @Override
    public Account find(String login) {
        return accountRepo.find(login);
    }

    @Override
    public void create(Account account) {
        Account exists = accountRepo.find(account.getName());
        if (exists != null) {
            throw new AccountExistsException();
        }
        account.setId(sequenceRepo.getNextSequenceId(Account.COLLECTION_NAME));
        accountRepo.save(account);
    }

    @Override
    public void update(Account account) {
        accountRepo.save(account);
    }

    @Override
    public void remove(Long id) {
        accountRepo.remove(id);
    }
}
