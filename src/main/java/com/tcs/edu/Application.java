package com.tcs.edu;
import com.tcs.edu.decorator.Severity;
import static com.tcs.edu.decorator.MessageService.process;

class Application {
    public static void main(String[] args) {
        process(Severity.MINOR, "Hello world!");
        process(Severity.REGULAR,"Hello world!");
        process(Severity.REGULAR,"Hello world!");
        process(Severity.MAJOR,"Hello world!");
        process(Severity.MINOR,"Hello world!");
        process(Severity.MAJOR,"Hello world!");
    }
}