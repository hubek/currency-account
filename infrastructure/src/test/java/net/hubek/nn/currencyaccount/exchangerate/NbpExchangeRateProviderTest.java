package net.hubek.nn.currencyaccount.exchangerate;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static net.hubek.nn.currencyaccount.CurrencyCode.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class NbpExchangeRateProviderTest {

    private static final double USD_MID_RATE = 4.0;

    @Mock
    private NbpApiRestClient nbpApiRestClient;

    @InjectMocks
    private NbpExchangeRateProvider nbpExchangeRateProvider;

    @Test
    void shouldGetRateForPlnToUsd() {
        // given
        NbpMidRateResponse response = createNbpMidRateResponse();
        when(nbpApiRestClient.getUsdMidRate()).thenReturn(response);

        BigDecimal expectedRate = BigDecimal.ONE
                .divide(BigDecimal.valueOf(USD_MID_RATE), 4, RoundingMode.HALF_UP);

        // when
        BigDecimal actualRate = nbpExchangeRateProvider.getRate(PLN, USD);

        // then
        assertEquals(expectedRate, actualRate);
        verify(nbpApiRestClient, times(1)).getUsdMidRate();
    }

    @Test
    void shouldGetRateForUsdToPln() {
        // given
        NbpMidRateResponse response = createNbpMidRateResponse();
        when(nbpApiRestClient.getUsdMidRate()).thenReturn(response);

        BigDecimal expectedRate = BigDecimal.valueOf(USD_MID_RATE);

        // when
        BigDecimal actualRate = nbpExchangeRateProvider.getRate(USD, PLN);

        // then
        assertEquals(expectedRate, actualRate);
        verify(nbpApiRestClient, times(1)).getUsdMidRate();
    }

    @Test
    void shouldThrowExceptionWhenUnsupportedCurrencyPair() {
        // given
        NbpMidRateResponse response = createNbpMidRateResponse();
        when(nbpApiRestClient.getUsdMidRate()).thenReturn(response);

        // when and then
        ExchangeRateProviderException exception = assertThrows(
                ExchangeRateProviderException.class,
                () -> nbpExchangeRateProvider.getRate(PLN, EUR)
        );

        assertTrue(exception.getMessage().contains("Failed to fetch NBP exchange rate"));
        verify(nbpApiRestClient, times(1)).getUsdMidRate();
    }

    @Test
    void shouldThrowExceptionWhenApiCallFails() {
        // given
        when(nbpApiRestClient.getUsdMidRate()).thenThrow(new RuntimeException("API connection error"));

        // when and then
        ExchangeRateProviderException exception = assertThrows(
                ExchangeRateProviderException.class,
                () -> nbpExchangeRateProvider.getRate(PLN, USD)
        );

        assertEquals("Failed to fetch NBP exchange rate", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("API connection error", exception.getCause().getMessage());
        verify(nbpApiRestClient, times(1)).getUsdMidRate();
    }

    private NbpMidRateResponse createNbpMidRateResponse() {
        return NbpMidRateResponse.builder()
                .code("USD")
                .rates(List.of(NbpMidRateResponse.RateDetails.builder().mid(USD_MID_RATE).build())
                ).build();
    }
}