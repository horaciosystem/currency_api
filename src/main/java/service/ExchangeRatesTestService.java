package service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class ExchangeRatesTestService implements ExchangeRateService {
    JSONParser parser = new JSONParser();

    @Override
    public JSONObject getAll() {
        return readMockFile();
    }

    @Override
    public JSONObject getByCurrency(String currency) {
        return null;
    }

    private JSONObject readMockFile() {
        try {
            return (JSONObject) parser.parse(new FileReader("src/test/resources/currency-mock.json"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

}
