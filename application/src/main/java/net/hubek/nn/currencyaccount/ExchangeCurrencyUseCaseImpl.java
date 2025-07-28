package net.hubek.nn.currencyaccount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hubek.nn.currencyaccount.exception.AccountNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
public class ExchangeCurrencyUseCaseImpl implements ExchangeCurrencyUseCase {
    private final AccountRepository accountRepository;
    private final ExchangeRateProvider exchangeRateProvider;

    @Override
    @Transactional
    public void exchange(String accountId, long amount, String fromCurrency, String toCurrency) {
        log.debug("Trying to exchange currency for account: {}, amount: {}, {} -> {}", accountId, amount, fromCurrency, toCurrency);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
        log.trace("Account found: {}", account);

        BigDecimal exchangeRate = exchangeRateProvider.getRate(CurrencyCode.valueOf(fromCurrency), CurrencyCode.valueOf(toCurrency));
        log.trace("Exchange rate: {}", exchangeRate);

        account.exchange(BigDecimal.valueOf(amount), CurrencyCode.valueOf(fromCurrency), CurrencyCode.valueOf(toCurrency), exchangeRate);

        accountRepository.save(account);
        log.info("Successfully exchanged currency for account: {}, amount: {}, {} -> {}, exchange rate: {}, result amount: {}", accountId, amount, fromCurrency, toCurrency, exchangeRate, account.getBalances());
    }

}
