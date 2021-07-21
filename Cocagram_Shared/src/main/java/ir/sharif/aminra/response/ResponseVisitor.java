package ir.sharif.aminra.response;

public interface ResponseVisitor {
    void goTo(String pageName, String message);
    void showError(String message);
    void updatePage(String pageName, Object... args);
    void enter(boolean success, String message);
    void logout();
}
