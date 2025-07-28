package net.hubek.nn.currencyaccount;

import net.hubek.nn.currencyaccount.exception.AccountCreationException;
import net.hubek.nn.currencyaccount.exception.CurrencyExchangeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static net.hubek.nn.currencyaccount.CurrencyCode.PLN;
import static net.hubek.nn.currencyaccount.CurrencyCode.USD;
import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void shouldExchangeCurrencyAndUpdateBalances() {
        // given
        Account account = new Account("1", "Jan", "Kowalski", new BigDecimal("100.00"), new BigDecimal("50.00"));
        BigDecimal amount = new BigDecimal("20.00");
        BigDecimal exchangeRate = new BigDecimal("2.00");

        // when
        BigDecimal exchangedAmount = account.exchange(amount, PLN, USD, exchangeRate);

        // then
        assertEquals(new BigDecimal("80.00"), account.getBalance(PLN));
        assertEquals(new BigDecimal("90.00"), account.getBalance(USD));
        assertEquals(new BigDecimal("40.00"), exchangedAmount);
    }

    @Test
    void shouldThrowExceptionWhenInsufficientBalance() {
        // given
        Account account = new Account("2", "Anna", "Nowak", new BigDecimal("10.00"), new BigDecimal("30.00"));
        BigDecimal amount = new BigDecimal("20.00");
        BigDecimal exchangeRate = new BigDecimal("1.50");

        // when // then
        CurrencyExchangeException ex = assertThrows(
                CurrencyExchangeException.class,
                () -> account.exchange(amount, PLN, USD, exchangeRate)
        );
        assertTrue(ex.getMessage().contains("Insufficient balance"));
    }

    @Test
    void shouldThrowExceptionWhenExchangingToSameCurrency() {
        // given
        Account account = new Account("3", "Piotr", "Zieliński", new BigDecimal("50.00"), new BigDecimal("50.00"));
        BigDecimal amount = new BigDecimal("10.00");
        BigDecimal exchangeRate = new BigDecimal("1.00");

        // when // then
        CurrencyExchangeException ex = assertThrows(
                CurrencyExchangeException.class,
                () -> account.exchange(amount, PLN, PLN, exchangeRate)
        );
        assertTrue(ex.getMessage().contains("Cannot exchange to the same currency"));
    }

    @ParameterizedTest
    @CsvSource({
            "PLN, EUR",
            "EUR, PLN"
    })
    void shouldThrowExceptionWhenExchangingUnsupportedCurrency(CurrencyCode from, CurrencyCode to) {
        Account account = new Account("3", "Piotr", "Zieliński", new BigDecimal("50.00"), new BigDecimal("50.00"));
        BigDecimal amount = new BigDecimal("10.00");
        BigDecimal exchangeRate = new BigDecimal("1.00");

        CurrencyExchangeException ex = assertThrows(
                CurrencyExchangeException.class,
                () -> account.exchange(amount, from, to, exchangeRate)
        );
        assertTrue(ex.getMessage().contains("Exchange is not possible for account"));
    }

    @Test
    void shouldThrowExceptionWhenNegativeInitialBalance() {
        assertThrows(
                AccountCreationException.class,
                () -> new Account("4", "Kasia", "Wiśniewska", new BigDecimal("-1.00"), new BigDecimal("10.00"))
        );
    }

    @Test
    void shouldThrowExceptionWhenBlankUserData() {
        assertThrows(
                AccountCreationException.class,
                () -> new Account("4", "", "Wiśniewska", new BigDecimal("1.00"), new BigDecimal("10.00"))
        );
        assertThrows(
                AccountCreationException.class,
                () -> new Account("4", "Kasia", "", new BigDecimal("1.00"), new BigDecimal("10.00"))
        );
    }

    @Test
    void shouldReturnCorrectBalanceForCurrency() {
        // given
        Account account = new Account("5", "Marek", "Lewandowski", new BigDecimal("200.00"), new BigDecimal("300.00"));

        // when
        BigDecimal plnBalance = account.getBalance(PLN);
        BigDecimal usdBalance = account.getBalance(USD);

        // then
        assertEquals(new BigDecimal("200.00"), plnBalance);
        assertEquals(new BigDecimal("300.00"), usdBalance);
    }
}