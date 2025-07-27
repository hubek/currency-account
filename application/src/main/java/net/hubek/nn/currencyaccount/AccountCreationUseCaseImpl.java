package net.hubek.nn.currencyaccount;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
public class AccountCreationUseCaseImpl implements AccountCreationUseCase {

    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(String firstName, String lastName, long initialPlnBalance) {
        String accountId = UUID.randomUUID().toString();
        Account account = new Account(accountId, firstName, lastName, BigDecimal.valueOf(initialPlnBalance), BigDecimal.ZERO);
        return accountRepository.save(account);
    }

    @Override
    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
    }

}
