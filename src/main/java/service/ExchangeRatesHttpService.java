package service;

import org.json.simple.JSONObject;

public class ExchangeRatesHttpService implements ExchangeRateService {

    @Override
    public JSONObject getAll() {
        JSONObject foo = new JSONObject();
        foo.put("hello", "world");
        return foo;
    }

    @Override
    public JSONObject getByCurrency(String currency) {
        JSONObject foo = new JSONObject();
            foo.put("hello", "world");
        return foo;
    }
}
