package net.hubek.nn.controller.request;

import lombok.NonNull;

public record ExchangeCurrencyRequest(
        long amount,
        @NonNull String fromCurrency,
        @NonNull String toCurrency) {
}
