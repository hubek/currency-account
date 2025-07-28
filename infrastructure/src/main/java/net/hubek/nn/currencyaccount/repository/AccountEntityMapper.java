package net.hubek.nn.currencyaccount.repository;

import net.hubek.nn.currencyaccount.Account;
import net.hubek.nn.currencyaccount.CurrencyCode;

public class AccountEntityMapper {

    public AccountEntity toAccountEntity(Account account) {
        return new AccountEntity(account.getId(), account.getFirstName(), account.getLastName(), account.getBalances().get(CurrencyCode.PLN), account.getBalances().get(CurrencyCode.USD));
    }

    public Account toAccount(AccountEntity accountEntity) {
        return new Account(accountEntity.getId(), accountEntity.getFirstName(), accountEntity.getLastName(), accountEntity.getBalances().get(CurrencyCode.PLN), accountEntity.getBalances().get(CurrencyCode.USD));
    }

}
