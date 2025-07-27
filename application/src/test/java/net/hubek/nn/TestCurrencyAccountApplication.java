package net.hubek.nn;

import org.springframework.boot.SpringApplication;

public class TestCurrencyAccountApplication {

    public static void main(String[] args) {
        SpringApplication.from(CurrencyAccountApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
