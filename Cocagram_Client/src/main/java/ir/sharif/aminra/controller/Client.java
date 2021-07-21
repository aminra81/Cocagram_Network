package ir.sharif.aminra.controller;

import ir.sharif.aminra.controller.enterPage.EnterController;
import ir.sharif.aminra.controller.network.RequestSender;
import ir.sharif.aminra.exceptions.ClientDisconnectException;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.UpdatePageRequest;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.util.Loop;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import javafx.application.Platform;
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
    private final EnterController enterController;

    public Client(RequestSender requestSender) {
        this.requestSender = requestSender;
        this.requestsList = new LinkedList<>();
        this.loop = new Loop(10, this::sendRequests);
        this.updater = new Loop(1, this::updatePage);
        client = this;

        enterController = new EnterController();
    }

    public void start(Stage stage) {
        loop.start();
        updater.start();
        ViewManager.getInstance().start(stage);
    }


    public void addRequest(Request request) {
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
                if(response != null)
                    response.visit(this);
            }
        } catch (ClientDisconnectException e) {
            ViewManager.getInstance().showError(Config.getConfig("client").getProperty("disconnectError"));
        }
    }

    private void updatePage() {
        if(ViewManager.getInstance().getCurPage() == null)
            return;
        addRequest(new UpdatePageRequest(ViewManager.getInstance().getCurPage().getFxController().getClass().getSimpleName()));
    }

    @Override
    public void goTo(String pageName, String message) {
        Platform.runLater(() -> {
            ViewManager.getInstance().setPage(new Page(pageName));
            if (message.length() > 0)
                ViewManager.getInstance().showInformation(message);
        });
    }

    @Override
    public void showError(String message) {
        Platform.runLater(() -> ViewManager.getInstance().showError(message));
    }

    @Override
    public void updatePage(String pageName, Object... args) {

    }

    @Override
    public void enter(boolean success, String message) {
        enterController.enter(success, message);
    }

    @Override
    public void logout() {
        goTo("enterPage", "");
    }
}
