package controller;

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

import static org.junit.Assert.assertNotNull;

public class ExchangeRatesControllerTest {
    private static int PORT = 4567;

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
    public void getAll() {
        UrlResponse response = executeRequest("GET", "/rates");
        System.out.println(response);
        assertNotNull(response);
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
            return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return new UrlResponse();
        } catch (ProtocolException e) {
            e.printStackTrace();
            return new UrlResponse();
        } catch (IOException e) {
            e.printStackTrace();
            return new UrlResponse();
        }
    }

    private static class UrlResponse {
        public Map<String, List<String>> headers;
        private String body;
        private int status;

        public String toString() {
            return "Body: "+ body + ", Status " + status;
        }
    }


}