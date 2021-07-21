package ir.sharif.aminra.listeners.enterPage;

import ir.sharif.aminra.controller.Client;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.enterPage.LoginRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignInFormListener {

    static private final Logger logger = LogManager.getLogger(SignInFormListener.class);

    public void login(String username, String password) {
        Request request = new LoginRequest(username, password);
        logger.info(String.format("client requested %s", request));
        Client.getClient().addRequest(request);
    }
}
