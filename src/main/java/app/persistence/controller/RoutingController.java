package app.persistence.controller;

import app.entities.CustomerInformation;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.documentCreation.SVGgenerator;
import app.persistence.mappers.OfferMapper;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.PriceAndMaterialMapper;
import app.persistence.util.MailSender;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;

public class RoutingController {
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static OfferMapper offerMapper = new OfferMapper();
    private static OrderMapper orderMapper = new OrderMapper();
    private static PriceAndMaterialMapper priceAndMaterialMapper = new PriceAndMaterialMapper();
    private static MailSender mailSender = new MailSender();
    private static SVGgenerator svgGenerator = new SVGgenerator();
    private int offerId;

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

        app.get("/mail-sent", ctx -> showMailSentPage(ctx));
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
        // Denne verySecretAdminCode er hardcoded med vilje så alle kan tilgå admin-page
        String verySecretAdminCode = String.valueOf(getSellerCode());

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

    public static void handleFinalAcceptOfferPage(Context ctx) throws DatabaseException {
        CustomerInformation customerInformation = app.persistence.mappers.OfferMapper.getCustomerInformationFromOfferId(connectionPool, offerId);
        String sellerMail = app.persistence.mappers.OfferMapper.getSellerMailFromOfferId(connectionPool, offerId);
        String action = ctx.formParam("action");
        if ("confirm".equals(action)) {
            ctx.sessionAttribute("offerConfirmed", true);
            ctx.sessionAttribute("offerDenied", false);
            try {
                mailSender.sendSellerMailAccept(sellerMail, customerInformation);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

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

    public static void handleAcceptOfferPage(Context ctx) throws DatabaseException {
        try {
            float salesPriceFromOfferId = app.persistence.mappers.OfferMapper.getSalesPriceFromOfferId(connectionPool, offerId);
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
        ctx.render("/seller-contact.html");
    }

    public static void handleSellerContactPage(Context ctx) throws DatabaseException, IOException {
        String acceptOfferTempLink = "acceptoffertemplink.com";
        String sellerMail = app.persistence.mappers.OfferMapper.getSellerMailFromOfferId(connectionPool, offerId);
        CustomerInformation customerInformation = app.persistence.mappers.OfferMapper.getCustomerInformationFromOfferId(connectionPool, offerId);
        mailSender.sendSecondMail(customerInformation, acceptOfferTempLink);
        mailSender.sendSellerMailContact(sellerMail, customerInformation);
        showIndexPage(ctx);
    }

    private static void handleQuickBygPage(Context ctx) {
        String widthStr = ctx.formParam("carportWidth");
        String lengthStr = ctx.formParam("carportLength");
        String roofStr = ctx.formParam("carportTrapezRoof");
        String shedCheckbox = ctx.formParam("redskabsrumCheckbox");
        String shedLengthStr = ctx.formParam("redskabsrumLength");
        String shedWidthStr = ctx.formParam("redskabsrumWidth");
        String shedDoorsStr = ctx.formParam("redskabsrumDoors");

        int carportWidth = 0;
        int carportLength = 0;
        String carportTrapezRoof = "";
        boolean hasShed = false;
        int shedLength = 0;
        int shedWidth = 0;
        int shedDoors = 0;

        if (widthStr != null && !widthStr.isEmpty()) {
            carportWidth = Integer.parseInt(widthStr);
        }
        if (lengthStr != null && !lengthStr.isEmpty()) {
            carportLength = Integer.parseInt(lengthStr);
        }
        if (roofStr != null) {
            carportTrapezRoof = roofStr;
        }
        if (shedCheckbox != null) {
            hasShed = true;
            if (shedLengthStr != null && !shedLengthStr.isEmpty()) {
                shedLength = Integer.parseInt(shedLengthStr);
            }
            if (shedWidthStr != null && !shedWidthStr.isEmpty()) {
                shedWidth = Integer.parseInt(shedWidthStr);
            }
            if (shedDoorsStr != null && !shedDoorsStr.isEmpty()) {
                shedDoors = Integer.parseInt(shedDoorsStr);
            }
        }
        ctx.sessionAttribute("carportWidth", carportWidth);
        ctx.sessionAttribute("carportLength", carportLength);
        ctx.sessionAttribute("carportTrapezRoof", carportTrapezRoof);
        ctx.sessionAttribute("redskabsrumCheckbox", hasShed);
        ctx.sessionAttribute("redskabsrumLength", shedLength);
        ctx.sessionAttribute("redskabsrumWidth", shedWidth);
        ctx.sessionAttribute("redskabsrumDoors", shedDoors);

        ctx.render("/quick-byg-contact-information.html");
    }

    private static void showQuickBygPage(Context ctx) {
        carportAttributes(ctx);
        ctx.render("/quick-byg.html");
    }

    public static void handleContactInformationPage(Context ctx) {
        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String address = ctx.formParam("address");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        String city = ctx.formParam("city");
        String phone = ctx.formParam("phone");
        String email = ctx.formParam("email");
        boolean samtykke = ctx.formParam("samtykkeCheckbox") != null;

        ctx.sessionAttribute("samtykke", samtykke);
        ctx.sessionAttribute("firstname", firstname);
        ctx.sessionAttribute("lastname", lastname);
        ctx.sessionAttribute("address", address);
        ctx.sessionAttribute("zipcode", zipcode);
        ctx.sessionAttribute("housenumber", housenumber);
        ctx.sessionAttribute("phonenumber", phone);
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

        ctx.render("/quick-byg-contact-information.html");
    }

    public static void handleConfirmationPage(Context ctx) {
    }

    public static void showConfirmationPage(Context ctx) {
        carportAttributes(ctx);
        ctx.render("/quick-byg-confirmation.html");
    }

    public static void handleMailSentPage(Context ctx) {
    }

    public static void showMailSentPage(Context ctx) throws DatabaseException {
        offerId = app.persistence.mappers.MaterialCalculator.offerCalculator(connectionPool, getCarportLength(ctx), getCarportWidth(ctx), 210, getShedLength(ctx), getShedCheckbox(ctx)), getShedWidth(ctx, getShedCheckbox(ctx)), getShedDoors(ctx, getShedCheckbox(ctx)), getCustomerEmail(ctx), getCustomerFirstName(ctx), getCustomerLastName(ctx), getCustomerStreetName(ctx), getCustomerHouseNumber(ctx), getCustomerZipCode(ctx), getCustomerPhoneNumber(ctx), 120, getCarportTrapezRoof(ctx), 20, 5); ;

        ctx.render("/quick-byg-mail-sent.html");
        String searchForOfferLink = "becontactedbyaseller.com";

        float salesPrice = app.persistence.mappers.OfferMapper.getSalesPriceFromOfferId(connectionPool, offerId);
        try {
            mailSender.sendFirstMail(getCustomerEmail(ctx), getCustomerFirstName(ctx), salesPrice, offerId, searchForOfferLink);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void carportAttributes(Context ctx) {
        ctx.attribute("carportWidth", ctx.sessionAttribute("carportWidth"));
        ctx.attribute("carportLength", ctx.sessionAttribute("carportLength"));
        ctx.attribute("carportTrapezRoof", ctx.sessionAttribute("carportTrapezRoof"));
        ctx.attribute("redskabsrumCheckbox", ctx.sessionAttribute("redskabsrumCheckbox"));
        ctx.attribute("redskabsrumLength", ctx.sessionAttribute("redskabsrumLength"));
        ctx.attribute("redskabsrumWidth", ctx.sessionAttribute("redskabsrumWidth"));
        ctx.attribute("redskabsrumDoors", ctx.sessionAttribute("redskabsrumDoors"));
    }

    public static String getCustomerFirstName(Context ctx) {
        return ctx.sessionAttribute("firstname");
    }

    public static String getCustomerLastName(Context ctx) {
        return ctx.sessionAttribute("lastname");
    }

    public static String getStreetName(Context ctx) {
        return ctx.sessionAttribute("adresse");
    }

    public static int getHouseNumber(Context ctx) {
        return ctx.sessionAttribute("housenumber");
    }

    public static String getZipcode(Context ctx) {
        return ctx.sessionAttribute("zipcode");
    }

    public static String getCustomerEmail(Context ctx) {
        return ctx.sessionAttribute("email");
    }

    public static String getCustomerPhoneNumber(Context ctx) {
        return ctx.sessionAttribute("phonenumber");
    }

    public static int getSellerCode() {
        //very secret
        return 1111;
    }

    public static int getCarportWidth(Context ctx) {
        return ctx.sessionAttribute("carportWidth");
    }

    public static int getCarportLength(Context ctx) {
        return ctx.sessionAttribute("carportLength");
    }

    public static String getCarportTrapezRoof(Context ctx) {
        return ctx.sessionAttribute("carportTrapezRoof");
    }

    public static Boolean getShedCheckbox(Context ctx) {
        return ctx.sessionAttribute("redskabsrumCheckbox");
    }

    public static int getShedLength(Context ctx, boolean hasShed) {
        if (hasShed) {
            return ctx.sessionAttribute("redskabsrumLength");
        }
        return 0;
    }

    public static int getShedWidth(Context ctx, boolean hasShed) {
        if (hasShed) {
            return ctx.sessionAttribute("redskabsrumWidth");
        }
        return 0;
    }

    public static int getShedDoors(Context ctx, boolean hasShed) {
        if (hasShed) {
            return ctx.sessionAttribute("redskabsrumDoors");
        }
        return 0;
    }
}
