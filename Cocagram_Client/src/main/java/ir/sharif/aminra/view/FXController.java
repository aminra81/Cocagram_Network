package ir.sharif.aminra.view;

import javafx.fxml.FXML;

public abstract class FXController {

    @FXML
    public void back() { ViewManager.getInstance().back(); }

    @FXML
    public void goToMainPage() { ViewManager.getInstance().goToMainPage(); }

    @FXML
    public void clear() {}

}
