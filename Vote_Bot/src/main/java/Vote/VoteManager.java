package Vote;

import java.util.ArrayList;
import java.util.List;

public class VoteManager {

    private final String botMention = "@vote_bot";

    public String getResponse(Integer chatId, String messageContent, Integer messageWriter) {
        List<String> lines = new ArrayList<>();
        messageContent.lines().forEach(lines::add);
        if(lines.size() == 0 || !lines.get(0).equals(botMention))
            return null;
        if(lines.get(1).equals("new vote"))
            return newVoteResponse(chatId, lines);
        else if(lines.get(1).equals("vote"))
            return voteResponse(chatId, lines, messageWriter);
        else
            return null;
    }

    private String voteResponse(Integer chatId, List<String> lines, Integer messageWriter) {
        try {
            Vote vote = Vote.getVote(Integer.valueOf(lines.get(2)));
            if (!vote.getChatId().equals(chatId))
                return null;
            if(vote.getVotedUsers().contains(messageWriter))
                return "you already voted!";
            VoteOption option = vote.getOptions().get(Integer.parseInt(lines.get(3)) - 1);
            option.addVote();
            vote.addVotedUser(messageWriter);
            return "vote added\n" + getVoteDetails(vote);
        } catch (Exception e) {
            return null;
        }
    }

    private String newVoteResponse(Integer chatId, List<String> lines) {
        try {
            List<VoteOption> options = new ArrayList<>();
            for (int vote_index = 3; vote_index < lines.size(); vote_index++)
                options.add(new VoteOption(lines.get(vote_index)));
            Vote vote = new Vote(chatId, lines.get(2), options);
            return String.format("new vote (vote %s) added\n", vote.getVoteId()) + getVoteDetails(vote);
        } catch (Exception e) {
            return null;
        }
    }

    private String getVoteDetails(Vote vote) {
        String message = "";
        List<VoteOption> options = vote.getOptions();
        message += vote.getDescription() + "\n";
        for (int vote_index = 0; vote_index < options.size(); vote_index++)
            message += vote_index + 1 + ". " + options.get(vote_index).getOptionText() + " " +
                    options.get(vote_index).getNumberOfVotes() + "\n";
        return message;
    }
}
