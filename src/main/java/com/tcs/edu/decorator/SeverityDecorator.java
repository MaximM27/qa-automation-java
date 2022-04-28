package com.tcs.edu.decorator;

/**
 * Класс содержит методы для декорирования сообщений
 * уровнем важности
 * @author ma.makarov
 */
public class SeverityDecorator {

    /**
     * метод severityDecoratedMessage используется для возвращения строки
     * в зависимости от уровня важности
     * @param level enum класса Severity, представляющий собой уровень
     * важности
     * @return строку, значение которой зависит от уровня важности
     */
    public static String getMessageBySeverity(Severity level) {

        String severityString = null;

        switch (level) {
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
