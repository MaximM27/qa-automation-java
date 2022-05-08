package com.tinkoff.edu.decorator;

import com.tinkoff.edu.MessageDecorator;
import com.tinkoff.edu.domain.Message;

import static com.tinkoff.edu.decorator.PrefixDecorator.messageCount;
import static com.tinkoff.edu.decorator.PrefixDecorator.prefixDecorate;
import static com.tinkoff.edu.decorator.SeparateMessageDecorator.separatePage;
import static com.tinkoff.edu.decorator.SeverityDecorator.getMessageBySeverity;

/**
 * Класс описывает методы по декорированию сообщения
 * @author ma.makarov
 */
public class Decorator implements MessageDecorator {
    /**
     * метод process используется для получения
     * итоговой строки после применения различных аспектов
     * декорирования
     * @param message объект типа Message, который требуется декорировать
     */
    public String decorate(Message message) {

        return prefixDecorate(message.getBody()) + getMessageBySeverity(message.getLevel()) + separatePage(messageCount);
    }
}
