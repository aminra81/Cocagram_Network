package ir.sharif.aminra.view.messagingPage;

import ir.sharif.aminra.listeners.messagingPage.MessagePanelListener;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MessagePanelFXController extends FXController implements Initializable {
    MessagePanelListener messagePanelListener;
    Integer messageID;

    public void setMessageID(Integer messageID) { this.messageID = messageID; }

    public Integer getMessageID() { return messageID; }

    @FXML
    private AnchorPane messagePanel;

    @FXML
    private Label senderLabel;

    @FXML
    private Label messageTextLabel;

    @FXML
    private Label messageDateLabel;

    @FXML
    private Label messageStateLabel;

    @FXML
    public void view() { messagePanelListener.view();}

    public void setSenderLabel(String senderText) { senderLabel.setText(senderText); }

    public void setMessageTextLabel(String messageText) { messageTextLabel.setText(messageText); }

    public void setMessageDateLabel(String messageDate) { messageDateLabel.setText(messageDate); }

    public void setMessageStateLabel(String messageState) { messageStateLabel.setText(messageState); }

    public AnchorPane getMessagePanel() { return messagePanel; }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        messagePanelListener = new MessagePanelListener();
    }
}
