package ir.sharif.aminra.response.personalPage.editPage;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.time.LocalDate;

public class SwitchToEditPageResponse extends Response {

    String username;
    String firstname;
    String lastname;
    String bio;
    LocalDate birthdate;
    String email;
    String phoneNumber;
    String lastSeenType;
    boolean isPrivate;
    boolean isPublicData;

    public SwitchToEditPageResponse(String username, String firstname, String lastname, String bio,
                                  LocalDate birthdate, String email, String phoneNumber, String lastSeenType,
                                  boolean isPrivate, boolean isPublicData) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.lastSeenType = lastSeenType;
        this.isPrivate = isPrivate;
        this.isPublicData = isPublicData;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.switchToEditPage(username, firstname, lastname, bio, birthdate, email, phoneNumber, lastSeenType,
                isPrivate, isPublicData);
    }
}
