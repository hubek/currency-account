package net.hubek.nn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCaching
@EnableFeignClients
@SpringBootApplication
public class CurrencyAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(CurrencyAccountApplication.class, args);
    }

}
