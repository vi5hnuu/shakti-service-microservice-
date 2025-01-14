package com.vi5hnu.auth_service.events.listeners;

import com.vi5hnu.auth_service.events.authEvents.RegistrationVerificationEvent;
import com.vi5hnu.auth_service.utils.Utils;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegistrationVerificationEventListener implements ApplicationListener<RegistrationVerificationEvent> {
    private final JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(RegistrationVerificationEvent event) {
        //build verification url to be sent to user
        final String url = event.getVerificationUrl()+ "?token=" + event.getToken();
        //send the email
        try {
            sendVerificationEmail(url,event.getUserModel().getFirstName(),event.getUserModel().getLastName(),event.getUserModel().getEmail());
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            System.out.print("Faild to send email :");
            System.out.println(e.getMessage());
        }
    }

    public void sendVerificationEmail(String url,String firstName,String lastName,String userEmail) throws MailException, MessagingException, UnsupportedEncodingException {
        String subject = "Shakti - Account Verification";
        String senderName = "Shakti";
        String mailContent = "<p> Hi, "+ firstName+" "+ lastName + ", </p>"+
                "<p>Thank you for registering with Shakti ,"+"<br>" +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Shakti User Registration";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("kumarvishnu1619@gmail.com", senderName);
        messageHelper.setTo(userEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
