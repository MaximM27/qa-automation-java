package com.tinkoff.edu.decorator;
import java.time.Instant;

/**
 * Класс содержит методы для декорирования префикса
 * сообщений
 * @author ma.makarov
 */
public class PrefixDecorator {
    public static int messageCount;
    /**
     * Метод prefixDecorate используется для обогащения строки
     * счетчиком строк и текущим временем
     * @param message объект типа String
     * @return строку, декорированную счетчиком строк и текущим
     * временем в формате "YYYY-MM-DD'T'HH:mm:ss.sss'Z'"
     */
    public static String prefixDecorate(String message) {
        messageCount++;

        return String.format("%d %s %s", messageCount, Instant.now(), message);
    }
}

