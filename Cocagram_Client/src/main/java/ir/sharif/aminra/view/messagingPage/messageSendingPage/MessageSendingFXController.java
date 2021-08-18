package ir.sharif.aminra.view.messagingPage.messageSendingPage;

import ir.sharif.aminra.listeners.messagingPage.messageSendigPage.NewMessageListener;
import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageSendingFXController extends FXController implements Initializable {
    static private final Logger logger = LogManager.getLogger(MessageSendingFXController.class);
    NewMessageListener newMessageListener;

    BufferedImage messageImage;
    Integer receiverID;

    public void setReceiverID(Integer receiverID) {
        this.receiverID = receiverID;
    }

    @FXML
    private TextArea messageContent;

    @FXML
    private Label errorLabel;

    @FXML
    public void attach() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterPNG);
        File file = fileChooser.showOpenDialog(null);

        try {
            if(file == null)
                messageImage = null;
            else
                messageImage = ImageIO.read(file);
            logger.info("loaded image successfully");
        } catch (IOException e) {
            e.printStackTrace();
            logger.warn("an error occurred while trying to load image.");
        }
    }

    @FXML
    public void send() {
        newMessageListener.send(messageImage, messageContent.getText(), receiverID);
    }

    public void setErrorLabel(String error) {
        errorLabel.setText(error);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newMessageListener = new NewMessageListener();
    }
}
