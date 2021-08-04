package ir.sharif.aminra.view.messagingPage.ChatGroupPage;

import ir.sharif.aminra.listeners.messagingPage.chatGroupPage.ChatGroupPageListener;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatGroupFXController extends FXController implements Initializable {

    ChatGroupPageListener chatGroupPageListener;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField newGroupNameField;

    @FXML
    private TextField removeGroupNameField;

    @FXML
    private TextField groupNameField;

    @FXML
    private TextField usernameField;

    @FXML
    public void addUser() {
        chatGroupPageListener.addUser(groupNameField.getText(), usernameField.getText());
    }

    @FXML
    public void newGroup() {
        chatGroupPageListener.createGroup(newGroupNameField.getText());
    }

    @FXML
    public void removeGroup() {
        chatGroupPageListener.removeGroup(newGroupNameField.getText());
    }

    public void setErrorLabel(String error) { errorLabel.setText(error); }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatGroupPageListener = new ChatGroupPageListener();
    }
}
