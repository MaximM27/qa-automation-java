package com.tcs.edu.decorator;

import com.tcs.edu.printer.ConsolePrinter;

import static com.tcs.edu.decorator.PrefixDecorator.messageCount;
import static com.tcs.edu.decorator.SeparateMessageDecorator.separatePage;
import static com.tcs.edu.decorator.SeverityDecorator.getMessageBySeverity;
import static com.tcs.edu.decorator.PrefixDecorator.prefixDecorate;

/**
 * Класс содержит методы для получения итоговой строки
 * путем применения разных аспектов декорирования
 * @author ma.makarov
 */
public class MessageService {
    /**
     * метод process используется для получения
     * итоговой строки после применения различных аспектов
     * декорирования
     * @param level типа Severity, определяющий уровень важности
     * @param message объект типа String, который требуется декорировать
     */

    public static String process(Severity level, String message) {

        return prefixDecorate(message) + getMessageBySeverity(level) + separatePage(messageCount);
    }

    /**
     * метод используется для вывода
     * @param level типа Severity, определяющий уровень важности
     * @param message объект типа String, который требуется декорировать
     * @param messages массив объектов типа String, которые требуется декорировать
     */

    public static void print(Severity level, String message, String ... messages) {
        ConsolePrinter.print(process(level, message));
        for (String current: messages) {
            if (current != null) {
                ConsolePrinter.print(process(level, current));
            }
        }
    }

    /**
     * метод используется для вывода
     * @param level типа Severity, определяющий уровень важности
     * @param order типа MessageOrder, определяющий порядок сортировки
     * @param message message объект типа String, который требуется декорировать
     * @param messages messages массив объектов типа String, которые требуется декорировать
     */
    public static void print (Severity level, MessageOrder order, String message, String ... messages) {
        ConsolePrinter.print(process(level, message));
        switch (order) {
            case ASC: {
                for (String current: messages) {
                    if (current != null) {
                        ConsolePrinter.print(process(level, current));
                        }
                    }
                break;
            }
            case DESC: {
                for (int counter = messages.length - 1; counter > 0; counter--) {
                    if (messages[counter] != null) {
                        ConsolePrinter.print(process(level, messages[counter]));
                        }
                    }
                break;
            }
        }
    }
}
