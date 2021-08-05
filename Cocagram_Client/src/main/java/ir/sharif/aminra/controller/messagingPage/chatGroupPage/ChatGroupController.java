package ir.sharif.aminra.controller.messagingPage.chatGroupPage;

import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.messagingPage.ChatGroupPage.ChatGroupFXController;
import javafx.application.Platform;

public class ChatGroupController {

    public void applyError(String error) {
        if(!(ViewManager.getInstance().getCurPage().getFxController() instanceof ChatGroupFXController))
            return;
        ChatGroupFXController chatGroupFXController = (ChatGroupFXController)
                ViewManager.getInstance().getCurPage().getFxController();
        Platform.runLater(() -> chatGroupFXController.setErrorLabel(error));
    }
}
