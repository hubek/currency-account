package net.hubek.nn.currencyaccount.exchangerate;

import lombok.Value;

import java.util.List;

@Value
public class NbpMidRateResponse {
    String code;
    List<RateDetails> rates;

    @Value
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
