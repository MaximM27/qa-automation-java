package com.tcs.edu.decorator;

import static com.tcs.edu.decorator.SeparateLineMessageDecorator.separateLineDecorate;
import static com.tcs.edu.decorator.SeverityDecorator.severityDecorate;
import static com.tcs.edu.decorator.PrefixDecorator.prefixDecorate;

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
     * @param severity типа Severity, определяющий уровень важности
     * @param message типа String, который требуется декорировать
     * @return строку message после применения различных аспектов
     * декорирования
     */

    public static String messageResult(Severity severity, String message) {

        return prefixDecorate(message) + severityDecorate(severity) + separateLineDecorate();
    }
}
