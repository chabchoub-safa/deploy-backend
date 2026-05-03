package com.example.PFE.repository;

import com.example.PFE.model.MessageChat;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface MessageChatRepository extends MongoRepository<MessageChat, String> {
    List<MessageChat> findByClientEmailOrderByCreatedAtAsc(String clientEmail);
    List<MessageChat> findAllByOrderByCreatedAtDesc();
}