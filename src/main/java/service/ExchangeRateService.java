package service;

import org.json.simple.JSONObject;


public interface ExchangeRateService {
    JSONObject getAll();
    JSONObject getByCurrency(String currency);
}
