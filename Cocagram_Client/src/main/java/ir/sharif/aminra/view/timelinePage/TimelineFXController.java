package ir.sharif.aminra.view.timelinePage;

import ir.sharif.aminra.view.FXController;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;


public class TimelineFXController extends FXController {

    @FXML
    private VBox tweetBox;

    public VBox getTweetBox() {
        return tweetBox;
    }

    @Override
    public void clear() {
        tweetBox.getChildren().clear();
    }
}
