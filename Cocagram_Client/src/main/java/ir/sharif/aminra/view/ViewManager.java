package ir.sharif.aminra.view;

import ir.sharif.aminra.util.Config;
import javafx.stage.Stage;

import java.util.Stack;

public class ViewManager {

    Stage stage;
    Stack<Page> stack = new Stack<>();
    Page curPage;

    public void start(Stage stage) {
        this.stage = stage;

        curPage = new Page("enterPage");
        setPage(curPage);

        stage.setTitle(Config.getConfig("main").getProperty("projectName"));
        stage.setResizable(false);
        stage.show();
    }

    public void setPage(Page page) {
        stack.push(page);
        stage.setScene(page.getScene());
    }

    public void back() {
        stack.pop();
        stage.setScene(stack.peek().getScene());
    }
}
