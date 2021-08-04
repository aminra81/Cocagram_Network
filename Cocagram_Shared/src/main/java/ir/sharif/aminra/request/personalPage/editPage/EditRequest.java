package ir.sharif.aminra.request.personalPage.editPage;

import ir.sharif.aminra.request.Request;
import ir.sharif.aminra.request.RequestVisitor;
import ir.sharif.aminra.response.Response;
import lombok.ToString;

import java.time.LocalDate;

@ToString
public class EditRequest extends Request {
    private final String firstname;
    private final String lastname;
    private final String bio;
    private final LocalDate birthdate;
    private final String email;
    private final String phoneNumber;
    private final String avatarString;

    public EditRequest(String firstname, String lastname, String bio, LocalDate birthdate, String email,
                       String phoneNumber, String avatarString) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.bio = bio;
        this.birthdate = birthdate;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.avatarString = avatarString;
    }

    @Override
    public Response visit(RequestVisitor requestVisitor) {
        return requestVisitor.edit(firstname, lastname, bio, birthdate, email, phoneNumber, avatarString);
    }
}
