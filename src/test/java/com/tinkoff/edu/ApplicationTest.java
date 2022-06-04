package com.tinkoff.edu;
import com.tinkoff.edu.decorator.*;
import com.tinkoff.edu.domain.Message;
import com.tinkoff.edu.printer.ConsolePrinter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ApplicationTest {

    @Test
    @DisplayName("Проверить добавление объекта Message в HashSet")
    public void ShouldSaveAllMessagesTest() {
        Message message1 = new Message(SeverityLevel.MINOR, "Hello, world!");

        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());
        messageService.log(message1);

        final Collection<Message> allMessages = messageService.findAll();

        for (Message current: allMessages) {
            assertAll(
                () -> assertEquals(current.getBody(), message1.getBody()),
                () -> assertEquals(current.getLevel(), message1.getLevel())
            );
        }
    }

    @Test
    @DisplayName("Проверить фильтр по параметру Severity")
    public void ShouldReturnFilteredMessagesBySeverityTest() {
        Message message1 = new Message(SeverityLevel.MINOR, "Hello, world!");
        Message message2 = new Message(SeverityLevel.REGULAR, "Hi!");
        Message message3 = new Message(SeverityLevel.MAJOR, "Bye!");

        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());
        messageService.log(message1, message2, message3);

        final Collection<Message> filteredMessages = messageService.findBySeverity(SeverityLevel.MINOR);

        for (Message current : filteredMessages) {
            assertAll(
                () -> assertEquals(current.getBody(), message1.getBody()),
                () -> assertEquals(current.getLevel(), message1.getLevel())
            );
        }
    }

    @Test
    @DisplayName("Проверить фильтр по generatedKey")
    public void ShouldReturnFilteredMessagesByGeneratedKeyTest() {
        Message message1 = new Message(SeverityLevel.MINOR, "Hello, world!");
        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());

        final UUID generatedKey = messageService.log(message1);

        assertEquals(messageService.findByPrimaryKey(generatedKey), message1);
    }

    @Test
    @DisplayName("Проверить выбрасывание исключения, если message is null")
    public void ShouldReturnExceptionWhenMessageIsNullTest() {
        Message message1 = new Message(SeverityLevel.MINOR, null);
        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());

        assertThrows(LogException.class, () -> messageService.log(message1));
    }

    @Test
    @DisplayName("Проверить выбрасывание исключения, если message is empty")
    public void ShouldReturnExceptionIsEmptyTest() {
        Message message1 = new Message(SeverityLevel.MINOR, "");
        MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());

        assertThrows(LogException.class, () -> messageService.log(message1));
    }
}