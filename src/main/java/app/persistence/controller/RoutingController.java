package app.persistence.controller;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.connection.ConnectionPool;
import app.persistence.documentCreation.MailSender;
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

    }
    private static void handleIndexPage(Context ctx) {}
    private static void showIndexPage(Context ctx) {}
}
