package com.springboot.stockmanagementhub.model;

import lombok.Builder;

@Builder
public record EmailDetails(String recipient, String msgBody, String subject) {
}
