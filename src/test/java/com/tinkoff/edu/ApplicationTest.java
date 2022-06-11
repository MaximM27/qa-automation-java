package com.tinkoff.edu;
import com.tinkoff.edu.decorator.*;
import com.tinkoff.edu.domain.Message;
import com.tinkoff.edu.printer.ConsolePrinter;

import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.UUID;

import static com.tinkoff.edu.decorator.SeverityLevel.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationTest {
    private MessageService messageService;
    private Message message1;
    private Message message2;
    private Message message3;

    @BeforeEach
    public void init() {
        messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());
        message1 = new Message(MINOR, "Hello, world!");
        message2 = new Message(MINOR, "Hi!");
        message3 = new Message(MAJOR, "Bye!");
    }

    @Nested
    @DisplayName("Тесты добавления объекта message в HashMap")
    class addMessageToHashMapTest {

        @BeforeEach
        public void initData() {
            UUID primaryKey1 = messageService.log(message1);
            UUID primaryKey2 = messageService.log(message2);
            UUID primaryKey3 = messageService.log(message3);
            message1.setId(primaryKey1);
            message2.setId(primaryKey2);
            message3.setId(primaryKey3);
        }

        @Test
        @DisplayName("Проверить добавление объектов message в HashMap")
        public void shouldSaveAllMessagesTest() {
            final Collection<Message> allMessages = messageService.findAll();
            assertThat(allMessages, hasSize(3));
            assertThat(allMessages, containsInAnyOrder(message1, message2, message3));
        }

        @Test
        @DisplayName("Повторно добавить объект Message в HashSet")
        public void tryRepeatSaveMessageTest() {
            messageService.log(message1);
            final Collection<Message> allMessages = messageService.findAll();
            assertThat(allMessages, hasSize(4));
        }
    }

    @Nested
    @DisplayName("Тесты по проверке фильтров")
    class checkFiltersTest {

        private UUID primaryKey1;
        private UUID primaryKey2;
        private UUID primaryKey3;
        private UUID randomPrimaryKey;

        @BeforeEach
        public void initData() {
            primaryKey1 = messageService.log(message1);
            primaryKey2 = messageService.log(message2);
            primaryKey3 = messageService.log(message3);
            randomPrimaryKey = UUID.randomUUID();
        }

        @Test
        @DisplayName("Проверить фильтр по параметру Severity")
        public void shouldReturnFilteredMessagesBySeverityTest() {

            final Collection<Message> filteredMessages = getMessages(messageService, MINOR);
            assertThat(filteredMessages, hasSize(2));
            assertThat(filteredMessages, containsInAnyOrder(message1, message2));
        }

        @Test
        @DisplayName("Проверить фильтр по параметру Severity при отсутствии сообщения в нужным Severity в мапе")
        public void shouldNotReturnFilteredMessagesBySeverityTest() {

            final Collection<Message> filteredMessages = getMessages(messageService, REGULAR);
            assertThat(filteredMessages, hasSize(0));
        }

        @Test
        @DisplayName("Проверить фильтр по generatedKey при передаче существующего в HashMap значения")
        public void shouldReturnFilteredMessagesByGeneratedKeyTest() {

            assertEquals(messageService.findByPrimaryKey(primaryKey1), message1);
        }

        @Test
        @DisplayName("Проверить фильтр по generatedKey при передаче несуществующего в HashMap значения")
        public void shouldNotReturnFilteredMessagesByGeneratedKeyTest() {

            assertNull(messageService.findByPrimaryKey(randomPrimaryKey));
        }
    }

    @Nested
    @DisplayName("Тесты по проверке исключений")
    class throwExceptionTest {

        @Test
        @DisplayName("Проверить выбрасывание исключения, если message is null")
        public void shouldReturnExceptionWhenMessageIsNullTest() {
            Message message1 = new Message(MINOR, "");
            MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());
            assertThrows(LogException.class, () -> messageService.log(message1));
        }

        @Test
        @DisplayName("Проверить выбрасывание исключения, если message is empty")
        public void shouldReturnExceptionIsEmptyTest() {
            Message message1 = new Message(MINOR, null);
            MessageService messageService = new OrderedDistinctMessageService(new Decorator(), new ConsolePrinter());

            assertThrows(LogException.class, () -> messageService.log(message1));
        }
    }
    private Collection<Message> getMessages(MessageService messageService, SeverityLevel level) {
        final Collection<Message> filteredMessages = messageService.findBySeverity(level);
        return filteredMessages;
    }
}