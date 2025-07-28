package net.hubek.nn.currencyaccount.repository;

import jakarta.persistence.*;
import lombok.Data;
import net.hubek.nn.currencyaccount.CurrencyCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Map;

import static net.hubek.nn.currencyaccount.CurrencyCode.PLN;
import static net.hubek.nn.currencyaccount.CurrencyCode.USD;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = AccountBalancesConverter.class)
    private Map<CurrencyCode, BigDecimal> balances;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    public AccountEntity() {
    }

    public AccountEntity(String id, String firstName, String lastName, BigDecimal plnBalance, BigDecimal usdBalance) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.setBalances(Map.of(PLN, plnBalance, USD, usdBalance));
    }
}