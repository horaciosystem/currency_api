package controller;

import controller.ExchangeRatesController;
import service.ExchangeRateService;
import service.ExchangeRatesHttpService;
import service.ExchangeRatesTestService;

public class CurrencyApiMain {
    public static void main(String[] args) {
        ExchangeRateService service = null;
        String environment = args.length > 0 ? args[0] : "";
        if (environment.trim().isEmpty()) {
            service = new ExchangeRatesHttpService();
        }
        else if ("testing".equals(environment)) {
            service = new ExchangeRatesTestService();
        } else {
            throw new IllegalArgumentException("Unknown environment");
        }

        new ExchangeRatesController(service);
    }

}
