package net.hubek.nn.controller.response;

import lombok.Builder;

@Builder
public record AccountResponse(String id, String firstName, String lastName, long plnBalance, long usdBalance) {
}
