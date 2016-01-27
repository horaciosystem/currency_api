package controller; /**
 * Created by horacio on 1/20/16.
 */

import model.ResponseError;
import service.ExchangeRateService;

import static spark.Spark.*;

public class ExchangeRatesController {

    public ExchangeRatesController(ExchangeRateService exchangeService) {

        get("/currencies", (req, res) -> exchangeService.getAll("all"));

        get("/currencies/:id", (req, res) -> exchangeService.getByCurrency(req.params(":id")));

        get("/convert", (req, res) -> {
            String to = String.valueOf(req.attribute("to"));
            double amount = (double) req.attribute("amount");

            if (isValidParams(to, amount)) return exchangeService.convert(to, amount);
            else {
                return 404;
            }
//            halt(401, "Invalid Parameters!");
//            return "";
        });

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(e.getMessage());
        });

    }

    private boolean isValidParams(String to, double amount) {
        return !to.trim().isEmpty() && amount > 0;
    }


}
