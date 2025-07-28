package net.hubek.nn.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse(
        String id,
        String firstName,
        String lastName,
        BigDecimal plnBalance,
        BigDecimal usdBalance) {
}
