import ir.sharif.aminra.controller.network.ResponseSender;
import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.response.Response;

import java.util.LinkedList;
import java.util.Queue;

public class BotResponseSender implements ResponseSender {
    private final Queue<Request> requestQueue = new LinkedList<>();
    private final Object lock = new Object();
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
        synchronized (lock) {
            this.response = response;
            lock.notifyAll();
        }
    }

    public Response getResponse() {
        synchronized (lock) {
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Response currentResponse = response;
            this.response = null;
            return currentResponse;
        }
    }

    @Override
    public void close() {

    }
}
