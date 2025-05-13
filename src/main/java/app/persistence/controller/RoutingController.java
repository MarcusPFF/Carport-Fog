package app.persistence.controller;

import app.persistence.connection.ConnectionPool;
import app.persistence.util.MailSender;
import app.persistence.documentCreation.SVGgenerator;
import app.persistence.mappers.OfferMapper;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.PriceAndMaterialMapper;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class RoutingController {
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static OfferMapper offerMapper = new OfferMapper();
    private static OrderMapper orderMapper = new OrderMapper();
    private static PriceAndMaterialMapper priceAndMaterialMapper = new PriceAndMaterialMapper();
    private static MailSender mailSender = new MailSender();
    private static SVGgenerator svgGenerator = new SVGgenerator();

    public static void routes(Javalin app) {
        //skabelon
        app.get("/index", ctx -> showIndexPage(ctx));
        app.post("/index", ctx -> handleIndexPage(ctx));

        app.get("/quickByg", ctx -> showQuickBygPage(ctx));
        app.post("/quickByg", ctx -> handleQuickBygPage(ctx));

    }

    private static void handleIndexPage(Context ctx) {
    }

    private static void showIndexPage(Context ctx) {
        ctx.render("/index.html");
    }

    public static void getShowIndexPage(Context ctx) {
        showIndexPage(ctx);
    }

    private static void handleQuickBygPage(Context ctx) {
        if (ctx.formParam("carportWidth") != null) {
            String carportWidth = ctx.formParam("carportWidth");
            String carportLength = ctx.formParam("carportLength");
            String carportTrapezRoof = ctx.formParam("carportTrapezRoof");
            boolean redskabsrumCheckbox = ctx.formParam("redskabsrumCheckbox") != null;
            String redskabsrumLength = ctx.formParam("redskabsrumLength");
            String redskabsrumWidth = ctx.formParam("redskabsrumWidth");

            // Save choices in session attributes
            ctx.sessionAttribute("carportWidth", carportWidth);
            ctx.sessionAttribute("carportLength", carportLength);
            ctx.sessionAttribute("carportTrapezRoof", carportTrapezRoof);
            ctx.sessionAttribute("redskabsrumCheckbox", redskabsrumCheckbox);
            ctx.sessionAttribute("redskabsrumLength", redskabsrumLength);
            ctx.sessionAttribute("redskabsrumWidth", redskabsrumWidth);

        }
        ctx.render("/quick-byg.html");
    }

    private static void showQuickBygPage(Context ctx) {
        ctx.render("/quick-byg.html");
    }

    public static void getShowQuickBygPage(Context ctx) {
        showQuickBygPage(ctx);
    }

}
