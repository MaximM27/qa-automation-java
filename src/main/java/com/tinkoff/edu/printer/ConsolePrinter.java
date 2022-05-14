package com.tinkoff.edu.printer;

import com.tinkoff.edu.Printer;

/**
 * Класс ConsolePrinter содержит методы для вывода в консоль
 * @author ma.makarov
 */
public class ConsolePrinter implements Printer {
    /**
     * Метод print используется для вывода в консоли параметра message типа String
     * и увеличения значения счетчика messageCount на 1
     * @param message объект типа String, который требуется
     * вывести в консоли
     */
    public void print(String message) {

        System.out.println(message);
    }
}
