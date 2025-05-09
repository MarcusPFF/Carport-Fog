package app;

import app.config.SessionConfig;
import app.config.ThymeleafConfig;
import app.persistence.controller.RoutingController;
import app.persistence.connection.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.rendering.template.JavalinThymeleaf;
import java.util.logging.Logger;


public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static final String USER = "postgres";
    private static final String PASSWORD = System.getenv("kudsk_db_password");
    private static final String URL = "jdbc:postgresql://134.122.71.16/%s?currentSchema=public";
    private static final String DB = "Fog_Carport";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    public static void main(String[] args) {

        // Initializing Javalin and Jetty webserver
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public");
            config.jetty.modifyServletContextHandler(handler -> handler.setSessionHandler(SessionConfig.sessionConfig()));
            config.fileRenderer(new JavalinThymeleaf(ThymeleafConfig.templateEngine()));
        }).start(7070);

        app.get("/", ctx -> RoutingController.getShowIndexPage(ctx));
        RoutingController.routes(app);
    }
}



