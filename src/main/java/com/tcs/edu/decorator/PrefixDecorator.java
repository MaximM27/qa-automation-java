package com.tcs.edu.decorator;
import java.time.Instant;


/**
 * Класс prefixDecorator содержит методы для декорирования сообщений
 * @author ma.makarov
 */
public class PrefixDecorator {


    public static int messageCount;
    /**
     * Метод prefixDecorate используется для обогащения строки текущим
     * временем
     * @param message объект типа String, к которому требуется
     * добавить текущее время
     * @return объект decoratedMessage, представляющий собой
     * строку, декорированную текущим временем в формате
     * "YYYY-MM-DD'T'HH:mm:ss.sss'Z'"
     */
    public static String prefixDecorate(String message) {
        messageCount++;

        return String.format("%d %s %s", messageCount, Instant.now(), message);
    }
}

