package service;
import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONObject;

import java.util.concurrent.ExecutionException;


public interface ExchangeRateService {
    JsonNode getAll() throws ExecutionException;
    JsonNode getByCurrency(String currency) throws ExecutionException;
    JsonNode convert(String to, double amount) throws ExecutionException;
}
