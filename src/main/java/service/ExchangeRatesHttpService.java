package service;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import model.ResponseError;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public class ExchangeRatesHttpService implements ExchangeRateService {
    private final String ACCESS_KEY = "10dea10860a0cd6fb526efef448f3e99";
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode getAll() {
       return toJson(requestLiveRates());
    }

    @Override
    public JsonNode getByCurrency(String currency) {
        return null;
    }

    private HttpResponse requestLiveRates() {
        return HttpRequest.get("http://apilayer.net/api/live")
                .query("access_key", ACCESS_KEY)
                .send();
    }

    private JsonNode toJson(HttpResponse response) {
        try {
            return mapper.readValue(response.body(), JsonNode.class);
        } catch (Exception e) {
            return new ResponseError(e.getMessage()).get();
        }
    }


}
