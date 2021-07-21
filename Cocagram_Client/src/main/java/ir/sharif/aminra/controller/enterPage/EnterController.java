package ir.sharif.aminra.controller.enterPage;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.enterPage.SignInFXController;
import ir.sharif.aminra.view.enterPage.SignUpFXController;
import javafx.application.Platform;

public class EnterController {

    public void enter(boolean success, String message) {
        if(success) {
            Platform.runLater(() -> ViewManager.getInstance().goToMainPage());
            return;
        }
        if(ViewManager.getInstance().getCurPage().getFxController() instanceof SignInFXController)
            Platform.runLater(() ->
                    ((SignInFXController)ViewManager.getInstance().getCurPage().getFxController()).setError(message));
        else
            Platform.runLater(() ->
                    ((SignUpFXController)ViewManager.getInstance().getCurPage().getFxController()).setError(message));
    }
}
