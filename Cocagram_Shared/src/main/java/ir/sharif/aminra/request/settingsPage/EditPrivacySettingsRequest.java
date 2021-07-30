package ir.sharif.aminra.request.settingsPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

@ToString
public class EditPrivacySettingsRequest extends Request {
    boolean isPrivate;
    String lastSeenType;
    String password;

    public EditPrivacySettingsRequest(boolean isPrivate, String lastSeenType, String password) {
        this.isPrivate = isPrivate;
        this.lastSeenType = lastSeenType;
        this.password = password;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.editPrivacySettings(isPrivate, lastSeenType, password);
    }
}
