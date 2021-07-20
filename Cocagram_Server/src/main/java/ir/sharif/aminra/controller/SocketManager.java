package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.network.ResponseSender;
import ir.sharif.aminra.controller.network.SocketResponseSender;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SocketManager extends Thread {

    static private final Logger logger = LogManager.getLogger(SocketManager.class);

    private final ServerSocket serverSocket;
    private final List<ClientHandler> clientHandlers;

    public SocketManager() throws IOException, DatabaseDisconnectException {
        Config config = Config.getConfig("server");
        int port = config.getProperty(Integer.class, "port") != null ?
                config.getProperty(Integer.class, "port") : 8000;
        serverSocket = new ServerSocket(port);
        //to build connection to database
        Connector.getInstance();
        clientHandlers = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                logger.info(String.format("socket %s is connected", socket));
                ResponseSender responseSender = new SocketResponseSender(socket);
                ClientHandler clientHandler = new ClientHandler(responseSender);
                clientHandlers.add(clientHandler);
                clientHandler.start();
            } catch (IOException ignore) {
            }
        }
    }


    public void removeClientHandler(SocketResponseSender socketResponseSender) {
        clientHandlers.removeIf(clientHandler -> clientHandler.getResponseSender().equals(socketResponseSender));
    }
}
