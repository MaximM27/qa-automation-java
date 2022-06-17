package com.tinkoff.edu.decorator;

import com.tinkoff.edu.dictionary.SeverityLevel;

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
    public static String getMessageBySeverity(SeverityLevel level) {

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
