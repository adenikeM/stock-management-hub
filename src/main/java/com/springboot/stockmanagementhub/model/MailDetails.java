package com.springboot.stockmanagementhub.model;

import lombok.Builder;

@Builder
public record MailDetails(String recipient, String msgBody, String subject) {
}
