package net.hubek.nn.currencyaccount.repository;

import lombok.RequiredArgsConstructor;
import net.hubek.nn.currencyaccount.Account;
import net.hubek.nn.currencyaccount.AccountRepository;

import java.util.Optional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
public class PostgresAccountRepositoryAdapter implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final ManagedEntityResolver managedEntityResolver;
    private final AccountEntityMapper accountMapper;

    @Override
    public Optional<Account> findById(String id) {
        return jpaAccountRepository.findById(id).map(accountMapper::toAccount);
    }

    @Override
    public Account save(Account account) {
        // potrzebne dla poprawnej obslugi optimistic locking
        AccountEntity managedEntity = managedEntityResolver.getManagedEntity(AccountEntity.class, account.getId());
        if (notManagedEntity(managedEntity)) {
            return accountMapper.toAccount(jpaAccountRepository.save(accountMapper.toAccountEntity(account)));
        } else {
            mergeEntity(managedEntity, account);
            return accountMapper.toAccount(jpaAccountRepository.save(managedEntity));
        }
    }

    private boolean notManagedEntity(AccountEntity managedEntity) {
        return isNull(managedEntity);
    }

    private void mergeEntity(AccountEntity managedEntity, Account account) {
        managedEntity.setFirstName(account.getFirstName());
        managedEntity.setLastName(account.getLastName());
        managedEntity.setBalances(account.getBalances());
    }
}
