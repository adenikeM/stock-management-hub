package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.EmailDetails;
import com.springboot.stockmanagementhub.model.MailDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService  {
    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(EmailDetails details) {
        log.info("Enter send mail service method {}", details);
        try{
            log.info("log javamailsender {}", javaMailSender);
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("horythorkehadenike@gmail.com");
            message.setTo(details.recipient());
            message.setText(details.msgBody());
            message.setSubject(details.subject());
            log.info("print message {} ", message);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error occurred while sending email: {}", e.getMessage());

        }
    }

    public void sendRefundEmail(MailDetails details) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("horythorkehadenike@gmail.com");
            message.setTo(details.recipient());
            message.setSubject(details.subject());
            message.setText(details.msgBody());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
