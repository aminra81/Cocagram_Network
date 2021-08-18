package Vote;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Vote {

    private final static List<Vote> allVotes = new ArrayList<>();

    @Getter
    private final Integer voteId;
    @Getter
    private final Integer chatId;
    @Getter
    private final String description;
    @Getter
    private final List<VoteOption> options;
    @Getter
    private final List<Integer> votedUsers;

    public static Vote getVote(Integer id) {
        for (Vote vote : allVotes)
            if(vote.getVoteId().equals(id))
                return vote;
        return null;
    }

    public Vote(Integer chatId, String description, List<VoteOption> options) {
        this.voteId = allVotes.size() + 1;

        this.chatId = chatId;
        this.description = description;
        this.options = options;
        this.votedUsers = new ArrayList<>();

        allVotes.add(this);
    }

    public void addVotedUser(Integer messageWriter) {
        votedUsers.add(messageWriter);
    }
}
