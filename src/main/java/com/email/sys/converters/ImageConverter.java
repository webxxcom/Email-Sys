package com.email.sys.converters;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.junit.platform.commons.support.conversion.ConversionException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageConverter {

    private ImageConverter() {
    }

    public static byte[] fromImageToBytes(Image image){
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        writableImage.getPixelWriter().setPixels(0, 0, width, height, image.getPixelReader(), 0, 0);

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new ConversionException("Error converting " + image + " to bytes");
        }
    }

    public static Image fromBytesToImage(byte[] bytes){
        return new Image(new ByteArrayInputStream(bytes));
    }
}
