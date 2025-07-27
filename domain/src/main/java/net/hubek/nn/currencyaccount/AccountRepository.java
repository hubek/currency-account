package net.hubek.nn.currencyaccount;

import java.util.Optional;

public interface AccountRepository {
    Account save(Account account);

    Optional<Account> findById(String accountId);
}
