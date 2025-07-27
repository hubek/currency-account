package net.hubek.nn.currencyaccount;

import java.math.BigDecimal;

public class NbpExchangeRateProvider implements ExchangeRateProvider {
    public BigDecimal getRate(CurrencyCode from, CurrencyCode to) {
        return new BigDecimal("4.00");
    }
}
