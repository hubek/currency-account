package net.hubek.nn.configuration;

import jakarta.persistence.EntityManager;
import net.hubek.nn.currencyaccount.*;
import net.hubek.nn.currencyaccount.exchangerate.NbpApiRestClient;
import net.hubek.nn.currencyaccount.exchangerate.NbpExchangeRateProvider;
import net.hubek.nn.currencyaccount.repository.AccountEntityMapper;
import net.hubek.nn.currencyaccount.repository.JpaAccountRepository;
import net.hubek.nn.currencyaccount.repository.ManagedEntityResolver;
import net.hubek.nn.currencyaccount.repository.PostgresAccountRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CurrencyAccountConfiguration {

    @Bean
    public AccountCreationUseCase accountCreationUseCase(AccountRepository accountRepository) {
        return new AccountCreationUseCaseImpl(accountRepository);
    }

    @Bean
    public ExchangeCurrencyUseCase exchangeCurrencyUseCase(AccountRepository accountRepository, ExchangeRateProvider exchangeRateProvider) {
        return new ExchangeCurrencyUseCaseImpl(accountRepository, exchangeRateProvider);
    }

    @Bean
    public ManagedEntityResolver managedEntityResolver(EntityManager entityManager) {
        return new ManagedEntityResolver(entityManager);
    }

    @Bean
    public AccountRepository accountRepository(JpaAccountRepository jpaAccountRepository, AccountEntityMapper accountMapper, ManagedEntityResolver managedEntityResolver) {
        return new PostgresAccountRepositoryAdapter(jpaAccountRepository, managedEntityResolver, accountMapper);
    }

    @Bean
    public ExchangeRateProvider exchangeRateProvider(NbpApiRestClient nbpApiRestClient) {
        return new NbpExchangeRateProvider(nbpApiRestClient);
    }

    @Bean
    public AccountEntityMapper accountMapper() {
        return new AccountEntityMapper();
    }


}
