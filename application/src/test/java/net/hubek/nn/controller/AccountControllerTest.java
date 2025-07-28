package net.hubek.nn.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.hubek.nn.controller.request.CreateAccountRequest;
import net.hubek.nn.controller.request.ExchangeCurrencyRequest;
import net.hubek.nn.currencyaccount.AccountCreationUseCase;
import net.hubek.nn.currencyaccount.ExchangeCurrencyUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AccountCreationUseCase accountCreationUseCase;

    @MockitoBean
    private ExchangeCurrencyUseCase exchangeCurrencyUseCase;

    @Test
    void shouldExchangeCurrency() throws Exception {
        // given
        String accountId = "acc-123";
        String idempotencyKey = "exchange-key-123";
        ExchangeCurrencyRequest request = new ExchangeCurrencyRequest(
                100L,
                "PLN",
                "USD"
        );

        // when and then
        mockMvc.perform(post("/api/v1/accounts/{id}/exchange", accountId)
                        .header("Idempotency-Key", idempotencyKey)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(exchangeCurrencyUseCase).exchange(
                eq(accountId),
                eq(100L),
                eq("PLN"),
                eq("USD")
        );
    }

    @Test
    void shouldReturn400WhenMissingIdempotencyKey() throws Exception {
        // given
        CreateAccountRequest request = new CreateAccountRequest("Jan", "Kowalski", 1000);

        // when and then
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}