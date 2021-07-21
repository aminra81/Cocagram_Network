package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.enterPage.SignInController;
import ir.sharif.aminra.controller.enterPage.SignUpController;
import ir.sharif.aminra.controller.network.ResponseSender;
import ir.sharif.aminra.exceptions.ClientDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

public class ClientHandler extends Thread implements RequestVisitor {

    @Getter
    private final ResponseSender responseSender;
    private volatile boolean running;
    @Setter
    private User user;

    private final SignUpController signUpController;
    private final SignInController signInController;
    public ClientHandler(ResponseSender responseSender) throws IOException {
        this.responseSender = responseSender;
        signUpController = new SignUpController(this);
        signInController = new SignInController(this);
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
                responseSender.sendResponse(response);
            } catch (ClientDisconnectException e) {
                running = false;
            }
        }
        responseSender.close();
    }

    @Override
    public Response updatePage(String pageName) {
        return null;
    }

    @Override
    public Response login(String username, String password) {
        return signInController.login(username, password);
    }

    @Override
    public Response register(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                             String email, String phoneNumber, String password, boolean publicData, String lastSeenType) {
        return signUpController.register(username, firstname, lastname, bio, birthDate, email, phoneNumber, password, publicData,
                lastSeenType);
    }

    @Override
    public Response logout() {
        return null;
    }
}
