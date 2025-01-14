package com.vi5hnu.auth_service.events.listeners;

import com.vi5hnu.auth_service.events.authEvents.PasswordUpdateComplete;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import java.io.UnsupportedEncodingException;
@Slf4j
@Component
public class PasswordUpdateCompleteEventListener implements ApplicationListener<PasswordUpdateComplete> {
    private final JavaMailSender javaMailSender;

    public PasswordUpdateCompleteEventListener(JavaMailSender javaMailSender){
        this.javaMailSender=javaMailSender;
    }

    @Override
    public void onApplicationEvent(PasswordUpdateComplete event) {
        //send the email
        try {
            sendVerificationEmail(event.getUserModel().getFirstName(),event.getUserModel().getLastName(),event.getUserModel().getEmail());
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendVerificationEmail(String firstName,String lastName,String userEmail) throws MessagingException, UnsupportedEncodingException {
        String subject = "Shakti - Security Alert";
        String senderName = "Shakti";
        String mailContent = "<p> Hi, "+ firstName+" "+ lastName + ", </p>"+
                "<p>Your password has been changed.</p>"+
                "<p> Thank you for using Shakti";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("kumarvishnu1619@gmail.com", senderName);
        messageHelper.setTo(userEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
