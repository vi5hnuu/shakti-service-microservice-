package com.vi5hnu.auth_service.events.listeners;

import com.vi5hnu.auth_service.Dto.UpdatePasswordInit;
import com.vi5hnu.auth_service.Entity.user.OtpModel;
import com.vi5hnu.auth_service.enums.OtpReason;
import com.vi5hnu.auth_service.enums.OtpStatus;
import com.vi5hnu.auth_service.events.authEvents.OtpEvent;
import com.vi5hnu.auth_service.events.authEvents.PasswordUpdateInit;
import com.vi5hnu.auth_service.services.user.OtpRepository;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdatePasswordInitEventListener implements ApplicationListener<PasswordUpdateInit> {
    private final JavaMailSender javaMailSender;

    @Override
    public void onApplicationEvent(PasswordUpdateInit passwordUpdateInit) {
        //send the email
        try {
            sendOtpEmail(passwordUpdateInit.getUser().getFirstName(),passwordUpdateInit.getUser().getEmail(),passwordUpdateInit.getOtp());
        } catch (MailException | MessagingException | UnsupportedEncodingException e) {
            System.out.print("Failed to send email :");
            System.out.println(e.getMessage());
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generates a random 6-digit number
        return String.valueOf(otp);
    }

    public void sendOtpEmail(String name,String userEmail,String otp) throws MailException, MessagingException, UnsupportedEncodingException {
        String subject = "Shakti - Otp";
        String senderName = "Shakti";
        String mailContent = "<p> Hi, "+ name+", </p>"+
                "</p>Here is your Otp <strong>"+ otp +"</strong> .</p>"+
                "The otp is valid only for "+OtpModel.EXPIRE_AFTER_MINS+" mins"+
                "<p> Thank you <br> Shakti";
        MimeMessage message = javaMailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("kumarvishnu1619@gmail.com", senderName);
        messageHelper.setTo(userEmail);
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        javaMailSender.send(message);
    }
}
