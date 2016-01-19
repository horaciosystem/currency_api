import static spark.Spark.*;


public class CurrencyHatesMain {
  public static void main(String[] args) {
    get("/currency", (req, res) -> {
      return "Hello, World!";
    });

  }
  
}
