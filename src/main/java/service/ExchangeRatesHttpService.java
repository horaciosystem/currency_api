package service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import model.ConversionResult;
import model.CurrencyConverter;
import org.codehaus.jackson.JsonNode;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ExchangeRatesHttpService implements ExchangeRateService {

    public static final String ALL = "all";

    @Override
    public JsonNode getAll() throws ExecutionException {
        return JsonUtil.responseToJson(cache.get(ALL));
    }

    @Override
    public JsonNode getByCurrency(String currency) throws ExecutionException {
        return JsonUtil.responseToJson(cache.get(currency));
    }

    @Override
    public JsonNode convert(String to, double amount) throws ExecutionException {
        ConversionResult conversionResult = CurrencyConverter.convert(to, amount, this.getAll());
        return JsonUtil.toJson(conversionResult);
    }

    private HttpResponse requestCurrencies(String currency) {
        String ACCESS_KEY = "10dea10860a0cd6fb526efef448f3e99";

        if (ALL.equals(currency)) {
            return HttpRequest.get("http://apilayer.net/api/live")
                    .query("access_key", ACCESS_KEY)
                    .send();
        } else {
            return HttpRequest.get("http://apilayer.net/api/live")
                    .query("access_key", ACCESS_KEY)
                    .query("currencies", currency)
                    .send();
        }
    }

    private LoadingCache<String, HttpResponse> cache = CacheBuilder.newBuilder()
            .maximumSize(170)
            .expireAfterAccess(10, TimeUnit.MINUTES)
            .recordStats()
            .build(new CacheLoader<String, HttpResponse>() {
                @Override
                public HttpResponse load(String filter) throws IOException {
                    return requestCurrencies(filter);
                }
            });

}
