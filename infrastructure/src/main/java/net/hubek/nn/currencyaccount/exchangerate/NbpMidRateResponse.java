package net.hubek.nn.currencyaccount.exchangerate;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class NbpMidRateResponse {
    String code;
    List<RateDetails> rates;

    @Value
    @Builder
    static class RateDetails {
        double mid;
    }

    public double getMid() {
        if (rates == null || rates.isEmpty()) {
            throw new ExchangeRateProviderException("No mid rate found for currency: " + code);
        }
        return rates.getFirst().getMid();
    }
}
