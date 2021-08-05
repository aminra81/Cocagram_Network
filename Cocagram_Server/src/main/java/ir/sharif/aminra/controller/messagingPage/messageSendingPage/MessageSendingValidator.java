package ir.sharif.aminra.controller.messagingPage.messageSendingPage;

import ir.sharif.aminra.models.User;
import ir.sharif.aminra.util.Config;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessageSendingValidator {

    static private final Logger logger = LogManager.getLogger(MessageSendingValidator.class);

    String SendMessageError(User writer, User receiver) {

        Config errorsConfig = Config.getConfig("messageSendingPage");

        if(writer.equals(receiver)) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("messageToSelfError");
        }
        if(!receiver.isActive()) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("messageToDeactivatedUserError");
        }
        if (!writer.getFollowings().contains(receiver.getId()) && !receiver.getFollowings().contains(writer.getId())) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("invalidFollowError");
        }

        if (writer.getBlockList().contains(receiver.getId())) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("youBlockedReceiverError");
        }
        if (receiver.getBlockList().contains(writer.getId())) {
            logger.info(String.format("user %s wants to send an invalid message.", writer.getUsername()));
            return errorsConfig.getProperty("receiverBlockedYouError");
        }
        return "";
    }
}
