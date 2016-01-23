package service;

import model.ResponseError;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class ExchangeRatesTestService implements ExchangeRateService {
    ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode getAll() {
        return readMockFile();
    }

    @Override
    public JsonNode getByCurrency(String currency) {
        return null;
    }

    private JsonNode readMockFile() {
        try {
            return mapper.readValue((Reader) new FileReader("src/main/java/resources/currency-mock.json"), JsonNode.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseError(e.getMessage()).get();
        }
    }

}
