package com.tcs.edu.printer;
import static com.tcs.edu.decorator.TimestampMessageDecorator.messageCount;
/**
 * Класс ConsolePrinter содержит методы для вывода в консоль
 * @author ma.makarov
 */
public class ConsolePrinter {
    /**
     * Метод print используется для вывода в консоли параметра message типа String
     * и увеличения значения счетчика messageCount на 1
     * @param message объект типа String, который требуется
     * вывести в консоли
     */
    public static void print(String message) {

        System.out.println(message);
        messageCount++;
    }
}
