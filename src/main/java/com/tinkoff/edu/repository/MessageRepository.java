package com.tinkoff.edu.repository;

import com.tinkoff.edu.domain.Message;

import java.util.UUID;

public interface MessageRepository {
    UUID create(Message message);
}
