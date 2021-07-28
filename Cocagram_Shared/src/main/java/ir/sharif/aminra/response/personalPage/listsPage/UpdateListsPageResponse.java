package ir.sharif.aminra.response.personalPage.listsPage;

import ir.sharif.aminra.models.Group;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateListsPageResponse extends Response {
    List<User> followers;
    List<User> followings;
    List<User> blocklist;
    List<Group> groups;

    public UpdateListsPageResponse(List<User> followers, List<User> followings, List<User> blocklist, List<Group> groups) {
        this.followers = followers;
        this.followings = followings;
        this.blocklist = blocklist;
        this.groups = groups;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.updateListsPage(followers, followings, blocklist, groups);
    }
}
