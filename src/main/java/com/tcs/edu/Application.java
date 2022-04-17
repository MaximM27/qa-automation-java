package com.tcs.edu;
import com.tcs.edu.decorator.Severity;
import com.tcs.edu.printer.ConsolePrinter;
import static com.tcs.edu.decorator.MessageService.messageResult;

class Application {
    public static void main(String[] args) {

        ConsolePrinter.print(messageResult(Severity.MINOR, "Hello world!"));
        ConsolePrinter.print(messageResult(Severity.REGULAR,"Hello world!"));
        ConsolePrinter.print(messageResult(Severity.REGULAR,"Hello world!"));
        ConsolePrinter.print(messageResult(Severity.MAJOR,"Hello world!"));
        ConsolePrinter.print(messageResult(Severity.MINOR,"Hello world!"));
        ConsolePrinter.print(messageResult(Severity.MAJOR,"Hello world!"));
    }
}