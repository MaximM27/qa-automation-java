package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.SeparateLineMessageDecorator.separateLineDecorate;
import static com.tcs.edu.decorator.SeverityDecorator.severityDecorate;
import static com.tcs.edu.decorator.PrefixDecorator.prefixDecorate;
import static com.tcs.edu.printer.ConsolePrinter.print;

/**
 * Класс messageService используется для получения
 * итоговой строки путем применения разных аспектов
 * декорирования
 * @author ma.makarov
 */
public class MessageService {

    /**
     * метод messageResult используется для получения
     * итоговой строки после применения различных аспектов
     * декорирования
     * @param level типа Severity, определяющий уровень важности
     * @param messages массив объектов типа String, которые требуется декорировать
     */

    public static void process(Severity level, String ... messages) {
        for (String message : messages) {
            print(prefixDecorate(message) + severityDecorate(level) + separateLineDecorate());
        }
    }
}
