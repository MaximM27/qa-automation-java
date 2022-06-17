package com.tinkoff.edu;

import com.tinkoff.edu.dictionary.Doubling;
import com.tinkoff.edu.dictionary.MessageOrder;
import com.tinkoff.edu.dictionary.SeverityLevel;
import com.tinkoff.edu.domain.Message;

import java.util.Collection;
import java.util.UUID;

public interface MessageService {
    UUID log(Message message);
    void log(Message message, Message... messages);
    void log(MessageOrder order, Message message, Message... messages);
    void log(MessageOrder order, Doubling doubling, Message message, Message... messages);
    Message findByPrimaryKey(UUID key);
    Collection<Message> findAll();
    Collection<Message> findBySeverity(SeverityLevel by);
}
