package com.tcs.edu.decorator;
import java.time.Instant;


/**
 * Класс TimestampMessageDecorator содержит методы для декорирования сообщений
 * @author ma.makarov
 */
public class TimestampMessageDecorator {

    final static int PAGE_SIZE = 1;

    private static int messageCount;
    /**
     * Метод decorate используется для обогащения строки текущим
     * временем и значением счетчика messageCount
     * @param message объект типа String, к которому требуется
     * добавить текущее время
     * @return объект decoratedMessage, представляющий собой
     * строку, декорированную текущим счетчиком и временем в формате
     * "YYYY-MM-DD'T'HH:mm:ss.sss'Z'"
     */
    public static String decorate(String message) {
        messageCount++;
        String decoratedMessage;
        decoratedMessage = String.format("%d %s %s", messageCount, Instant.now(), message);
        if (messageCount % PAGE_SIZE == 0) {
            decoratedMessage = String.format("%s \n %s", decoratedMessage, "---");
        }
        return decoratedMessage;
    }
}

