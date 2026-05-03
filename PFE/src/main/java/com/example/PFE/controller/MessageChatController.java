package com.example.PFE.controller;

import com.example.PFE.model.MessageChat;
import com.example.PFE.repository.MessageChatRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageChatController {

    private final MessageChatRepository repo;

    public MessageChatController(MessageChatRepository repo) {
        this.repo = repo;
    }

    // Client envoie message à admin
    @PostMapping("/client/send")
    public MessageChat sendClientMessage(@RequestBody Map<String, String> body,
                                         Authentication auth) {
        MessageChat msg = new MessageChat();
        msg.setClientEmail(auth.getName());
        msg.setSenderEmail(auth.getName());
        msg.setSenderRole("ROLE_USER");
        msg.setContenu(body.get("contenu"));
        return repo.save(msg);
    }

    // Client voit sa discussion
    @GetMapping("/client/my")
    public List<MessageChat> myMessages(Authentication auth) {
        return repo.findByClientEmailOrderByCreatedAtAsc(auth.getName());
    }

    // Admin voit toutes les discussions
    @GetMapping("/admin/all")
    public List<MessageChat> allMessages() {
        return repo.findAllByOrderByCreatedAtDesc();
    }

    // Admin répond à un client
    @PostMapping("/admin/reply/{clientEmail}")
    public MessageChat reply(@PathVariable String clientEmail,
                             @RequestBody Map<String, String> body,
                             Authentication auth) {
        MessageChat msg = new MessageChat();
        msg.setClientEmail(clientEmail);
        msg.setSenderEmail(auth.getName());
        msg.setSenderRole("ROLE_ADMIN");
        msg.setContenu(body.get("contenu"));
        return repo.save(msg);
    }
    @PutMapping("/client/mark-read")
    public void markClientMessagesRead(Authentication auth) {
        List<MessageChat> messages = repo.findByClientEmailOrderByCreatedAtAsc(auth.getName());

        for (MessageChat msg : messages) {
            if ("ROLE_ADMIN".equals(msg.getSenderRole())) {
                msg.setLu(true);
                repo.save(msg);
            }
        }
    }

    @PutMapping("/admin/mark-read/{clientEmail}")
    public void markAdminMessagesRead(@PathVariable String clientEmail) {
        List<MessageChat> messages = repo.findByClientEmailOrderByCreatedAtAsc(clientEmail);

        for (MessageChat msg : messages) {
            if ("ROLE_USER".equals(msg.getSenderRole())) {
                msg.setLu(true);
                repo.save(msg);
            }
        }
    }
}