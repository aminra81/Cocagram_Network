import ir.sharif.aminra.controller.network.ResponseSender;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.Response;

import java.util.LinkedList;
import java.util.Queue;

public class BotResponseSender implements ResponseSender {
    private final Queue<Request> requestQueue = new LinkedList<>();
    private Response response;
    @Override
    public Request getRequest() {
        synchronized (requestQueue) {
            while (requestQueue.isEmpty()) {
                try {
                    requestQueue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return requestQueue.poll();
        }
    }

    public void addRequest(Request request) {
        synchronized (requestQueue) {
            requestQueue.add(request);
            requestQueue.notifyAll();
        }
    }

    @Override
    public void sendResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() { return response; }

    @Override
    public void close() {

    }
}
