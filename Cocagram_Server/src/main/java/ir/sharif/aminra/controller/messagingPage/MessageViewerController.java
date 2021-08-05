package ir.sharif.aminra.controller.messagingPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.Connector;
import ir.sharif.aminra.database.ImageLoader;
import ir.sharif.aminra.exceptions.DatabaseDisconnectException;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.models.media.Message;
import ir.sharif.aminra.models.media.Tweet;
import ir.sharif.aminra.response.BackResponse;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.ShowErrorResponse;
import ir.sharif.aminra.response.messagingPage.EditMessageResponse;
import ir.sharif.aminra.response.messagingPage.UpdateMessageViewerResponse;
import ir.sharif.aminra.util.Config;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.time.LocalDateTime;

public class MessageViewerController {
    private final ClientHandler clientHandler;
    static private final Logger logger = LogManager.getLogger(MessageViewerController.class);

    public MessageViewerController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate(Integer messageId) {
        try {
            User user = clientHandler.getUser();
            Message message = Connector.getInstance().fetch(Message.class, messageId);

            boolean deactivated = !message.getWriter().equals(user.getId());

            ImageLoader imageLoader = new ImageLoader();
            BufferedImage bufferedImage = imageLoader.getByID(message.getImage());

            ImageUtils imageUtils = new ImageUtils();
            String messageImage;
            try {
                messageImage = imageUtils.toString(bufferedImage, "png");
            } catch (IOException e) {
                logger.warn("can't convert buffered image to byte array");
                e.printStackTrace();
                return null;
            }

            String messageContent = message.getContent();
            LocalDateTime messageDateTime = message.getDateTime();

            String writerUsername;
            if (message.getMainMedia() == null)
                writerUsername = Connector.getInstance().fetch(User.class, message.getWriter()).getUsername();
            else if (message.isForwardedTweet()) {
                Tweet mainTweet = Connector.getInstance().fetch(Tweet.class, message.getMainMedia());
                String tweetWriter = Connector.getInstance().fetch(User.class, mainTweet.getWriter()).getUsername();
                writerUsername = String.format("%s forwarded a tweet from %s",
                        Connector.getInstance().fetch(User.class, message.getWriter()).getUsername(), tweetWriter);
            } else {
                Message mainMessage = Connector.getInstance().fetch(Message.class, message.getMainMedia());
                String messageWriter = Connector.getInstance().fetch(User.class, mainMessage.getWriter()).getUsername();
                writerUsername = String.format("%s forwarded a message from %s",
                        Connector.getInstance().fetch(User.class, message.getWriter()).getUsername(), messageWriter);
            }
            return new UpdateMessageViewerResponse(deactivated, messageImage, messageContent, messageDateTime, writerUsername);

        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    public Response deleteMessage(Integer messageID) {
        try {
            Message message = Connector.getInstance().fetch(Message.class, messageID);
            message.setDeleted(true);
            Connector.getInstance().save(message);
            return new BackResponse();
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }

    public Response editMessage(Integer messageID, String messageContent) {
        try {
            Message message = Connector.getInstance().fetch(Message.class, messageID);
            if (message.getMainMedia() == null) {
                message.setContent(messageContent);
                Connector.getInstance().save(message);
                logger.info(String.format("message %s is edited.", message.getId()));
                return new BackResponse();
            } else {
                logger.info(String.format("user wants to edit message %s which is forwarded", message.getId()));
                return new EditMessageResponse(
                        Config.getConfig("messageViewerPage").getProperty("editForwardedMessageError"));
            }
        } catch (DatabaseDisconnectException e) {
            return new ShowErrorResponse(Config.getConfig("server").getProperty("databaseDisconnectError"));
        }
    }
}
