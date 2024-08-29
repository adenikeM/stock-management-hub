package com.springboot.stockmanagementhub.service;

import com.springboot.stockmanagementhub.model.EmailDetails;
import com.springboot.stockmanagementhub.model.MailDetails;

public interface EmailService {
    void sendMail(EmailDetails details);

    void sendRefundEmail(MailDetails details);

}
