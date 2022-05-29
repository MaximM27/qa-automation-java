package com.tinkoff.edu.decorator;

import com.tinkoff.edu.MessageDecorator;
import com.tinkoff.edu.Printer;
import com.tinkoff.edu.domain.Message;
import com.tinkoff.edu.printer.ConsolePrinter;
import com.tinkoff.edu.repository.HashMapMessageRepository;
import com.tinkoff.edu.repository.MessageRepository;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс содержит методы для получения итоговой строки
 * путем применения разных аспектов декорирования
 * @author ma.makarov
 */
public class OrderedDistinctMessageService extends ValidatedService implements com.tinkoff.edu.MessageService {
    private Printer printer;
    private MessageDecorator decorator;
    private MessageRepository messageRepository = new HashMapMessageRepository();

    /**
     * Конструктор для создания объектов класса OrderedDistinctMessageService
     * @param decorator объект класса Decorator
     * @param printer объект класса ConsolePrinter
     */
    public OrderedDistinctMessageService(Decorator decorator, ConsolePrinter printer) {
        this.decorator = decorator;
        this.printer = printer;
    }

    /**
     * метод используется для сохранения создания primary key и сохранения
     * объекта Message в HashMap
     * @param message объект типа Message, который требуется сохранить в HashMap
     * @return primaryKey типа UUID
     */
    public UUID log(Message message){
        try {
            super.isArgsValid(message);
        } catch (IllegalArgumentException e){
            throw new LogException("invalid argument message", e);
        }
        return messageRepository.create(message);
    }

    /**
     * метод используется для вывода в консоль декорированных сообщений
     * @param message  объект типа Message, который требуется декорировать
     * @param messages массив объектов типа Message, которые требуется декорировать
     */
    public void log(Message message, Message... messages) {
        try {
            super.isArgsValid(message);
        } catch (IllegalArgumentException e){
            throw new LogException("invalid argument message", e);
        }
        messageRepository.create(message);

        for (Message currentMessage : messages) {
            try {
                super.isArgsValid(currentMessage);
            } catch (IllegalArgumentException e){
                throw new LogException("invalid argument message", e);
            }
            messageRepository.create(currentMessage);
        }
       // return messageRepository.;
    }

    /**
     * метод используется для вывода в консоль сортированных декорированных сообщений
     * @param message  объект типа Message, который требуется декорировать
     * @param messages массив объектов типа Message, которые требуется декорировать
     */
    public void log(MessageOrder order, Message message, Message... messages) {
        try {
            super.isArgsValid(message);
        } catch (IllegalArgumentException e){
            throw new LogException("invalid argument message", e);
        }
         messageRepository.create(message);
        switch (order) {
            case ASC: {
                for (Message current: messages) {
                    try {
                        super.isArgsValid(current);
                    } catch (IllegalArgumentException e){
                        throw new LogException("invalid argument message", e);
                    }
                    messageRepository.create(current);
                }
                break;
            }
            case DESC: {
                for (int counter = messages.length - 1; counter >= 0; counter--) {
                    try {
                        super.isArgsValid(messages[counter]);
                    } catch (IllegalArgumentException e){
                        throw new LogException("invalid argument message", e);
                    }
                    messageRepository.create(messages[counter]);
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
    public void log(MessageOrder order, Doubling doubling, Message message, Message... messages) {
        switch (doubling) {
            case DOUBLES: {
                log(order, message, messages);
                break;
            }
            case DISTINCT: {
                try {
                    super.isArgsValid(message);
                } catch (IllegalArgumentException e){
                    throw new LogException("invalid argument message", e);
                }
                messageRepository.create(message);

                String[] printedMessages = new String[messages.length];
                switch (order) {
                    case ASC: {
                        for (int counter = 0; counter < messages.length; counter++) {
                            try {
                                super.isArgsValid(messages[counter]);
                            } catch (IllegalArgumentException e){
                                throw new LogException("invalid argument message", e);
                            }
                            if (!isDuplicate(messages[counter].getBody(), printedMessages)) {
                                messageRepository.create(messages[counter]);
                                printedMessages[counter] = messages[counter].getBody();

                            }
                        }
                        break;
                    }
                    case DESC: {
                        for (int counter = messages.length - 1; counter >= 0; counter--) {
                            try {
                                super.isArgsValid(messages[counter]);
                            } catch (IllegalArgumentException e){
                                throw new LogException("invalid argument message", e);
                            }
                            if (!isDuplicate(messages[counter].getBody(), printedMessages)) {
                                messageRepository.create(messages[counter]);
                                printedMessages[counter] = messages[counter].getBody();
                            }

                        }
                        break;
                    }
                }
                break;
            }
        }
    }

    @Override
    public Message findByPrimaryKey(UUID key) {
        return messageRepository.findByPrimaryKey(key);
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
