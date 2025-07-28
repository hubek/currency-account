package net.hubek.nn.currencyaccount;

import lombok.NonNull;
import lombok.Value;
import net.hubek.nn.currencyaccount.exception.AccountCreationException;
import net.hubek.nn.currencyaccount.exception.CurrencyExchangeException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

import static net.hubek.nn.currencyaccount.CurrencyCode.PLN;
import static net.hubek.nn.currencyaccount.CurrencyCode.USD;

@Value
public class Account {
    String id;
    String firstName;
    String lastName;
    HashMap<CurrencyCode, BigDecimal> balances;

    public Account(@NonNull String id, @NonNull String firstName, @NonNull String lastName, @NonNull BigDecimal plnBalance, @NonNull BigDecimal usdBalance) {
        validateNotNegativeAmount(plnBalance, PLN);
        validateNotNegativeAmount(usdBalance, USD);
        validateUserData(firstName, lastName);

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.balances = new HashMap<>();
        this.balances.put(PLN, plnBalance.setScale(2, RoundingMode.HALF_UP));
        this.balances.put(USD, usdBalance.setScale(2, RoundingMode.HALF_UP));
    }

    public BigDecimal exchange(BigDecimal amount, CurrencyCode fromCurrency, CurrencyCode toCurrency, BigDecimal exchangeRate) {
        validateCorrectCurrencyCodes(fromCurrency, toCurrency);

        BigDecimal fromCurrencyBalance = balances.get(fromCurrency);
        validateSufficientBalance(fromCurrencyBalance, amount);

        balances.put(fromCurrency, fromCurrencyBalance.subtract(amount).setScale(2, RoundingMode.HALF_UP));

        BigDecimal exchangedAmount = amount.multiply(exchangeRate).setScale(2, RoundingMode.HALF_UP);
        balances.put(toCurrency, balances.get(toCurrency).add(exchangedAmount).setScale(2, RoundingMode.HALF_UP));

        return exchangedAmount;
    }

    public BigDecimal getBalance(CurrencyCode currencyCode) {
        return balances.get(currencyCode);
    }

    private void validateNotNegativeAmount(BigDecimal amount, CurrencyCode currencyCode) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new AccountCreationException("Amount cannot be negative, currency: " + currencyCode + "account: " + this.id);
        }
    }

    private void validateUserData(String firstName, String lastName) {
        if (firstName.isBlank() || lastName.isBlank()) {
            throw new AccountCreationException("First name and last name cannot be blank, account: " + this.id);
        }
    }

    private void validateCorrectCurrencyCodes(CurrencyCode fromCurrency, CurrencyCode toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            throw new CurrencyExchangeException("Cannot exchange to the same currency: " + fromCurrency + "account: " + this.id);
        }
        if (!balances.containsKey(fromCurrency) || !balances.containsKey(toCurrency)) {
            throw new CurrencyExchangeException("Exchange is not possible for account " + this.id + ", currencies: " + fromCurrency + ", " + toCurrency);
        }
    }

    private void validateSufficientBalance(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new CurrencyExchangeException("Insufficient balance for exchange, account: " + this.id);
        }
    }

}
