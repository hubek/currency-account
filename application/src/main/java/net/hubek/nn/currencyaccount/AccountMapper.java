package net.hubek.nn.currencyaccount;

import net.hubek.nn.controller.response.AccountResponse;

public class AccountMapper {
    public static AccountResponse toAccountResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .plnBalance(account.getBalance(CurrencyCode.PLN))
                .usdBalance(account.getBalance(CurrencyCode.USD))
                .build();
    }

}
