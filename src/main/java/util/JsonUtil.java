package util;

import jodd.http.HttpResponse;
import model.ResponseError;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by horacio on 1/23/16.
 */
public class JsonUtil {
    static ObjectMapper mapper = new ObjectMapper();

    public static JsonNode responseToJson(HttpResponse response) {
        try {
            return mapper.readValue(response.body(), JsonNode.class);
        } catch (Exception e) {
            return new ResponseError(e.getMessage()).get();
        }
    }

}
