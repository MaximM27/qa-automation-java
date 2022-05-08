package com.tinkoff.edu;

import com.tinkoff.edu.domain.Message;

public interface MessageDecorator {
    String decorate(Message message);
}
