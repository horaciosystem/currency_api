package controller; /**
 * Created by horacio on 1/20/16.
 */

import service.ExchangeRateService;

import static spark.Spark.*;

public class ExchangeRatesController {

    public ExchangeRatesController(ExchangeRateService exchangeService) {

        get("/currencies", (req, res) -> {
            return exchangeService.getAll("all");
        });

        get("/currencies/:id", (req, res) -> {
            return exchangeService.getByCurrency(req.params(":id"));
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
