package net.hubek.nn.currencyaccount;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hubek.nn.currencyaccount.exception.AccountNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class AccountCreationUseCaseImpl implements AccountCreationUseCase {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public Account createAccount(String firstName, String lastName, long initialPlnBalance) {
        String accountId = UUID.randomUUID().toString();
        Account account = new Account(accountId, firstName, lastName, BigDecimal.valueOf(initialPlnBalance), BigDecimal.ZERO);
        log.debug("Created account prepared to save: {}", account);

        Account savedAccount = accountRepository.save(account);
        log.info("Account successfully created: {}", savedAccount);

        return savedAccount;
    }

    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String accountId) {
        return accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found: " + accountId));
    }

}
