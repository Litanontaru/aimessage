package org.aim.aimessage.core.repository;

import org.aim.aimessage.core.model.Account;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class AccountRepo {
    private final MongoOperations mongoOperations;

    @Inject
    public AccountRepo(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void save(Account account) {
        mongoOperations.save(account);
    }

    public Account get(Long id) {
        return mongoOperations.findOne(Query.query(Criteria.where("id").is(id)), Account.class);
    }

    public Account find(String login) {
        return mongoOperations.findOne(Query.query(Criteria.where("login").is(login)), Account.class);
    }

    public void remove(Long id) {
        mongoOperations.remove(Query.query(Criteria.where("id").is(id)), Account.class);
    }
}
