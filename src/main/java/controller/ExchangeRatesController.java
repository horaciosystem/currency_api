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
            String to = req.queryParams("to");
            double amount = Double.valueOf(req.queryParams("amount"));
            return exchangeService.convert(to, amount);
        });

        after((req, res) -> {
            res.type("application/json");
        });

        exception(IllegalArgumentException.class, (e, req, res) -> {
            res.status(400);
            res.body(e.getMessage());
        });

    }

}
