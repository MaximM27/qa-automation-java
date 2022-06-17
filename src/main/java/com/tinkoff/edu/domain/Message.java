package com.tinkoff.edu.domain;

import com.tinkoff.edu.dictionary.SeverityLevel;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс описывает объекты типа Message
 */
public class Message {

    public UUID id;
    private SeverityLevel level;
    private String body;

    /**
     * Конструктор для создания объектов типа Message
     * @param id  типа UUID - primary key сообщения
     * @param level типа SeverityLevel, описывает уровень важности
     * @param body типа String - текстовое сообщение
     */
    public Message(UUID id, SeverityLevel level, String body) {
        this.id = id;
        if (level == null)
            this.level = SeverityLevel.MINOR;
        else
            this.level = level;
        this.body = body;
    }

    /**
     * Перегруженный конструктор для создания объектов типа Message
     * @param id типа UUID - primary key сообщения
     * @param body типа String - текстовое сообщение
     */
    public Message(UUID id, String body) {
        this(id, SeverityLevel.MINOR, body);
    }

    /**
     * Перегруженный конструктор для создания объектов типа Message
     * @param level типа SeverityLevel, описывает уровень важности
     * @param body типа String - текстовое сообщение
     */
    public Message(SeverityLevel level, String body) {
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
     * Метод для получения значения параметра id
     * @return значения параметра id
     */
    public UUID getId() { return id; }

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

    /**
     * Метод для модификации значения параметра id
     */
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", level=" + level +
                ", body='" + body + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id.equals(message.id) && level == message.level && body.equals(message.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, level, body);
    }
}

