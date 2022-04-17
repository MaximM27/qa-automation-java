package com.tcs.edu.decorator;

/**
 * Класс CounterDecorator используется для обогащения строки
 * счетчиком выведенных сообщений
 * @author ma.makarov
 */
public class CounterDecorator {

    public static int messageCount;

    /**
     * Метод counterDecorate используется для обогащения строки
     * счетчиком выведенных на консоль сообщений
     * @param message объект типа String, к которому требуется добавить
     * значение счетчика
     * @return строку message, обогащенную значением счетчика
     */
    public static String counterDecorate (String message) {
        messageCount++;

        return String.format("%d %s", messageCount, message);
    }
}
