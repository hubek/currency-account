package net.hubek.nn.controller;

import lombok.RequiredArgsConstructor;
import net.hubek.nn.controller.request.CreateAccountRequest;
import net.hubek.nn.controller.request.ExchangeCurrencyRequest;
import net.hubek.nn.controller.response.AccountResponse;
import net.hubek.nn.currencyaccount.AccountCreationUseCase;
import net.hubek.nn.currencyaccount.ExchangeCurrencyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static net.hubek.nn.currencyaccount.AccountMapper.toAccountResponse;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountCreationUseCase accountCreationUseCase;
    private final ExchangeCurrencyUseCase exchangeCurrencyUseCase;

    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@RequestHeader("Idempotency-Key") String idempotencyKey, @RequestBody CreateAccountRequest accountRequest) {
//        if (idempotencyKeyStore.contains(idempotencyKey)) {
//            // obsluga idempotencyjnosci, najlepiej jakims aspectem
//            // niepotrzebne gdybysmy mieli unikalne pole, np. pesel
//            // return poprzedni response
//        }

        AccountResponse accountResponse = toAccountResponse(accountCreationUseCase.createAccount(accountRequest.firstName(), accountRequest.lastName(), accountRequest.plnBalance()));
        return ResponseEntity.ok(accountResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable String id) {
        return ResponseEntity.ok(toAccountResponse(accountCreationUseCase.getAccount(id)));
    }

    @PostMapping("/{id}/exchange")
    public ResponseEntity<Void> exchange(@RequestHeader("Idempotency-Key") String idempotencyKey, @PathVariable String id, @RequestBody ExchangeCurrencyRequest exchangeRequest) {
//        if (idempotencyKeyStore.contains(idempotencyKey)) {
//            // obsluga idempotencyjnosci, najlepiej jakims aspectem
//            // return poprzedni response
//        }
        exchangeCurrencyUseCase.exchange(id, exchangeRequest.amount(), exchangeRequest.fromCurrency(), exchangeRequest.toCurrency());
        return ResponseEntity.ok().build();
    }


}
