package controller;

import controller.ExchangeRatesController;
import service.ExchangeRatesHttpService;
import service.ExchangeRatesTestService;

public class CurrencyApiMain {
    public static void main(String[] args) {
        String environment = args.length > 0 ? args[0] : "";
        if (environment.trim().isEmpty()) {
            new ExchangeRatesController(new ExchangeRatesHttpService());
        }
        else if ("testing".equals(environment)) {
            new ExchangeRatesController(new ExchangeRatesTestService());
        } else {
            throw new IllegalArgumentException("Unknown environment");
        }
    }

}
