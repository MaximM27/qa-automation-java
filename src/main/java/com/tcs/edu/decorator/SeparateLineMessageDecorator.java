package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.CounterDecorator.messageCount;

/**
 * Класс SeparateLineMessageDecorator содержит методы
 * для добавления разделения между выведенным в консоли
 * сообщениями
 * @author ma.makarov
 */
public class SeparateLineMessageDecorator {

    final static int PAGE_SIZE = 2;

    /**
     * метод separateLineMessageDecorator испольуется для обогащения
     * строки разделительной линией
     * @param message объект типа String, к которому требуется добавить
     * разделитнельную линию
     * @return строку message, обогащенную разделительной линией
     */
    public static String separateLineDecorate (String message) {
        if (messageCount % PAGE_SIZE == 0) {
            message = String.format("%s \n %s", message, "---");
        }
        return message;
    }
}
