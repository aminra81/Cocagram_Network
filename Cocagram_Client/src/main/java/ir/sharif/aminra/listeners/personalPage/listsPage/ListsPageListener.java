package ir.sharif.aminra.listeners.personalPage.listsPage;

import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;

public class ListsPageListener {

    public void stringEventOccurred(String event) {
        if ("newGroup".equals(event))
            ViewManager.getInstance().setPage(new Page("newGroupPage"));
    }
}
