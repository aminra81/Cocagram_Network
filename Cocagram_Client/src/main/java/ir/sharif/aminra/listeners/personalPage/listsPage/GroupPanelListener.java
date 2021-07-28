package ir.sharif.aminra.listeners.personalPage.listsPage;

import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.personalPage.listsPage.GroupFXController;

public class GroupPanelListener {

    public void viewEventOccurred(Integer group) {
        ViewManager.getInstance().setPage(new Page("groupPage"));
        GroupFXController groupFXController = (GroupFXController) ViewManager.getInstance().getCurPage().getFxController();
        groupFXController.setGroup(group);
    }
}
