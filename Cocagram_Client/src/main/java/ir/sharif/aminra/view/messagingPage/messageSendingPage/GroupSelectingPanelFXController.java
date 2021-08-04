package ir.sharif.aminra.view.messagingPage.messageSendingPage;

import ir.sharif.aminra.listeners.messagingPage.messageSendigPage.GroupPanelToChatPageListener;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class GroupSelectingPanelFXController extends FXController {
    Integer group;
    GroupPanelToChatPageListener groupPanelToChatPageListener;

    @FXML
    private Label groupNameLabel;

    @FXML
    private AnchorPane groupSelectingPanel;

    @FXML
    public void toggle() {
        groupPanelToChatPageListener.switchState(group);
    }

    public void setGroup(Integer group) { this.group = group; }

    public void setGroupPanelToChatPageListener(GroupPanelToChatPageListener groupPanelToChatPageListener) {
        this.groupPanelToChatPageListener = groupPanelToChatPageListener;
    }

    public AnchorPane getGroupSelectingPanel() { return groupSelectingPanel; }

    public void setGroupNameLabel(String groupName) { groupNameLabel.setText(groupName); }
}
