package com.tinkoff.edu.repository;

import com.tinkoff.edu.dictionary.SeverityLevel;
import com.tinkoff.edu.domain.Message;

import java.util.Collection;
import java.util.UUID;

public interface MessageRepository {
    UUID create(Message message);
    Message findByPrimaryKey(UUID key);
    Collection<Message> findAll();
    Collection<Message> findBySeverity(SeverityLevel by);
}
