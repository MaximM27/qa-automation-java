package com.tinkoff.edu.decorator;

import com.tinkoff.edu.domain.Message;

public abstract class ValidatedService {
    public boolean isArgsValid(Message message){
        if (message.getBody() == null) return false;
        if (message.getBody().isEmpty()) return false;

        return true;
    }
}
