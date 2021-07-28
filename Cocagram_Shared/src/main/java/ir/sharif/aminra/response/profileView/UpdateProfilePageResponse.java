package ir.sharif.aminra.response.profileView;

import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

public class UpdateProfilePageResponse extends Response {
    String username;
    byte[] avatarArray;
    String firstname;
    String lastname;
    String lastSeen;
    String bio;
    String birthdate;
    String email;
    String phoneNumber;
    String blockString;
    String muteString;
    String followString;

    public UpdateProfilePageResponse(String username, byte[] avatarArray, String firstname, String lastname,
                                     String lastSeen, String bio, String birthdate, String email, String phoneNumber,
                                     String blockString, String muteString, String followString) {
        this.username = username;
        this.avatarArray = avatarArray;
        this.firstname = firstname;
        this.lastname = lastname;
        this.lastSeen = lastSeen;
        this.bio = bio;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.blockString = blockString;
        this.muteString = muteString;
        this.followString = followString;
    }


    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateProfilePage(username, avatarArray, firstname, lastname, lastSeen, bio, birthdate, email,
                phoneNumber, blockString, muteString, followString);
    }
}
