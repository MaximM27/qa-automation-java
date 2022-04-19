package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.PrefixDecorator.messageCount;

/**
 * Класс SeparateMessageDecorator содержит методы
 * для оформления сообщений
 * @author ma.makarov
 */
public class SeparateMessageDecorator {

    final static int PAGE_SIZE = 2;

    /**
     * метод separateMessageDecorator испольуется для обогащения
     * строки разделительной линией
     * @return строку, обогащенную разделительной линией, если
     * счетчик сообщение messageCount делится без остатка на
     * размер страницы PAGE_SIZE
     */
    public static String separatePage() {
        String separateLine = "";
        if (messageCount % PAGE_SIZE == 0) {
            separateLine = " \n ---";
        }
        return separateLine;
    }
}
