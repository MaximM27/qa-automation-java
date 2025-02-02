package com.tinkoff.edu.repository;

import com.tinkoff.edu.dictionary.SeverityLevel;
import com.tinkoff.edu.domain.Message;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HashMapMessageRepository implements MessageRepository {
    private Map<UUID, Message> messages = new HashMap<>();

    @Override
    public UUID create(Message message) {
        final UUID key = UUID.randomUUID();
        message.setId(key);
        messages.put(key, message);
        return key;
    }

    @Override
    public Message findByPrimaryKey(UUID key) {
        return messages.get(key);
    }

    @Override
    public Collection<Message> findAll() {
         return messages.values();
    }

    @Override
    public Collection<Message> findBySeverity(SeverityLevel by) {
        return messages.values().stream()
                .filter(message -> message.getLevel().equals(by))
                .collect(Collectors.toList());
    }
}
