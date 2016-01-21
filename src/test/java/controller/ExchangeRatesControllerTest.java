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

    @Test
    public void getAll() throws ParseException {
        UrlResponse response = executeRequest("GET", "/rates");
        assertNotNull(response);
        assertEquals(response.status, 200);
        JSONObject body = null;
        body = (JSONObject) parser.parse(response.body);
        JSONObject quotes = (JSONObject) body.get("quotes");
        System.out.println(quotes);
        System.out.println(response.headers);
        assertEquals(response.headers.get("Content-Type").get(0), "application/json");
        assertNotNull(quotes);
    }

    private static UrlResponse executeRequest(String requestMethod, String path) {
        UrlResponse response = new UrlResponse();
        try {
            URL url = new URL("http://localhost:" + PORT + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);
            connection.connect();
            response.body = IOUtils.toString(connection.getInputStream());
            response.status = connection.getResponseCode();
            response.headers = connection.getHeaderFields();
            connection.disconnect();
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class UrlResponse {
        public Map<String, List<String>> headers;
        private String body;
        private int status;

        public String toString() {
            return "Body: " + body + ", Status " + status;
        }
    }


}