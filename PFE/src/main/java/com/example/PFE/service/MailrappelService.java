package com.example.PFE.service;


import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class MailrappelService {
    private final JavaMailSender sender;

    public MailrappelService(JavaMailSender sender) {
        this.sender = sender;
    }

    public void send(String to, String subject, String text) {
        if (to == null || to.isBlank()) return;

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(text);

        sender.send(msg);
    }
}
