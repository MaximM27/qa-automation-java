package com.tinkoff.edu;

import com.tinkoff.edu.decorator.TimestampMessageDecorator;
import com.tinkoff.edu.printer.ConsolePrinter;

/**
 * Main class: entry point
 */
class Application {
    public static void main(String[] args) {
        ConsolePrinter.print(TimestampMessageDecorator.decorate("Hello world!"));
    }
}