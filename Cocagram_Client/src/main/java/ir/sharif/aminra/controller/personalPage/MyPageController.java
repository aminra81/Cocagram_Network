package ir.sharif.aminra.controller.personalPage;

import ir.sharif.aminra.util.ImageUtils;
import ir.sharif.aminra.view.ViewManager;
import ir.sharif.aminra.view.personalPage.MyFXController;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.paint.ImagePattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class MyPageController {

    static private final Logger logger = LogManager.getLogger(MyPageController.class);

    public void refresh(byte[] bytes) {
        ImageUtils imageUtils = new ImageUtils();
        BufferedImage image = null;
        try {
            image = imageUtils.toBufferedImage(bytes);
        } catch (IOException e) {
            logger.warn("can't convert byte array to buffered image");
            e.printStackTrace();
        }
        if (!(ViewManager.getInstance().getCurPage().getFxController() instanceof MyFXController))
            return;
        MyFXController myFXController = (MyFXController) ViewManager.getInstance().getCurPage().getFxController();
        myFXController.getAvatar().setFill(new ImagePattern(SwingFXUtils.toFXImage(image, null)));
    }
}