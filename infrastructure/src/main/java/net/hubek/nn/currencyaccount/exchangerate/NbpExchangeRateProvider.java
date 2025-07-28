package net.hubek.nn.currencyaccount.exchangerate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hubek.nn.currencyaccount.CurrencyCode;
import net.hubek.nn.currencyaccount.ExchangeRateProvider;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.function.Function;

import static net.hubek.nn.currencyaccount.CurrencyCode.PLN;
import static net.hubek.nn.currencyaccount.CurrencyCode.USD;

@Slf4j
@RequiredArgsConstructor
public class NbpExchangeRateProvider implements ExchangeRateProvider {

    private static final Map<Pair<CurrencyCode, CurrencyCode>, Function<Double, BigDecimal>> RATE_RESOLVERS = Map.of(
            Pair.of(PLN, USD), rate -> BigDecimal.ONE.divide(BigDecimal.valueOf(rate), 4, RoundingMode.HALF_UP),
            Pair.of(USD, PLN), BigDecimal::valueOf
    );

    private final NbpApiRestClient nbpApiRestClient;

    @Cacheable(value = "exchangeRate", key = "{#from, #to}")
    public BigDecimal getRate(CurrencyCode from, CurrencyCode to) {
        log.debug("Fetching NBP exchange rate for {} -> {}", from, to);

        try {
            NbpMidRateResponse usdMidRate = nbpApiRestClient.getUsdMidRate();
            log.info("Fetched NBP exchange rate: {}", usdMidRate);
            return resolveRate(from, to, usdMidRate.getMid());
        } catch (Exception e) {
            throw new ExchangeRateProviderException("Failed to fetch NBP exchange rate", e);
        }
    }

    private BigDecimal resolveRate(CurrencyCode from, CurrencyCode to, double usdMidRate) {
        Function<Double, BigDecimal> resolver = RATE_RESOLVERS.get(Pair.of(from, to));
        if (resolver == null) {
            throw new ExchangeRateProviderException("Unsupported currency pair: " + from + " -> " + to);
        }
        return resolver.apply(usdMidRate);
    }
}
