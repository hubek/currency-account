package net.hubek.nn.controller.request;

import lombok.NonNull;

public record CreateAccountRequest(
        @NonNull String firstName,
        @NonNull String lastName,
        long plnBalance) {
}
