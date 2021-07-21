package ir.sharif.aminra.request;

import ir.sharif.aminra.response.Response;

import java.time.LocalDate;

public interface RequestVisitor {
    Response updatePage(String pageName);
    Response login(String username, String password);
    Response register(String username, String firstname, String lastname, String bio, LocalDate birthDate,
                      String email, String phoneNumber, String password, boolean publicData, String lastSeenType);
    Response logout();
}
