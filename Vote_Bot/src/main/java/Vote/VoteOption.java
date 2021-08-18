package Vote;

import lombok.Getter;

public class VoteOption {
    @Getter
    private final String optionText;
    @Getter
    private Integer numberOfVotes;

    public VoteOption(String optionText) {
        this.optionText = optionText;
        this.numberOfVotes = 0;
    }

    public void addVote() {
        numberOfVotes++;
    }
}
