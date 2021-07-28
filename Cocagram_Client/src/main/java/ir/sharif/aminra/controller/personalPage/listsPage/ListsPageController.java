package ir.sharif.aminra.controller.personalPage.listsPage;

import ir.sharif.aminra.models.Group;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.view.Page;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.personalPage.listsPage.GroupPanelFXController;
import ir.sharif.aminra.view.personalPage.listsPage.ListsFXController;
import ir.sharif.aminra.view.personalPage.listsPage.ViewPanelFXController;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class ListsPageController {
    public void refresh(List<User> followers, List<User> followings, List<User> blocklist, List<Group> groups) {
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof ListsFXController))
            return;
        ListsFXController listsFXController = (ListsFXController) ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> {
            listsFXController.clear();
            addListToBox(followers, listsFXController.getFollowersBox());
            addListToBox(followings, listsFXController.getFollowingsBox());
            addListToBox(blocklist, listsFXController.getBlockedUsersBox());

            //adding groups to group box
            for (Group group : groups) {
                Page groupPanel = new Page("groupPanel");
                GroupPanelFXController groupPanelFXController = (GroupPanelFXController) groupPanel.getFxController();
                groupPanelFXController.setGroup(group.getId());
                groupPanelFXController.setGroupNameLabel(group.getGroupName());
                listsFXController.getGroupsBox().getChildren().add(new AnchorPane(groupPanelFXController.getGroupPane()));
            }
        });
    }

    public void addListToBox(List<User> userList, VBox box) {
        List<User> activeUsers = new ArrayList<>();
        for (User currentUser : userList) {
            if (currentUser.isActive())
                activeUsers.add(currentUser);
        }
        for (User userToBeVisited : activeUsers) {
            Page viewPanel = new Page("viewPanel");
            ViewPanelFXController viewPanelFXController = (ViewPanelFXController) viewPanel.getFxController();
            viewPanelFXController.setUserToBeVisitedID(userToBeVisited.getId());
            viewPanelFXController.setUsernameLabel(userToBeVisited.getUsername());
            box.getChildren().add(new AnchorPane(viewPanelFXController.getViewPane()));
        }
    }
}
