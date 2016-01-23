package controller;

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

public class ExchangeRatesControllerTest {
    private static int PORT = 4567;
    private JSONParser parser = new JSONParser();

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
        HttpResponse response = executeRequest("GET", "/rates");
        assertNotNull(response);
        assertEquals(response.statusCode(), 200);
    }

    @Test
    public void contentTypeAsJson() {
        HttpResponse response = executeRequest("GET", "/rates");
        assertNotNull(response);
        assertEquals(response.header("Content-Type"), "application/json");
    }

    @Test
    public void containsQuotesAttribute() throws ParseException {
        HttpResponse response = executeRequest("GET", "/rates");
        assertNotNull(response);
        JSONObject body = null;
        body = (JSONObject) parser.parse(response.body());
        JSONObject quotes = (JSONObject) body.get("quotes");
        assertNotNull(quotes);
    }

    @Test
    public void quotesQuantity() throws ParseException {
        HttpResponse response = executeRequest("GET", "/rates");
        assertNotNull(response);
        JSONObject body = null;
        body = (JSONObject) parser.parse(response.body());
        JSONObject quotes = (JSONObject) body.get("quotes");
        assertNotNull(quotes);
        assertEquals(quotes.size(), 168);
    }

    private static HttpResponse executeRequest(String requestMethod, String path) {
        String url = "http://localhost:" + PORT + path;
        return HttpRequest.get(url).send();
    }

}