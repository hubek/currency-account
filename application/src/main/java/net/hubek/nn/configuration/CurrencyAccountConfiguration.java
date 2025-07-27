package net.hubek.nn.configuration;

import net.hubek.nn.currencyaccount.*;
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
    public AccountRepository accountRepository(JpaAccountRepository jpaAccountRepository, AccountEntityMapper accountMapper) {
        return new PostgresAccountRepositoryAdapter(jpaAccountRepository, accountMapper);
    }

    @Bean
    public ExchangeRateProvider exchangeRateProvider() {
        return new NbpExchangeRateProvider();
    }

    @Bean
    public AccountEntityMapper accountMapper() {
        return new AccountEntityMapper();
    }


}
