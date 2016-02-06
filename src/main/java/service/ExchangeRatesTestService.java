package service;

import model.ConversionResult;
import model.CurrencyConverter;
import model.ResponseError;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import util.JsonUtil;

import java.io.FileReader;
import java.io.Reader;

public class ExchangeRatesTestService implements ExchangeRateService {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode getAll() {
        return readMockFile("currencies-mock.json");
    }

    @Override
    public JsonNode getByCurrency(String currency) {
        return readMockFile("currency-brl-mock.json");
    }

    @Override
    public JsonNode convert(String to, double amount) {
        ConversionResult conversionResult = CurrencyConverter.convert(to, amount, readMockFile("currencies-mock.json"));
        return JsonUtil.toJson(conversionResult);
    }

    private JsonNode readMockFile(String fileName) {
        try {
            return mapper.readValue((Reader) new FileReader("src/main/java/resources/"+fileName), JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseError(e.getMessage()).get();
        }
    }

}
