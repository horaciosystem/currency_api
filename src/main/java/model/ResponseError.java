package model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import static org.codehaus.jackson.node.JsonNodeFactory.instance;

/**
 * Created by horacio on 1/23/16.
 */
public class ResponseError extends JSONObject {
    private String message;
    private JsonNodeFactory factory = instance;

    public ResponseError(String message) {
        this.message = message;
    }

    public JsonNode get() {
        ObjectNode result = factory.objectNode();
        result.put("message", "Error "+ this.message);
        return result;
    }
}
