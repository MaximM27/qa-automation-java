package com.tinkoff.edu;
import com.tinkoff.edu.decorator.Doubling;
import com.tinkoff.edu.decorator.MessageOrder;
import com.tinkoff.edu.decorator.SeverityLevel;
import com.tinkoff.edu.domain.Message;

import static com.tinkoff.edu.decorator.MessageService.log;

class Application {
    public static void main(String[] args) {
        Message message1 = new Message(SeverityLevel.MINOR, "Hello, world!");
        Message message2 = new Message(SeverityLevel.REGULAR, "Hi!");
        Message message3 = new Message(SeverityLevel.MAJOR, "Bye!");
        Message message4 = new Message(SeverityLevel.MINOR, "Good Luck");
        Message message5 = new Message(SeverityLevel.MINOR, "Good Luck");
        Message message6 = new Message(null, null);
        log(message1, message2, message3, message4, message5, message6);
        log(MessageOrder.ASC, message1, message2, message3, message4, message5, message6);
        log(MessageOrder.DESC, message1, message2, message3, message4, message5, message6);
        log(MessageOrder.ASC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
        log(MessageOrder.DESC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        log(MessageOrder.ASC, Doubling.DISTINCT, message1, message2, message3, message4, message5, message6);
        log(MessageOrder.DESC, Doubling.DOUBLES, message1, message2, message3, message4, message5, message6);
    }
}