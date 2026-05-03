package com.example.PFE.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendResetCode(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Code de réinitialisation mot de passe");
        message.setText("Votre code est : " + code + "\nValable 10 minutes.");

        mailSender.send(message);
    }

    public void sendStopForgottenEmail(String to, String tissuCode, String machineCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Alerte STOP oublié");
        message.setText(
                "Bonjour,\n\n" +
                        "Vous avez lancé un START sans STOP dans le délai estimé.\n\n" +
                        "Code tissu : " + tissuCode + "\n" +
                        "Code machine : " + machineCode + "\n\n" +
                        "Merci de vérifier et scanner le STOP si le travail est terminé.\n\n" +
                        "Application de suivi CETTEX"
        );

        mailSender.send(message);
    }
}