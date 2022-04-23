package com.tcs.edu;
import com.tcs.edu.decorator.Doubling;
import com.tcs.edu.decorator.MessageOrder;
import com.tcs.edu.decorator.Severity;
import static com.tcs.edu.decorator.MessageService.print;

class Application {
    public static void main(String[] args) {
        //print(Severity.MINOR, MessageOrder.ASC, Doubling.DISTINCT, "Hello world!", "Hello!", "Bye","Hi!", null, "Bye");
        //print(Severity.REGULAR, MessageOrder.DESC, Doubling.DOUBLES, "Hello world!", "Hello!", "Hello", "Hi!", null, "Bye");
        print(Severity.REGULAR,MessageOrder.DESC, Doubling.DISTINCT, "Hello world!", "Hello", "Hello", "Hello","Hi", "Hi", null, "Bye");
        //print(Severity.MAJOR,"Hello world!");
        //print(Severity.MINOR,"Hello world!");
        //print(Severity.MAJOR,"Hello world!");
    }
}