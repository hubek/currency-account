package net.hubek.nn.currencyaccount;

import java.math.BigDecimal;

public interface ExchangeRateProvider {
    BigDecimal getRate(CurrencyCode from, CurrencyCode to);
}
