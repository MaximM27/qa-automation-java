package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.PrefixDecorator.messageCount;

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
     * @return строку, обогащенную разделительной линией, при выполнении условия
     */
    public static String separateLineDecorate () {
        String separateLine = "";
        if (messageCount % PAGE_SIZE == 0) {
            separateLine = " \n ---";
        }
        return separateLine;
    }
}
