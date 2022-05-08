package com.tinkoff.edu;

import com.tinkoff.edu.decorator.Doubling;
import com.tinkoff.edu.decorator.MessageOrder;
import com.tinkoff.edu.domain.Message;

public interface MessageService {
    void log(Message message, Message... messages);
    void log(MessageOrder order, Message message, Message... messages);
    void log(MessageOrder order, Doubling doubling, Message message, Message... messages);
}
