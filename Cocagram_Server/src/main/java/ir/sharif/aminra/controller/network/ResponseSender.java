package ir.sharif.aminra.controller.network;

import ir.sharif.aminra.exceptions.ClientDisconnectException;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.Response;

public interface ResponseSender {
    Request getRequest() throws ClientDisconnectException;

    void sendResponse(Response response) throws ClientDisconnectException;

    void close();
}
