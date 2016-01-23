package service;
import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;


public interface ExchangeRateService {
    JsonNode getAll();
    JsonNode getByCurrency(String currency);
}
