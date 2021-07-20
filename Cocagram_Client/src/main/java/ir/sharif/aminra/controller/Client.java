package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.network.RequestSender;
import ir.sharif.aminra.exceptions.ClientDisconnectException;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.util.Loop;
import ir.sharif.aminra.view.ViewManager;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;

public class Client implements ResponseVisitor {
    @Getter
    private static Client client;
    private final RequestSender requestSender;
    private final List<Request> requestsList;
    private final Loop loop, updater;

    public Client(RequestSender requestSender) {
        this.requestSender = requestSender;
        this.requestsList = new LinkedList<>();
        this.loop = new Loop(10, this::sendRequests);
        this.updater = new Loop(1, this::updatePage);
        client = this;
    }

    public void start(Stage stage) {
        loop.start();
        updater.start();
        ViewManager.getInstance().start(stage);
    }


    private void addRequest(Request request) {
        synchronized (requestsList) {
            requestsList.add(request);
        }
    }

    private void sendRequests() {
        List<Request> tmpRequestsList;
        synchronized (requestsList) {
            tmpRequestsList = new LinkedList<>(requestsList);
            requestsList.clear();
        }
        try {
            for (Request request : tmpRequestsList) {
                Response response;
                response = requestSender.sendRequest(request);
                response.visit(this);
            }
        } catch (ClientDisconnectException e) {
            ViewManager.getInstance().showError(Config.getConfig("client").getProperty("disconnectError"));
        }
    }

    private void updatePage() {

    }
}
