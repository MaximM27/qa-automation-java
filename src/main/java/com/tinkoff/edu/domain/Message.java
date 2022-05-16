package com.tinkoff.edu.domain;

import com.tinkoff.edu.decorator.SeverityLevel;

import java.util.Objects;

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

    @Override
    public String toString() {
        return "Message{" +
                "level=" + level +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return level == message.level && Objects.equals(body, message.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, body);
    }
}

