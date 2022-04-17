package com.tcs.edu.decorator;
import java.time.Instant;


/**
 * Класс TimestampMessageDecorator содержит методы для декорирования сообщений
 * @author ma.makarov
 */
public class TimestampMessageDecorator {

    final static int PAGE_SIZE = 2;

    private static int messageCount;
    /**
     * Метод timestampMessageDecorate используется для обогащения строки текущим
     * временем
     * @param message объект типа String, к которому требуется
     * добавить текущее время
     * @return объект decoratedMessage, представляющий собой
     * строку, декорированную текущим временем в формате
     * "YYYY-MM-DD'T'HH:mm:ss.sss'Z'"
     */
    public static String timestampDecorate(String message) {

        return String.format("%s, %s", Instant.now(), message);
    }
}

