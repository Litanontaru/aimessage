package org.aim.aimessage.core.repository;

import org.aim.aimessage.core.model.Sequence;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;

@Repository
public class SequenceRepo {
    private final MongoOperations mongoOperations;

    @Inject
    public SequenceRepo(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public Long getNextSequenceId(String key) {
        // �������� ������ Sequence �� ������������ ���������
        Query query = new Query(Criteria.where("_id").is(key));

        // ����������� ���� sequence �� �������
        Update update = new Update();
        update.inc("sequence", 1);

        // ��������� �����, ��� ����� ���������� ���������� ������
        FindAndModifyOptions options = new FindAndModifyOptions();
        options.returnNew(true);

        // ������� ����� :)
        Sequence sequence = mongoOperations.findAndModify(query, update, options, Sequence.class);

        if (sequence == null) {
            throw new SequenceException("Unable to get sequence for key: " + key);
        }

        return sequence.getSequence();
    }
}
