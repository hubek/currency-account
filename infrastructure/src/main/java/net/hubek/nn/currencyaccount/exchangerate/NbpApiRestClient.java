package net.hubek.nn.currencyaccount.exchangerate;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

// dla uproszczenia nie obsluguje po mojej stronie exceptionow z feigna, nalezaloby dorobic konfiguracje z ErrorDecoder'em
@FeignClient(name = "nbp-api", url = "http://api.nbp.pl/api")
public interface NbpApiRestClient {

    // dla uproszczenia logiki bazuje na srednim kursie, nie kursach zakupu/sprzedazy
    @GetMapping("/exchangerates/rates/a/usd/")
    NbpMidRateResponse getUsdMidRate();

}
