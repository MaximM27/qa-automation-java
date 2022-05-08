package com.tinkoff.edu.decorator;

import com.tinkoff.edu.domain.Message;
import com.tinkoff.edu.printer.ConsolePrinter;

import java.util.Objects;

import static com.tinkoff.edu.decorator.PrefixDecorator.messageCount;
import static com.tinkoff.edu.decorator.SeparateMessageDecorator.separatePage;
import static com.tinkoff.edu.decorator.SeverityDecorator.getMessageBySeverity;
import static com.tinkoff.edu.decorator.PrefixDecorator.prefixDecorate;

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
     * @param message объект типа Message, который требуется декорировать
     */
    public static String process(Message message) {

        return prefixDecorate(message.getBody()) + getMessageBySeverity(message.getLevel()) + separatePage(messageCount);
    }

    /**
     * метод используется для вывода в консоль декорированных сообщений
     * @param message  объект типа Message, который требуется декорировать
     * @param messages массив объектов типа Message, которые требуется декорировать
     */
    public static void log(Message message, Message... messages) {
        if (message.getBody() != null) {
            ConsolePrinter.print(process(message));
        }
        for (Message currentMessage : messages) {
            if (currentMessage.getBody() != null) {
                ConsolePrinter.print(process(currentMessage));
            }
        }
    }

    /**
     * метод используется для вывода в консоль сортированных декорированных сообщений
     * @param message  объект типа Message, который требуется декорировать
     * @param messages массив объектов типа Message, которые требуется декорировать
     */
    public static void log(MessageOrder order, Message message, Message... messages) {
        if (message.getBody() != null) {
            ConsolePrinter.print(process(message));
        }
        switch (order) {
            case ASC: {
                for (Message current: messages) {
                    if (current != null) {
                        ConsolePrinter.print(process(current));
                    }
                }
                break;
            }
            case DESC: {
                for (int counter = messages.length - 1; counter > 0; counter--) {
                    if (messages[counter].getBody() != null) {
                        ConsolePrinter.print(process(messages[counter]));
                    }
                }
                break;
            }
        }
    }


    /**
     * метод используется для вывода сортированных сообщений, обогащен
     * проверкой на дублирование сообщений
     * @param order    типа MessageOrder, определяющий порядок сортировки
     * @param doubling типа Doubling, определяющий характер дублирования сообщений
     * @param message  объект типа Message, который требуется вывести
     * @param messages массив объектов типа Message, которые требуется вывести
     */
    public static void log(MessageOrder order, Doubling doubling, Message message, Message... messages) {
        if (message.getBody() != null) {
            ConsolePrinter.print(process(message));
        }
        switch (doubling) {
            case DOUBLES: {
                switch (order) {
                    case ASC: {
                        for (Message current : messages) {
                            if (current.getBody() != null) {
                                ConsolePrinter.print(process(current));
                            }
                        }
                        break;
                    }
                    case DESC: {
                        for (int counter = messages.length - 1; counter >= 0; counter--) {
                            if (messages[counter].getBody() != null) {
                                ConsolePrinter.print(process(messages[counter]));
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
                            if (messages[counter].getBody() != null) {
                                if (!isDuplicate(messages[counter].getBody(), printedMessages)) {
                                    ConsolePrinter.print(process(messages[counter]));
                                    printedMessages[counter] = messages[counter].getBody();
                                }
                            }
                        }
                        break;
                    }
                    case DESC: {
                        for (int counter = messages.length - 1; counter >= 0; counter--) {
                            if (messages[counter] != null) {
                                if (!isDuplicate(messages[counter].getBody(), printedMessages)) {
                                    ConsolePrinter.print(process(messages[counter]));
                                    printedMessages[counter] = messages[counter].getBody();
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
