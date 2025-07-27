package net.hubek.nn.currencyaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum CurrencyCode {
    PLN("PLN"),
    USD("USD"),
    EUR("EUR");

    private final String code;
}
