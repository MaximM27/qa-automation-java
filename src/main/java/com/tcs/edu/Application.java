package com.tcs.edu;
import com.tcs.edu.decorator.Severity;
import static com.tcs.edu.decorator.MessageService.print;

class Application {
    public static void main(String[] args) {
        print(Severity.MINOR, "Hello world!");
        print(Severity.REGULAR,"Hello world!");
        print(Severity.REGULAR,"Hello world!");
        print(Severity.MAJOR,"Hello world!");
        print(Severity.MINOR,"Hello world!");
        print(Severity.MAJOR,"Hello world!");
    }
}