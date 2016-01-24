package service;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import model.ResponseError;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import util.JsonUtil;

public class ExchangeRatesHttpService implements ExchangeRateService {
    private final String ACCESS_KEY = "10dea10860a0cd6fb526efef448f3e99";

    @Override
    public JsonNode getAll() {
       return JsonUtil.responseToJson(requestLiveRates());
    }

    @Override
    public JsonNode getByCurrency(String currency) {
        return JsonUtil.responseToJson(requestSpecifiedCurrency(currency));
    }

    private HttpResponse requestLiveRates() {
        return HttpRequest.get("http://apilayer.net/api/live")
                .query("access_key", ACCESS_KEY)
                .send();
    }

    private HttpResponse requestSpecifiedCurrency(String currency) {
        return HttpRequest.get("http://apilayer.net/api/live")
                .query("access_key", ACCESS_KEY)
                .query("currencies", currency)
                .send();
    }

}
