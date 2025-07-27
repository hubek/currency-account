package net.hubek.nn.currencyaccount;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PostgresAccountRepositoryAdapter implements AccountRepository {

    private final JpaAccountRepository jpaAccountRepository;
    private final AccountEntityMapper accountMapper;

    @Override
    public Account save(Account account) {
        return accountMapper.toAccount(jpaAccountRepository.save(accountMapper.toAccountEntity(account)));
    }

    @Override
    public Optional<Account> findById(String id) {
        return jpaAccountRepository.findById(id).map(accountMapper::toAccount);
    }

}
