package com.tinkoff.edu.decorator;

/**
 * Класс содержит методы для оформления сообщений
 * @author ma.makarov
 */
public class SeparateMessageDecorator {

    final static int PAGE_SIZE = 2;
    /**
     * метод испольуется для обогащения строки разделительной линией
     * @param messageCount объект типа Integer, представляющий собой
     * счетчик сообщений
     * @return строку, обогащенную разделительной линией, если
     * счетчик сообщение messageCount делится без остатка на
     * размер страницы PAGE_SIZE
     */
    public static String separatePage(Integer messageCount) {
        String separateLine = "";
        if (messageCount % PAGE_SIZE == 0) {
            separateLine = " \n ---";
        }
        return separateLine;
    }
}
