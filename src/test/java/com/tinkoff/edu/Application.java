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
        Message message6 = new Message(null, null);
        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());
        messageService.log(message1, message2, message3, message4, message5, message6);
        messageService.log(MessageOrder.ASC, message1, message2, message3, message4, message5, message6);
        messageService.log(MessageOrder.DESC, message1, message2, message3, message4, message5, message6);
        messageService.log(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
        messageService.log(MessageOrder.DESC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        messageService.log(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        messageService.log(MessageOrder.DESC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
        System.out.println(message1);
        System.out.println(message1.equals(message2));
        System.out.println(message4.equals(message5));
        System.out.println(message4.hashCode());
    }
}