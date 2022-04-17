package com.tcs.edu.decorator;

/**
 * Класс SeverityDecorator содержит методы для декорирования
 * сообщений уровнем важности
 * @author ma.makarov
 */

public class SeverityDecorator {

    /**
     * метод severityDecoratedMessage используется для обогащения строки
     * уровнем важности
     * @return объект severityDecoratedMessage, представляющий собой
     * строку, декорированную уровнем важности
     */
    public static String severityDecorate(Severity severity) {

        String severityString = null;

        switch (severity) {
            case MINOR : {
                severityString = "()";
                break;
            }
            case REGULAR: {
                severityString = "(!)";
                break;
            }
            case MAJOR: {
                severityString = "(!!!)";
                break;
            }
        }
        return severityString;
    }
}
