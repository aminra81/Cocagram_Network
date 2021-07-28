package ir.sharif.aminra.controller.personalPage;

import ir.sharif.aminra.controller.ClientHandler;
import ir.sharif.aminra.database.ImageLoader;
import ir.sharif.aminra.models.User;
import ir.sharif.aminra.response.Response;
import ir.sharif.aminra.response.personalPage.UpdatePersonalPageResponse;
import ir.sharif.aminra.util.ImageUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyPageController {

    static private final Logger logger = LogManager.getLogger(MyPageController.class);

    private final ClientHandler clientHandler;

    public MyPageController(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public Response getUpdate() {
        User user =  clientHandler.getUser();

        ImageLoader imageLoader = new ImageLoader();
        BufferedImage bufferedImage = imageLoader.getByID(user.getAvatar());

        ImageUtils imageUtils = new ImageUtils();
        try {
            return new UpdatePersonalPageResponse(imageUtils.toByteArray(bufferedImage, "png"));
        } catch (IOException e) {
            logger.warn("can't convert buffered image to byte array");
            e.printStackTrace();
            return null;
        }
    }
}
