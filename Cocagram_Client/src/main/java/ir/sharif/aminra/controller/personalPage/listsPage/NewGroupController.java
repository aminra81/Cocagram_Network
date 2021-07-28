package ir.sharif.aminra.controller.personalPage.listsPage;

import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.personalPage.listsPage.NewGroupFXController;
import javafx.application.Platform;

public class NewGroupController {

    public void applyCreateGroupResponse(String error) {
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof NewGroupFXController))
            return;
        NewGroupFXController newGroupFXController = (NewGroupFXController) ViewManager.getInstance().
                getCurPage().getFxController();
        Platform.runLater(() -> {
            if(error.equals(""))
                ViewManager.getInstance().back();
            else
                newGroupFXController.setErrorLabel(error);
        });
    }
}
