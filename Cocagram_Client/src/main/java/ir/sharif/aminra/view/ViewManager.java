package ir.sharif.aminra.view;

import ir.sharif.aminra.util.Config;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Stack;

public class ViewManager {

    private static ViewManager instance;
    static private final Logger logger = LogManager.getLogger(ViewManager.class);

    public static ViewManager getInstance() {
        if(instance == null)
            instance = new ViewManager();
        return instance;
    }

    Stage stage;
    Stack<Page> stack = new Stack<>();
    Page curPage;

    public void start(Stage stage) {
        this.stage = stage;
        stage.setOnCloseRequest(windowEvent -> {
            logger.info("client program terminated");
            System.exit(0);
        });

        curPage = new Page("enterPage");
        setPage(curPage);

        stage.setTitle(Config.getConfig("main").getProperty("projectName"));
        stage.setResizable(false);
        stage.show();
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void showInformation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void setPage(Page page) {
        curPage = page;
        stack.push(page);
        stage.setScene(page.getScene());
    }

    public void back() {
        stack.pop();
        stage.setScene(stack.peek().getScene());
    }

    public void goToMainPage() {
        Page mainPage = new Page("mainPage");
        //TODO completing main page
        //MainFXController mainFXController = (MainFXController) mainPage.getFxController();
        ViewManager.getInstance().setPage(mainPage);
    }

    public Page getCurPage() { return curPage; }
}
