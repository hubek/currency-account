package net.hubek.nn.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hubek.nn.controller.request.CreateAccountRequest;
import net.hubek.nn.controller.request.ExchangeCurrencyRequest;
import net.hubek.nn.controller.response.AccountResponse;
import net.hubek.nn.currencyaccount.AccountCreationUseCase;
import net.hubek.nn.currencyaccount.ExchangeCurrencyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.hubek.nn.currencyaccount.AccountMapper.toAccountResponse;

@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountCreationUseCase accountCreationService;
    private final ExchangeCurrencyUseCase exchangeCurrencyService;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestBody CreateAccountRequest accountRequest) {
        AccountResponse accountResponse = toAccountResponse(accountCreationService.createAccount(accountRequest.firstName(), accountRequest.lastName(), accountRequest.plnBalance()));
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String id) {
        return ResponseEntity.ok(toAccountResponse(accountCreationService.getAccount(id)));
    }

    @PostMapping("/{id}/exchange")
    public ResponseEntity<Void> exchange(@PathVariable String id, @RequestBody ExchangeCurrencyRequest exchangeRequest) {
        exchangeCurrencyService.exchange(id, exchangeRequest.amount(), exchangeRequest.fromCurrency(), exchangeRequest.toCurrency());
        return ResponseEntity.ok().build();
    }


}
