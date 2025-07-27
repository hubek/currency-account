package net.hubek.nn.currencyaccount;

public interface ExchangeCurrencyUseCase {
    void exchange(String accountId, long amount, String fromCurrency, String toCurrency);
}
