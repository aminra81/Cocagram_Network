package ir.sharif.aminra;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.controller.network.SocketRequestSender;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.view.ViewManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.Socket;

public class Main extends Application {

    static private final Logger logger = LogManager.getLogger(Main.class);

    @Override
    public void start(Stage stage) {
        logger.info("client program started");
        Config config = Config.getConfig("client");
        int port = config.getProperty(Integer.class, "port") != null ?
                config.getProperty(Integer.class, "port") : 8000;
        String host = config.getProperty(String.class, "host") != null ?
                config.getProperty(String.class, "host") : "";
        try {
            Socket socket = new Socket(host, port);
            logger.info(String.format("socket %s is client socket", socket));
            Client client = new Client(new SocketRequestSender(socket));
            client.start(stage);
        } catch (Exception e) {
            logger.error("can't connect to server");
            ViewManager.getInstance().showError("you're offline");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}