package com.example.kafkaspring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailService {

    private final String REGISTRATION_INFO = "РЕГИСТРАЦИЯ НА MIRAS";

    private final String REGISTRATION_TEXT_MASK = "Спасибо за регистрацию на Miras\nВаш код подтверждения - %s";

    private final JavaMailSender javaMailSender;
    private final HtmlFileReader fileReader;
    @Value("${spring.mail.username}")
    private String emailSender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, HtmlFileReader fileReader) {
        this.javaMailSender = javaMailSender;
        this.fileReader = fileReader;
    }

    public void sendMessageAboutRegistration(String emailReceiver, String code) {
        MimeMessage message = javaMailSender.createMimeMessage();
        String file = fileReader.readHtmlFile("index.html");
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailReceiver);
            helper.setSubject(REGISTRATION_INFO);
            helper.setText(file, true);
            helper.setFrom(emailSender);
            javaMailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
