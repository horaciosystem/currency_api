package service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.codehaus.jackson.JsonNode;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ExchangeRatesHttpService implements ExchangeRateService {

    @Override
    public JsonNode getAll(String filter) throws ExecutionException {
        return JsonUtil.responseToJson(cache.get(filter));
    }

    @Override
    public JsonNode getByCurrency(String currency) throws ExecutionException {
        return JsonUtil.responseToJson(cache.get(currency));
    }

    @Override
    public JsonNode convert(String to, double amount) {
        return null;
    }

    private HttpResponse requestCurrencies(String currency) {
        String ACCESS_KEY = "10dea10860a0cd6fb526efef448f3e99";

        if ("all".equals(currency)) {
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
