package ir.sharif.aminra.listeners;

import ir.sharif.aminra.controller.Client;

public abstract class RequestListener {
    Client client;
    public RequestListener(Client client) {
        this.client = client;
    }

    public void back() {

    }

    public void goToMainPage() {

    }
}
