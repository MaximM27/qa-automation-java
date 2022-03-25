package com.tcs.edu.decorator;
import java.time.Instant;


/**
 * Класс TimestampMessageDecorator содержит методы для декорирования сообщений,
 * выведенных на консоль
 * @author ma.makarov
 */
public class TimestampMessageDecorator {
    /**
     * Метод decorate используется для обогащения строки текущим
     * временем
     * @param message объект типа String, к которому требуется
     * добавить текущее время
     * @return строку, декорированную текущим временем
     */
    public static String decorate(String message) {
        return Instant.now() + " " + message;
    }
}

