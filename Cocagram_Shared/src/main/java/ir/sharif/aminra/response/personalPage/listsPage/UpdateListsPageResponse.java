package ir.sharif.aminra.response.personalPage.listsPage;

import ir.sharif.aminra.models.viewModels.ViewGroup;
import ir.sharif.aminra.models.viewModels.ViewUser;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ResponseVisitor;

import java.util.List;

public class UpdateListsPageResponse extends Response {
    private final List<ViewUser> followers;
    private final List<ViewUser> followings;
    private final List<ViewUser> blocklist;
    private final List<ViewGroup> groups;

    public UpdateListsPageResponse(List<ViewUser> followers, List<ViewUser> followings, List<ViewUser> blocklist,
                                   List<ViewGroup> groups) {
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
