package com.tcs.edu.decorator;
import java.time.Instant;


/**
 * Класс TimestampMessageDecorator содержит методы для декорирования сообщений
 * @author ma.makarov
 */
public class TimestampMessageDecorator {
    public static int messageCount = 1;
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
        final var decoratedMessage = messageCount + " " + Instant.now() + " " + message;
        return decoratedMessage;
    }
}

