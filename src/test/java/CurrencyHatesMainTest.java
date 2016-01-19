import org.junit.After;
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

import static org.junit.Assert.*;
import static spark.Spark.after;
import static spark.Spark.before;

public class CurrencyHatesMainTest {
    private static int PORT = 4567;

    @AfterClass
    public static void tearDown() {
        Spark.stop();
    }

    @After
    public void clearBooks() {

    }

    @BeforeClass
    public static void setup() {
        before((request, response) -> {
            response.header("FOZ", "BAZ");
        });

        CurrencyHatesMain.main(null);

        after((request, response) -> {
            response.header("FOO", "BAR");
        });

        Spark.awaitInitialization();
    }

    @Test
    public void test() {
        UrlResponse response = doMethod("GET", "/currency", "");
        System.out.print("response " + response);
        assertNotNull(response);
//        String body = response.body.trim();
//        assertNotNull(body);
//        assertTrue(Integer.valueOf(body) > 0);
//        assertEquals(200, response.status);
//        assertTrue(response.body.contains(bookId));


    }

    private static UrlResponse doMethod(String requestMethod, String path, String body) {
        UrlResponse response = null;
        try {
            response = getResponse(requestMethod, path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    private static UrlResponse getResponse(String requestMethod, String path)
            throws MalformedURLException, IOException, ProtocolException {
        UrlResponse response = new UrlResponse();
        URL url = new URL("http://localhost:" + PORT + path);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(requestMethod);
        connection.connect();
        response.body = IOUtils.toString(connection.getInputStream());
        response.status = connection.getResponseCode();
        response.headers = connection.getHeaderFields();

        return response;
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