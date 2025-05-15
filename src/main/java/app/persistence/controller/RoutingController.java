package app.persistence.controller;

import app.persistence.connection.ConnectionPool;
import app.persistence.documentCreation.SVGgenerator;
import app.persistence.mappers.OfferMapper;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.PriceAndMaterialMapper;
import app.persistence.util.MailSender;
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

        app.get("/seller-contact", ctx -> showSellerContactPage(ctx));
        app.post("/seller-contact", ctx -> handleSellerContactPage(ctx));

        app.get("/accept-offer", ctx -> showAcceptOfferPage(ctx));
        app.post("/accept-offer", ctx -> handleAcceptOfferPage(ctx));

        app.get("/final-accept-offer", ctx -> showFinalAcceptOfferPage(ctx));
        app.post("/final-accept-offer", ctx -> handleFinalAcceptOfferPage(ctx));

        app.get("/quickByg", ctx -> showQuickBygPage(ctx));
        app.post("/quickByg", ctx -> handleQuickBygPage(ctx));
    }

    private static void showIndexPage(Context ctx) {
        ctx.render("/index.html");
    }

    public static void getShowIndexPage(Context ctx) {
        showIndexPage(ctx);
    }

    public static void showFinalAcceptOfferPage(Context ctx) {
        ctx.render("/final-accept-offer.html");
    }

    public static void handleFinalAcceptOfferPage(Context ctx) {
        String action = ctx.formParam("action");
        if ("confirm".equals(action)) {
            ctx.attribute("actionConfirmed", true);
            // evt. send mail her
        } else {
            ctx.attribute("actionConfirmed", false);
        }
        ctx.render("/final-accept-offer.html");
    }

    public static void showAcceptOfferPage(Context ctx) {
        ctx.render("/accept-offer.html");
    }

    public static void handleAcceptOfferPage(Context ctx) {
        String offerIdStr = ctx.sessionAttribute("offerId");

        try {
            int offerId = Integer.parseInt(offerIdStr);

            float salesPriceFromOfferId = app.persistence.mappers.OfferMapper.getSalesPriceFromOfferId(connectionPool, offerId);
            String email = app.persistence.mappers.OfferMapper.getMailFromOfferId(connectionPool, offerId);
            ctx.sessionAttribute("email", email);

            if (salesPriceFromOfferId > 0.1) {
                ctx.sessionAttribute("salesPriceFromOfferId", salesPriceFromOfferId);
                showFinalAcceptOfferPage(ctx);
            } else {
                ctx.status(400);
                ctx.attribute("errorMessage", "Tilbud " + offerId + " findes ikke.");
                ctx.render("accept-offer.html");
            }
        } catch (NumberFormatException e) {
            ctx.status(400);
            ctx.attribute("errorMessage", "Tilbudsnummeret er ugyldigt.");
            ctx.render("accept-offer.html");
        }
    }

    public static void showSellerContactPage(Context ctx) {
        String offerId = ctx.sessionAttribute("offerId");
        ctx.render("/seller-contact.html");
    }

    public static void handleSellerContactPage(Context ctx) {
        String offerId = ctx.formParam("offerId");
        ctx.sessionAttribute("offerId", offerId);
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
