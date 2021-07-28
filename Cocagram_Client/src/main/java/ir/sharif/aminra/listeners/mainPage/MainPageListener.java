package ir.sharif.aminra.listeners.mainPage;

import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainPageListener {
    static private final Logger logger = LogManager.getLogger(MainPageListener.class);

    public void stringEventOccurred(String event) {
        switch (event) {
            case "myPage":
                ViewManager.getInstance().setPage(new Page("myPage"));
                break;
            case "settings":
                break;
            case "timeline":
                break;
            case "explorer":
                break;
            case "messaging":
            default:
                break;
        }
    }
}
