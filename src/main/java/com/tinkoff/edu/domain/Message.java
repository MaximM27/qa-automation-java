package com.tinkoff.edu.domain;

import com.tinkoff.edu.decorator.SeverityLevel;

/**
 * Класс описывает объекты типа Message
 */
public class Message {
    private SeverityLevel level;
    private String body;

    /**
     * Конструктор для создания объектов типа Message
     * @param level типа SeverityLevel, описывает уровень важности
     * @param body типа String - текстовое сообщение
     */
    public Message(SeverityLevel level, String body) {
        if (level == null)
            this.level = SeverityLevel.MINOR;
        else
            this.level = level;
        this.body = body;
    }

    /**
     * Перегруженный конструктор для создания объектов типа Message
     * @param body типа String - текстовое сообщение
     */
    public Message(String body) {
        this(SeverityLevel.MINOR, body);
    }

    /**
     * Метод для получения значение параметра level
     * @return значение параметра level
     */
    public SeverityLevel getLevel() {
        return level;
    }

    /**
     * Метод для получения значение параметра body
     * @return значение параметра body
     */
    public String getBody() {
        return body;
    }
}

