package org.aim.aimessage.core.repository;

import org.aim.aimessage.core.model.ChatEntry;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

@Repository
public class ChatEntryRepo {
    private final MongoOperations mongoOperations;

    @Inject
    public ChatEntryRepo(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public void save(ChatEntry chatEntry) {
        mongoOperations.save(chatEntry);
    }

    //todo move to service
    public void update(ChatEntry chatEntry) {
        ChatEntry old = get(chatEntry.getId());
        old.setUpdated(true);
        old.setDt(chatEntry.getDt());
        old.setPhrase(chatEntry.getPhrase());
        save(chatEntry);
    }

    public ChatEntry remove(Long id) {
        ChatEntry result = get(id);
        mongoOperations.remove(Query.query(Criteria.where("id").is(id)), ChatEntry.class);
        return result;
    }

    public ChatEntry get(Long id) {
        return mongoOperations.findOne(Query.query(Criteria.where("id").is(id)), ChatEntry.class);
    }
    
    public List<ChatEntry> getAll(Long chatId) {
        return mongoOperations.find(Query.query(Criteria.where("chatId").is(chatId)), ChatEntry.class);
    }
}
