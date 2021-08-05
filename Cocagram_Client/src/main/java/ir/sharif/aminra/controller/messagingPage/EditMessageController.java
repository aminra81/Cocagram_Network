package ir.sharif.aminra.controller.messagingPage;

import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.messagingPage.EditMessageFXController;
import javafx.application.Platform;

public class EditMessageController {
    public void applyEditMessageResponse(String error) {
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof EditMessageFXController))
            return;
        EditMessageFXController editMessageFXController = (EditMessageFXController)
                ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> editMessageFXController.setErrorLabel(error));
    }
}
