package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.network.ResponseSender;
import ir.sharif.aminra.exceptions.ClientDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.Getter;

import java.io.IOException;

public class ClientHandler extends Thread implements RequestVisitor {

    @Getter
    private final ResponseSender responseSender;
    private volatile boolean running;
    private User user;
    public ClientHandler(ResponseSender responseSender) throws IOException {
        this.responseSender = responseSender;
    }

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Request request = responseSender.getRequest();
                Response response = request.visit(this);
                if(response == null) {
                    //TODO handling database disconnection error
                }
                responseSender.sendResponse(response);
            } catch (ClientDisconnectException e) {
                running = false;
            }
        }
        responseSender.close();
    }
}
