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

        app.get("/quickByg", ctx -> showQuickBygPage(ctx));
        app.post("/quickByg", ctx -> handleQuickBygPage(ctx));


        app.get("/contact-information", ctx -> showContactInformationPage(ctx));
        app.post("/contact-information", ctx -> handleContactInformationPage(ctx));

        app.get("/confirmation", ctx -> showConfirmationPage(ctx));
        app.post("/confirmation", ctx -> handleConfirmationPage(ctx));
    }

    private static void handleIndexPage(Context ctx) {

    }

    private static void showIndexPage(Context ctx) {
        ctx.render("/index.html");
    }


    public static void getShowIndexPage(Context ctx) {
        showIndexPage(ctx);
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
            String carportTrapezRoof = ctx.formParam("carportTrapezroof");
            boolean redskabsrumCheckbox = ctx.formParam("redskabsrumCheckbox") != null;
            String redskabsrumLength = ctx.formParam("redskabsrumLength");
            String redskabsrumWidth = ctx.formParam("redskabsrumWidth");

            ctx.sessionAttribute("carportWidth", carportWidth);
            ctx.sessionAttribute("carportLength", carportLength);
            ctx.sessionAttribute("carportTrapezRoof", carportTrapezRoof);
            ctx.sessionAttribute("redskabsrumCheckbox", redskabsrumCheckbox);
            ctx.sessionAttribute("redskabsrumLength", redskabsrumLength);
            ctx.sessionAttribute("redskabsrumWidth", redskabsrumWidth);
        }
        ctx.render("/contact-information.html");
    }

    private static void showQuickBygPage(Context ctx) {
        if (ctx.sessionAttribute("carportWidth") != null) {
            ctx.attribute("carportWidth", ctx.sessionAttribute("carportWidth"));
        }
        if (ctx.sessionAttribute("carportLength") != null) {
            ctx.attribute("carportLength", ctx.sessionAttribute("carportLength"));
        }
        if (ctx.sessionAttribute("carportTrapezRoof") != null) {
            ctx.attribute("carportTrapezRoof", ctx.sessionAttribute("carportTrapezRoof"));
        }
        if (ctx.sessionAttribute("redskabsrumCheckbox") != null) {
            ctx.attribute("redskabsrumCheckbox", ctx.sessionAttribute("redskabsrumCheckbox"));
        }
        if (ctx.sessionAttribute("redskabsrumLength") != null) {
            ctx.attribute("redskabsrumLength", ctx.sessionAttribute("redskabsrumLength"));
        }
        if (ctx.sessionAttribute("redskabsrumWidth") != null) {
            ctx.attribute("redskabsrumWidth", ctx.sessionAttribute("redskabsrumWidth"));
        }
        ctx.render("/quick-byg.html");
    }

    public static void getShowQuickBygPage(Context ctx) {
        showQuickBygPage(ctx);
    }


    public static void handleContactInformationPage(Context ctx) {
        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String address = ctx.formParam("address");
        String zipcode = ctx.formParam("zipcode");
        String city = ctx.formParam("city");
        String phone = ctx.formParam("phone");
        String email = ctx.formParam("email");
        boolean samtykke = ctx.formParam("samtykkeCheckbox") != null;
        ctx.sessionAttribute("samtykke", samtykke);

        ctx.sessionAttribute("firstname", firstname);
        ctx.sessionAttribute("lastname", lastname);
        ctx.sessionAttribute("address", address);
        ctx.sessionAttribute("zipcode", zipcode);
        ctx.sessionAttribute("city", city);
        ctx.sessionAttribute("phone", phone);
        ctx.sessionAttribute("email", email);

        ctx.redirect("/confirmation");
    }

    public static void showContactInformationPage(Context ctx) {
        ctx.attribute("firstname", ctx.sessionAttribute("firstname"));
        ctx.attribute("lastname", ctx.sessionAttribute("lastname"));
        ctx.attribute("address", ctx.sessionAttribute("address"));
        ctx.attribute("zipcode", ctx.sessionAttribute("zipcode"));
        ctx.attribute("city", ctx.sessionAttribute("city"));
        ctx.attribute("phone", ctx.sessionAttribute("phone"));
        ctx.attribute("email", ctx.sessionAttribute("email"));
        ctx.attribute("samtykke", ctx.sessionAttribute("samtykke"));

        ctx.render("/contact-information.html");
    }

    public static void handleConfirmationPage(Context ctx) {

    }

    public static void showConfirmationPage(Context ctx) {
        ctx.attribute("carportWidth", ctx.sessionAttribute("carportWidth"));
        ctx.attribute("carportLength", ctx.sessionAttribute("carportLength"));
        ctx.attribute("carportTrapezRoof", ctx.sessionAttribute("carportTrapezRoof"));
        ctx.attribute("redskabsrumCheckbox", ctx.sessionAttribute("redskabsrumCheckbox"));
        ctx.attribute("redskabsrumLength", ctx.sessionAttribute("redskabsrumLength"));
        ctx.attribute("redskabsrumWidth", ctx.sessionAttribute("redskabsrumWidth"));

        ctx.render("/confirmation.html");
    }
}
