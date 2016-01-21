package service;

import org.json.simple.JSONObject;

public class ExchangeRatesTestService implements ExchangeRateService {
    @Override
    public JSONObject getAll() {
        JSONObject foo = new JSONObject();
        foo.put("test", "test");
        return foo;
    }

    @Override
    public JSONObject getByCurrency(String currency) {
        return null;
    }
}
