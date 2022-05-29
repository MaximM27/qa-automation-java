package com.tinkoff.edu;
import com.tinkoff.edu.decorator.*;
import com.tinkoff.edu.domain.Message;
import com.tinkoff.edu.printer.ConsolePrinter;

import java.util.Collection;
import java.util.UUID;


class Application {
    public static void main(String[] args) {
        Message message1 = new Message(SeverityLevel.MINOR, "Hello, world!");
        Message message2 = new Message(SeverityLevel.REGULAR, "Hi!");
        Message message3 = new Message(SeverityLevel.MAJOR, "Bye!");
        Message message4 = new Message(SeverityLevel.MINOR, "Good Luck");
        Message message5 = new Message(SeverityLevel.MINOR, "Good Luck");
        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());
        messageService.log(message1, message2, message3, message4, message5);
        final Collection<Message> allMessages = messageService.findAll();
        for (Message current: allMessages) {
            System.out.println(current);
        }
        final UUID generatedKey = messageService.log(message1);
        System.out.println("=============");
        System.out.println(messageService.findByPrimaryKey(generatedKey));
    }
}