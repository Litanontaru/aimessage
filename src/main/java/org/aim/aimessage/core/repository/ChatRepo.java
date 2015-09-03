package org.aim.aimessage.core.repository;

import org.aim.aimessage.core.model.Account;
import org.aim.aimessage.core.model.Chat;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class ChatRepo {
    private final MongoOperations mongoOperations;

    @Inject
    public ChatRepo(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void save(Chat chat) {
        mongoOperations.save(chat);
    }

    public Chat get(Long id) {
        return mongoOperations.findOne(Query.query(Criteria.where("id").is(id)), Chat.class);
    }

    public void remove(Long id) {
        mongoOperations.remove(Query.query(Criteria.where("id").is(id)), Chat.class);
    }

    public List<Chat> find(Long accountId) {
        return mongoOperations.find(Query.query(Criteria.where("accounts").is(accountId)), Chat.class);
    }
}
