package model;

import org.codehaus.jackson.JsonNode;

import java.security.InvalidParameterException;

/**
 * Created by horacio on 1/26/16.
 */
public class CurrencyConverter {

    public static ConversionResult convert(String currency, double amount, JsonNode currencies) {
        String currencyTo = "USD"+currency;
        JsonNode quotes = currencies.get("quotes");
        if (!quotes.has(currencyTo)) {
            throw new InvalidParameterException("Currency Not Found");
        }

        String timestamp = currencies.get("timestamp").asText();
        double currencyToValue = quotes.findValue(currencyTo).asDouble();
        double result = amount * currencyToValue;
        return new ConversionResult(result, timestamp);
    }

}
