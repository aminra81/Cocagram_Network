package ir.sharif.aminra.listeners.mainPage;

import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;

public class MainPageListener {

    public void stringEventOccurred(String event) {
        switch (event) {
            case "myPage":
                ViewManager.getInstance().setPage(new Page("myPage"));
                break;
            case "settings":
                ViewManager.getInstance().setPage(new Page("settingsPage"));
                break;
            case "timeline":
                ViewManager.getInstance().setPage(new Page("timelinePage"));
                break;
            case "explorer":
                ViewManager.getInstance().setPage(new Page("explorerPage"));
                break;
            case "messaging":
                ViewManager.getInstance().setPage(new Page("messagingPage"));
            default:
                break;
        }
    }
}
