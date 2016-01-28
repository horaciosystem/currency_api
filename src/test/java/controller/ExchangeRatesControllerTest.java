package controller;

import boot.CurrencyApiMain;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import org.codehaus.jackson.JsonNode;
import org.json.simple.parser.ParseException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import spark.Spark;
import util.JsonUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class ExchangeRatesControllerTest {
    private static final int PORT = 4567;
    private static final String CURRENCIES = "/currencies";
    private static final String BRL = CURRENCIES + "/BRL";
    private static final String CONVERT = "/convert";
    private Map<String, String> queryParams = new HashMap<String, String>(){{
        put("source", "");
    }};

    @BeforeClass
    public static void beforeClass() throws Exception {
        CurrencyApiMain.main(new String[]{"testing"});
        Spark.awaitInitialization();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Spark.stop();
    }

    /**
    * GET "/currencies"
    **/

    @Test(description = "")
    public void currenciesSuccefully() {
        HttpResponse response = requestCurrencies(CURRENCIES, queryParams);
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void contentTypeAsJson() {
        HttpResponse response = requestCurrencies(CURRENCIES, queryParams);
        assertEquals(response.header("Content-Type"), "application/json");
    }

    @Test
    public void containsQuotesAttribute() throws ParseException, IOException {
        JsonNode body = getCurrienciesBody(CURRENCIES, queryParams);
        JsonNode quotes = body.get("quotes");
        assertNotNull(quotes);
    }

    @Test
    public void quotesQuantity() throws ParseException, IOException {
        JsonNode body = getCurrienciesBody(CURRENCIES, queryParams);
        JsonNode quotes = body.get("quotes");
        assertNotNull(quotes);
        assertEquals(quotes.size(), 168);
    }

    @Test
    public void isDefaultSourceUSD() {
        JsonNode body = getCurrienciesBody(CURRENCIES, queryParams);
        assertEquals(body.get("source").asText(), "USD");
    }

    /**
    * GET /currencies/currency_id
    **/

    @Test
    public void currencySuccefully() {
        HttpResponse response = requestCurrencies(BRL, queryParams);
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void currencyContentTypeAsJson() {
        HttpResponse response = requestCurrencies(BRL, queryParams);
        assertEquals(response.header("Content-Type"), "application/json");
    }

    @Test
    public void currencyContainsQuotesAttribute() throws ParseException, IOException {
        JsonNode body = getCurrienciesBody(BRL, queryParams);
        JsonNode quotes = body.get("quotes");
        assertNotNull(quotes);
    }

    @Test
    public void currencyQuotesQuantity() throws ParseException, IOException {
        JsonNode body = getCurrienciesBody(BRL, queryParams);
        JsonNode quotes = body.get("quotes");
        assertNotNull(quotes);
        assertEquals(quotes.size(), 1);
    }

    @Test
    public void currencyHasDefaultSourceUSD() {
        JsonNode body = getCurrienciesBody(BRL, queryParams);
        assertEquals(body.get("source").asText(), "USD");
    }

    /**
     * GET /convert?to = BRL& amount = 1
     * Return the amount of the specified currency converted in USD
     **/

    @Test
    public void convertSuccefully() {
        Map<String, String> params = queryParamsToConvert();
        HttpResponse response = requestCurrencies(CONVERT, params);
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void convertInvalidQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("to", "");
        params.put("amount", null);
        HttpResponse response = requestCurrencies(CONVERT, params);
        assertEquals(response.statusCode(), 400);
    }

    @Test
    public void convertInvalidCurrencyTo() {
        Map<String, String> params = new HashMap<>();
        params.put("to", "HOR");
        params.put("amount", "10");
        HttpResponse response = requestCurrencies(CONVERT, params);
        assertEquals(400, response.statusCode());
        assertEquals("Currency code not found", response.bodyText());
    }

    @Test(description = "should result the amount convert to specified currency", dataProvider = "stubConversions")
    public void convertToBRL(String amount, double result) {
        Map<String, String> params = new HashMap<>();
        params.put("to", "BRL");
        params.put("amount", amount);
        HttpResponse response = requestCurrencies(CONVERT, params);
        JsonNode body = JsonUtil.responseToJson(response);
        assertEquals(body.get("result").asDouble(), result, 4);
    }

    @DataProvider(name = "stubConversions")
    private Object[][] stubConversions() {
        return new Object[][]{
                {"7", 29.1049},
                {"3", 12.4736},
                {"15", 62.3678},
                {"69", 286.8916},
                {"12.34", 51.3078},
                {"8.92", 37.0880}
        };
    }

    private Map<String, String> queryParamsToConvert() {
        queryParams.put("to", "BRL");
        queryParams.put("amount", "1");
        return queryParams;
    }

    private static HttpResponse executeRequest(String method, String path, Map<String, String> queryParams) {
        HttpRequest request = new HttpRequest();
        return request.
                method(method)
                .port(PORT)
                .path(path)
                .query(queryParams)
                .send();
    }

    private HttpResponse requestCurrencies(String path, Map<String, String> queryParams) {
        return executeRequest("GET", path, queryParams);
    }

    private JsonNode getCurrienciesBody(String path, Map<String, String> queryParams) {
        HttpResponse response = requestCurrencies(path, queryParams);
        return JsonUtil.responseToJson(response);
    }

}