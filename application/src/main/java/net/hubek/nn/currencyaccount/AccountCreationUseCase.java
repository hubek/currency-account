package net.hubek.nn.currencyaccount;

public interface AccountCreationUseCase {
    Account createAccount(String firstName, String lastName, long initialPlnBalance);

    Account getAccount(String accountId);
}
