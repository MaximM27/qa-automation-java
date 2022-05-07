package com.tcs.edu.decorator;

import com.tcs.edu.printer.ConsolePrinter;

import java.util.Objects;

import static com.tcs.edu.decorator.PrefixDecorator.messageCount;
import static com.tcs.edu.decorator.SeparateMessageDecorator.separatePage;
import static com.tcs.edu.decorator.SeverityDecorator.getMessageBySeverity;
import static com.tcs.edu.decorator.PrefixDecorator.prefixDecorate;

/**
 * Класс содержит методы для получения итоговой строки
 * путем применения разных аспектов декорирования
 *
 * @author ma.makarov
 */
public class MessageService {
    /**
     * метод process используется для получения
     * итоговой строки после применения различных аспектов
     * декорирования
     *
     * @param level   типа Severity, определяющий уровень важности
     * @param message объект типа String, который требуется декорировать
     */
    public static String process(Severity level, String message) {

        return prefixDecorate(message) + getMessageBySeverity(level) + separatePage(messageCount);
    }

    /**
     * метод используется для вывода в консоль декорированных сообщений
     * @param level    типа Severity, определяющий уровень важности
     * @param message  объект типа String, который требуется декорировать
     * @param messages массив объектов типа String, которые требуется декорировать
     */
    public static void print(Severity level, String message, String... messages) {
        if (level == null) level = Severity.MINOR;
        if (message != null) {
            ConsolePrinter.print(process(level, message));
        }
        for (String currentMessage : messages) {
            if (currentMessage != null) {
                ConsolePrinter.print(process(level, currentMessage));
            }
        }
    }

    /**
     * метод используется для вывода в консоль сортированных декорированных сообщений
     * @param level    типа Severity, определяющий уровень важности
     * @param message  объект типа String, который требуется декорировать
     * @param messages массив объектов типа String, которые требуется декорировать
     */
    public static void print(Severity level, MessageOrder order, String message, String... messages) {
        if (level == null) level = Severity.MINOR;
        if (message != null) {
            ConsolePrinter.print(process(level, message));
        }
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


    /**
     * метод используется для вывода сортированных сообщений, обогащен
     * проверкой на дублирование сообщений
     * @param level    типа Severity, определяющий уровень важности
     * @param order    типа MessageOrder, определяющий порядок сортировки
     * @param doubling типа Doubling, определяющий характер дублирования сообщений
     * @param message  message объект типа String, который требуется декорировать
     * @param messages messages массив объектов типа String, которые требуется декорировать
     */
    public static void print(Severity level, MessageOrder order, Doubling doubling, String message, String... messages) {
        if (level == null) level = Severity.MINOR;
        if (message != null) {
            ConsolePrinter.print(process(level, message));
        }
        switch (doubling) {
            case DOUBLES: {
                switch (order) {
                    case ASC: {
                        for (String current : messages) {
                            if (current != null) {
                                ConsolePrinter.print(process(level, current));
                            }
                        }
                        break;
                    }
                    case DESC: {
                        for (int counter = messages.length - 1; counter >= 0; counter--) {
                            if (messages[counter] != null) {
                                ConsolePrinter.print(process(level, messages[counter]));
                            }
                        }
                        break;
                    }
                }
                break;
            }
            case DISTINCT: {
                String[] printedMessages = new String[messages.length];
                switch (order) {
                    case ASC: {
                        for (int counter = 0; counter < messages.length; counter++) {
                            if (messages[counter] != null) {
                                if (!isDuplicate(messages[counter], printedMessages)) {
                                    ConsolePrinter.print(process(level, messages[counter]));
                                    printedMessages[counter] = messages[counter];
                                }
                            }
                        }
                        break;
                    }
                    case DESC: {
                        for (int counter = messages.length - 1; counter >= 0; counter--) {
                            if (messages[counter] != null) {
                                if (!isDuplicate(messages[counter], printedMessages)) {
                                    ConsolePrinter.print(process(level, messages[counter]));
                                    printedMessages[counter] = messages[counter];
                                }
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    /**
     * метод используется для сравнения имеющейся строки
     * с элементами массива строк
     * @param message типа String, который требуется сравнивать с элементами массива
     * @param messages массив элементов типа String, с которыми сравнивается message
     * @return true, если совпадения есть, или false, - если совпадений нет
     */
    private static boolean isDuplicate(String message, String[] messages) {
        for (String currentMessage : messages) {
            if (Objects.equals(message, currentMessage)) {
                return true;
            }
        }
        return false;
    }
}
