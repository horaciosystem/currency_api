package controller;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import spark.Spark;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import util.JsonUtil;

public class ExchangeRatesControllerTest {
    private static final int PORT = 4567;
    private static final String CURRENCIES = "/currencies";

    @BeforeClass
    public static void beforeClass() throws Exception {
        CurrencyApiMain.main(new String[]{"testing"});
        Spark.awaitInitialization();
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Spark.stop();
    }

    /*
    * GET "/rates"
    */

    @Test
    public void respondsSuccefully() {
        HttpResponse response = executeRequest("GET", CURRENCIES);
        assertNotNull(response);
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void contentTypeAsJson() {
        HttpResponse response = executeRequest("GET", CURRENCIES);
        assertNotNull(response);
        assertEquals(response.header("Content-Type"), "application/json");
    }

    @Test
    public void containsQuotesAttribute() throws ParseException, IOException {
        HttpResponse response = executeRequest("GET", CURRENCIES);
        assertNotNull(response);
        JsonNode body = JsonUtil.responseToJson(response);
        JsonNode quotes = body.get("quotes");
        assertNotNull(quotes);
    }

    @Test
    public void quotesQuantity() throws ParseException, IOException {
        HttpResponse response = executeRequest("GET", CURRENCIES);
        assertNotNull(response);
        JsonNode body = JsonUtil.responseToJson(response);
        JsonNode quotes = body.get("quotes");
        assertNotNull(quotes);
        assertEquals(quotes.size(), 168);
    }

    private static HttpResponse executeRequest(String method, String path) {
        HttpRequest request = new HttpRequest();
        return request.
                method(method)
                .port(PORT)
                .path(path).send();
    }

}