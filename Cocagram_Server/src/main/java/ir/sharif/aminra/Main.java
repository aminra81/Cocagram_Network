package ir.sharif.aminra;

import ir.sharif.aminra.controller.SocketManager;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Main {

    static private final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        logger.info("server program started");
        try {
            SocketManager socketManager = new SocketManager();
            socketManager.start();
        } catch (DatabaseDisconnectException e) {
            //server terminates
            System.exit(0);
        }
    }
}
