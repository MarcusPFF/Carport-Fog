package app.persistence.controller;

import app.entities.customerInformation;
import app.entities.Material;
import app.exceptions.DatabaseException;
import app.persistence.calculator.MaterialCalculator;
import app.persistence.connection.ConnectionPool;
import app.persistence.documentCreation.CarportSvg;
import app.persistence.documentCreation.Svg;
import app.persistence.mappers.OfferMapper;
import app.persistence.mappers.OrderMapper;
import app.persistence.mappers.PriceAndMaterialMapper;
import app.persistence.util.MailSender;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class RoutingController {
    private static ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static OfferMapper offerMapper = new OfferMapper();
    private static OrderMapper orderMapper = new OrderMapper();
    private static PriceAndMaterialMapper priceAndMaterialMapper = new PriceAndMaterialMapper();
    private static MailSender mailSender = new MailSender();
    private static MaterialCalculator materialCalculator = new MaterialCalculator();
    private static int offerId;

    public static int getOfferId() {
        return offerId;
    }

    public static void setOfferId(int offerId) {
        RoutingController.offerId = offerId;
    }


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
        app.post("/button-offerid", ctx -> handleSellerAdminPageOfferID(ctx));
        app.post("/button-salesPrice", ctx -> handleSellerAdminPageSalesPrice(ctx));
        app.post("/button-send-final-offer", ctx -> handleSellerAdminPageSendFinalOffer(ctx));
        app.get("/button-editDimensions", ctx -> showSellerAdminPageEditDimensions(ctx));
        app.get("/button-editPrices", ctx -> showSellerAdminEditPrices(ctx));

        app.post("/button-updateWoodPrice", ctx -> handleSellerAdminUpdateWoodPrice(ctx));
        app.post("/button-updateMountPrice", ctx -> handleSellerAdminUpdateMountPrice(ctx));
        app.post("/button-updateScrewPrice", ctx -> handleSellerAdminUpdateScrewPrice(ctx));
        app.post("/button-updateRoofPrice", ctx -> handleSellerAdminUpdateRoofPrice(ctx));


        app.get("/contact-information", ctx -> showContactInformationPage(ctx));
        app.post("/contact-information", ctx -> handleContactInformationPage(ctx));

        app.get("/confirmation", ctx -> showConfirmationPage(ctx));
        app.post("/confirmation", ctx -> handleConfirmationPage(ctx));

        app.get("/mail-sent", ctx -> showMailSentPage(ctx));

        app.get("/svg-drawing", ctx -> showSvgDrawingPage(ctx));
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

    public static void handleSellerAdminLogin(Context ctx) throws DatabaseException {
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

    public static void showSellerAdminEditPrices(Context ctx) {
        ctx.render("/seller-admin-edit-prices.html");
    }

    public static void handleSellerAdminUpdateRoofPrice(Context ctx) throws DatabaseException {
        String roofName = ctx.formParam("roofName");
        float roofPrice = Float.parseFloat(ctx.formParam("roofPrice"));
        int roofLength = Integer.parseInt(ctx.formParam("roofLength"));
        priceAndMaterialMapper.updateRoofPrice(connectionPool, roofPrice, roofName, roofLength);
        showSellerAdminEditPrices(ctx);
    }

    public static void handleSellerAdminUpdateMountPrice(Context ctx) throws DatabaseException {
        String mountName = ctx.formParam("mountName");
        float mountPrice = Float.parseFloat(ctx.formParam("mountPrice"));
        priceAndMaterialMapper.updateMountPrice(connectionPool, mountPrice, mountName);
        showSellerAdminEditPrices(ctx);
    }

    public static void handleSellerAdminUpdateScrewPrice(Context ctx) throws DatabaseException {
        String screwName = ctx.formParam("screwName");
        float screwPrice = Float.parseFloat(ctx.formParam("screwPrice"));
        priceAndMaterialMapper.updateScrewsPrice(connectionPool, screwPrice, screwName);
        showSellerAdminEditPrices(ctx);
    }

    public static void handleSellerAdminUpdateWoodPrice(Context ctx) throws DatabaseException {
        int woodWidth = Integer.parseInt(ctx.formParam("woodWidth"));
        int woodHeight = Integer.parseInt(ctx.formParam("woodHeight"));
        float woodPrice = Float.parseFloat(ctx.formParam("mountPrice"));
        priceAndMaterialMapper.updateDimensionMeterPrice(connectionPool, woodPrice, woodWidth, woodHeight);
        showSellerAdminEditPrices(ctx);
    }

    public static void showSellerAdminPage(Context ctx) throws DatabaseException {
        List<Material> materials = new ArrayList<>();
        float salesPrice = 0;
        float expensesPrice = 0;
        offerMapper = new OfferMapper();
        ArrayList<Material> wood = offerMapper.getWoodListFromOfferId(connectionPool, getOfferId());
        ArrayList<Material> mount = offerMapper.getMountListFromOfferId(connectionPool, getOfferId());
        ArrayList<Material> screw = offerMapper.getScrewListFromOfferId(connectionPool, getOfferId());
        ArrayList<Material> roof = offerMapper.getRoofListFromOfferId(connectionPool, getOfferId());
        for (Material material : wood) {
            materials.add(material);
        }
        for (Material material : mount) {
            materials.add(material);
        }
        for (Material material : screw){
            materials.add(material);
        }
        for (Material material : roof){
            materials.add(material);
        }
        if (getOfferId() != 0){
            salesPrice = offerMapper.getSalesPriceFromOfferId(connectionPool, getOfferId());
            expensesPrice = offerMapper.getTotalExpensesPriceFromOfferId(connectionPool, getOfferId());
        }
        System.out.println(salesPrice);
        System.out.println(expensesPrice);


        ctx.render("/seller-admin-page.html", Map.of("materials", materials, "salesPrice", salesPrice, "expensesPrice", expensesPrice));
    }

    public static void handleSellerAdminPageOfferID(Context ctx) throws DatabaseException {
        int offerId = Integer.parseInt(ctx.formParam("offerId"));
        setOfferId(offerId);
        showSellerAdminPage(ctx);
    }

    public static void handleSellerAdminPageSalesPrice(Context ctx) throws DatabaseException {
        float salesPrice = Float.parseFloat(ctx.formParam("salesPrice"));
        offerMapper.updateTotalSalesPrice(connectionPool, salesPrice, getOfferId());
        showSellerAdminPage(ctx);
    }

    public static void showSellerAdminPageEditDimensions(Context ctx) throws DatabaseException {
        float salesPrice = Float.parseFloat(ctx.formParam("salesPrice"));
        offerMapper.updateTotalSalesPrice(connectionPool, salesPrice, getOfferId());
        showSellerAdminPage(ctx);
    }

    public static void handleSellerAdminPageEditDimensions(Context ctx) throws DatabaseException {
        float salesPrice = Float.parseFloat(ctx.formParam("salesPrice"));
        offerMapper.updateTotalSalesPrice(connectionPool, salesPrice, getOfferId());
        showSellerAdminPage(ctx);
    }
    public static void handleSellerAdminPageSendFinalOffer(Context ctx) throws DatabaseException, IOException {
        int offerId = getOfferId();
        setOfferId(0);
        String acceptOfferTempLink = "acceptoffertemplink.com";
        customerInformation customerInformation = offerMapper.getCustomerInformationFromOfferId(connectionPool, offerId);
        String to = customerInformation.getCustomerMail();
        String name = customerInformation.getFirstName();

        Personalization personalization = new Personalization();
        personalization.addTo(new Email(to));
        personalization.addDynamicTemplateData("name", name);
        personalization.addDynamicTemplateData("offerId", offerId);
        personalization.addDynamicTemplateData("acceptOfferLink", acceptOfferTempLink);
        mailSender.sendSecondMail(customerInformation, acceptOfferTempLink);
        showSellerAdminPage(ctx);
    }

    public static void showFinalAcceptOfferPage(Context ctx) {
        ctx.sessionAttribute("offerId");
        Boolean denied = ctx.sessionAttribute("offerDenied");
        Boolean confirmed = ctx.sessionAttribute("offerConfirmed");
        ctx.attribute("actionDenied", denied != null && denied);
        ctx.attribute("actionConfirmed", confirmed != null && confirmed);
        ctx.render("/final-accept-offer.html");
    }

    public static void handleFinalAcceptOfferPage(Context ctx) throws DatabaseException {
        customerInformation customerInformation = app.persistence.mappers.OfferMapper.getCustomerInformationFromOfferId(connectionPool, getOfferId());
        String sellerMail = app.persistence.mappers.OfferMapper.getSellerMailFromOfferId(connectionPool, getOfferId());
        String action = ctx.formParam("action");
        if ("confirm".equals(action)) {
            ctx.sessionAttribute("offerConfirmed", true);
            ctx.sessionAttribute("offerDenied", false);
            try {
                mailSender.sendSellerMailAccept(sellerMail, customerInformation);
                app.persistence.mappers.OrderMapper.createNewOrder(connectionPool, getOfferId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if ("deny".equals(action)) {
            ctx.sessionAttribute("offerConfirmed", false);
            ctx.sessionAttribute("offerDenied", true);
            app.persistence.mappers.OfferMapper.deleteOfferAndEveryThinkLinkedToItByOfferId(connectionPool, getOfferId());
            ctx.sessionAttribute("offerId", null);

        }
        ctx.redirect("/final-accept-offer");
    }

    public static void showAcceptOfferPage(Context ctx) {
        ctx.render("/accept-offer.html");
    }

    public static void handleAcceptOfferPage(Context ctx) throws DatabaseException {
        try {
            float salesPriceFromOfferId = app.persistence.mappers.OfferMapper.getSalesPriceFromOfferId(connectionPool, getOfferId());
            if (salesPriceFromOfferId > 0.1) {
                ctx.sessionAttribute("salesPriceFromOfferId", salesPriceFromOfferId);
                showFinalAcceptOfferPage(ctx);
            } else {
                ctx.status(400);
                ctx.attribute("errorMessage", "Tilbud " + getOfferId() + " findes ikke.");
                ctx.sessionAttribute("offerId", getOfferId());
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
        String sellerMail = app.persistence.mappers.OfferMapper.getSellerMailFromOfferId(connectionPool, getOfferId());
        customerInformation customerInformation = app.persistence.mappers.OfferMapper.getCustomerInformationFromOfferId(connectionPool, getOfferId());
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
        showSvgDrawingPage(ctx);
        ctx.render("/quick-byg.html");
    }

    public static void handleContactInformationPage(Context ctx) {
        String firstname = ctx.formParam("firstname");
        String lastname = ctx.formParam("lastname");
        String streetname = ctx.formParam("streetname");
        int zipcode = Integer.parseInt(ctx.formParam("zipcode"));
        int housenumber = Integer.parseInt(ctx.formParam("housenumber"));
        int phonenumber = Integer.parseInt(ctx.formParam("phonenumber"));
        String email = ctx.formParam("email");
        boolean samtykke = ctx.formParam("samtykkeCheckbox") != null;

        ctx.sessionAttribute("samtykke", samtykke);
        ctx.sessionAttribute("firstname", firstname);
        ctx.sessionAttribute("lastname", lastname);
        ctx.sessionAttribute("streetname", streetname);
        ctx.sessionAttribute("zipcode", zipcode);
        ctx.sessionAttribute("housenumber", housenumber);
        ctx.sessionAttribute("phonenumber", phonenumber);
        ctx.sessionAttribute("email", email);

        ctx.redirect("/confirmation");
    }

    public static void showContactInformationPage(Context ctx) {
        ctx.attribute("firstname", ctx.sessionAttribute("firstname"));
        ctx.attribute("lastname", ctx.sessionAttribute("lastname"));
        ctx.attribute("streetname", ctx.sessionAttribute("streetname"));
        ctx.attribute("zipcode", ctx.sessionAttribute("zipcode"));
        ctx.attribute("housenumber", ctx.sessionAttribute("housenumber"));
        ctx.attribute("phonenumber", ctx.sessionAttribute("phonenumber"));
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
        int offerId = materialCalculator.offerCalculator(connectionPool, getCarportLength(ctx), getCarportWidth(ctx), 210, getShedLength(ctx, getShedCheckbox(ctx)), getShedWidth(ctx, getShedCheckbox(ctx)), getShedDoors(ctx, getShedCheckbox(ctx)), getCustomerEmail(ctx), getCustomerFirstName(ctx), getCustomerLastName(ctx), getCustomerStreetName(ctx), getCustomerHouseNumber(ctx), getCustomerZipCode(ctx), getCustomerPhoneNumber(ctx), 120, getCarportTrapezRoof(ctx), 20, 5);
        ;
        setOfferId(offerId);
        ctx.render("/quick-byg-mail-sent.html");
        String searchForOfferLink = "becontactedbyaseller.com";

        float salesPrice = app.persistence.mappers.OfferMapper.getSalesPriceFromOfferId(connectionPool, getOfferId());
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

    public static String getCustomerStreetName(Context ctx) {
        return ctx.sessionAttribute("streetname");
    }

    public static int getCustomerHouseNumber(Context ctx) {
        return ctx.sessionAttribute("housenumber");
    }

    public static int getCustomerZipCode(Context ctx) {
        return ctx.sessionAttribute("zipcode");
    }

    public static String getCustomerEmail(Context ctx) {
        return ctx.sessionAttribute("email");
    }

    public static int getCustomerPhoneNumber(Context ctx) {
        return ctx.sessionAttribute("phonenumber");
    }

    public static int getSellerCode() {
        //very secret
        return 1111;
    }

    public static void showSvgDrawingPage(Context ctx) {
        Locale.setDefault(new Locale("US"));

        int ekstraMargin = 60;
        int carportLength = 780;
        int carportWidth = 600;

        int svgWidth = carportLength + ekstraMargin;
        int svgHeight = carportWidth + ekstraMargin;

        // Selve tegningen laves her
        CarportSvg svg = new CarportSvg(carportLength, carportWidth);

        // Brug præcis samme viewBox og størrelse til canvas
        Svg carportSvg = new Svg(0, 0, "0 0 " + svgWidth + " " + svgHeight, svgWidth + "px", svgHeight + "px");

        ctx.attribute("svg", svg.toString());
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
