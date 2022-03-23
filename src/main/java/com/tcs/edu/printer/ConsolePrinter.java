package com.tcs.edu.printer;

/**
 * Класс ConsolePrinter необходим для описания методов
 * по выводу в консоли сообщений различного содержания
 * @author ma.makarov
 */
public class ConsolePrinter {
    /**
     * Метод print используется для вывода в консоли
     * параметра message типа String
     * @param message объект типа String, который требуется
     * вывести в консоли
     */
    public static void print(String message) {
        System.out.println(message);
    }
}
