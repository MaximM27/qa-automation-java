package com.tinkoff.edu;
import com.tinkoff.edu.decorator.*;
import com.tinkoff.edu.domain.Message;
import com.tinkoff.edu.printer.ConsolePrinter;


class Application {
    public static void main(String[] args) {
        Message message1 = new Message(SeverityLevel.MINOR, "Hello, world!");
        Message message2 = new Message(SeverityLevel.REGULAR, "Hi!");
        Message message3 = new Message(SeverityLevel.MAJOR, "Bye!");
        Message message4 = new Message(SeverityLevel.MINOR, "Good Luck");
        Message message5 = new Message(SeverityLevel.MINOR, "Good Luck");
        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());
        messageService.log(message1, message2, message3, message4, message5);
        System.out.println();
    }
}