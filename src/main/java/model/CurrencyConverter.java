package model;

import org.codehaus.jackson.JsonNode;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;

/**
 * Created by horacio on 1/26/16.
 */
public class CurrencyConverter {

    public static ConversionResult convert(String currency, double amount, JsonNode currencies) {
        String currencyTo = "USD"+currency;
        JsonNode quotes = currencies.get("quotes");
        if (!quotes.has(currencyTo)) {
            throw new InvalidParameterException("Currency code not found");
        }

        String timestamp = currencies.get("timestamp").asText();
        double currencyToValue = quotes.findValue(currencyTo).asDouble();
        double result = amount * currencyToValue;

        return new ConversionResult(round(result, 4), timestamp);
    }

    public static float round(double d, int decimalPlace) {
        return BigDecimal.valueOf(d).setScale(decimalPlace,BigDecimal.ROUND_HALF_UP).floatValue();
    }

}
