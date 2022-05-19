package com.tinkoff.edu.decorator;

import com.tinkoff.edu.domain.Message;

/**
 *Базовый абстрактный класс описывает методы валидацции входных параметров
 * @author ma.makarov
 */
public abstract class ValidatedService {
    /**
     * метод описывает правила валидации входных параметров объектов класса Message
     * @param message объект класса Message
     * @return true или false
     */
    public boolean isArgsValid(Message message){
        if (message.getBody() == null) return false;
        if (message.getBody().isEmpty()) return false;

        return true;
    }
}
