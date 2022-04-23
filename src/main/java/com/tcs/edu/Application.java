package com.tcs.edu;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;
import static com.tcs.edu.decorator.MessageService.print;

class Application {
    public static void main(String[] args) {
        print(Severity.MINOR, MessageOrder.ASC, "Hello world!", "Hello!", "Hi!", null, "Bye");
        print(Severity.REGULAR, MessageOrder.DESC, "Hello world!", "Hello!", "Hi!", null, "Bye");
        print(Severity.REGULAR,"Hello world!");
        print(Severity.MAJOR,"Hello world!");
        print(Severity.MINOR,"Hello world!");
        print(Severity.MAJOR,"Hello world!");
    }
}