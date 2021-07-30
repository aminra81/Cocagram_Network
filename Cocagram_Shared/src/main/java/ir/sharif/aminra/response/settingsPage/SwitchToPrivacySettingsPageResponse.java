package ir.sharif.aminra.response.settingsPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class SwitchToPrivacySettingsPageResponse extends Response {
    boolean isPrivate;
    String lastSeenType;
    String password;

    public SwitchToPrivacySettingsPageResponse(boolean isPrivate, String lastSeenType, String password) {
        this.isPrivate = isPrivate;
        this.lastSeenType = lastSeenType;
        this.password = password;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.switchToSettingsPage(isPrivate, lastSeenType, password);
    }
}
