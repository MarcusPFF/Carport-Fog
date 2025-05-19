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

        app.get("/seller-admin-login", ctx -> showSellerAdminLogin(ctx));
        app.post("/seller-admin-login", ctx -> handleSellerAdminLogin(ctx));

        app.get("/seller-admin-page", ctx -> showSellerAdminPage(ctx));
        app.post("/seller-admin-page", ctx -> handleSellerAdminPage(ctx));

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

    public static void showSellerAdminLogin(Context ctx) {
        ctx.render("/seller-admin-login.html");
    }

    public static void handleSellerAdminLogin(Context ctx) {
        String sellerCode = ctx.formParam("sellerCode");

        //In purpose this is not a system env, everyone can get to the secret admin page :))
        String verySecretAdminCode = "1111";

        if (sellerCode.equals(verySecretAdminCode)) {
            showSellerAdminPage(ctx);
        } else {
            ctx.status(400);
            String errorMsg = "Forkert sælger kode: " + sellerCode;
            ctx.attribute("errorMessage", errorMsg);
            showSellerAdminLogin(ctx);
        }
    }

    public static void showSellerAdminPage(Context ctx) {
        ctx.render("/seller-admin-page.html");
    }

    public static void handleSellerAdminPage(Context ctx) {

    }

    public static void showFinalAcceptOfferPage(Context ctx) {
        Boolean denied = ctx.sessionAttribute("offerDenied");
        Boolean confirmed = ctx.sessionAttribute("offerConfirmed");
        ctx.attribute("actionDenied", denied != null && denied);
        ctx.attribute("actionConfirmed", confirmed != null && confirmed);
        ctx.render("/final-accept-offer.html");
    }

    public static void handleFinalAcceptOfferPage(Context ctx) {
        String action = ctx.formParam("action");
        if ("confirm".equals(action)) {
            ctx.sessionAttribute("offerConfirmed", true);
            ctx.sessionAttribute("offerDenied", false);
            //Handlingskode her

        } else if ("deny".equals(action)) {
            ctx.sessionAttribute("offerConfirmed", false);
            ctx.sessionAttribute("offerDenied", true);
            ctx.sessionAttribute("offerId", null);
            //Handlingskode her

        }
        ctx.redirect("/final-accept-offer");
    }


    public static void showAcceptOfferPage(Context ctx) {
        ctx.render("/accept-offer.html");
    }

    public static void handleAcceptOfferPage(Context ctx) {
        String offerIdStr = ctx.sessionAttribute("offerId");

        try {
            int offerId = Integer.parseInt(offerIdStr);

            float salesPriceFromOfferId = app.persistence.mappers.OfferMapper.getSalesPriceFromOfferId(connectionPool, offerId);
            String email = app.persistence.mappers.OfferMapper.getCustomerMailFromOfferId(connectionPool, offerId);
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
