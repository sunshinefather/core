package com.palmlink.core.image;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import net.coobird.thumbnailator.Thumbnails;

import com.palmlink.core.platform.exception.BusinessProcessException;

public final class CompressImage {

    private CompressImage() {

    }

    public static Integer[] compress(String sourcePath, String destPath, Integer width, Integer height) throws IOException {
        OutputStream destStream = null;
        InputStream inStream = null;
        int realWidth = 0;
        int realHeight = 0;
        Image img = new ImageIcon(sourcePath).getImage();

        Integer sourceWidth = img.getWidth(null);
        Integer sourceHeight = img.getHeight(null);
        if (sourceWidth < width && sourceHeight < height) {
            realWidth = sourceWidth;
            realHeight = sourceHeight;
        } else {
            double rate1 = (width * 1.0) / (sourceWidth * 1.0);
            double rate2 = (height * 1.0) / (sourceHeight * 1.0);
            double rate = rate1 > rate2 ? rate2 : rate1;

            realWidth = (int) Math.ceil(sourceWidth * rate);
            realHeight = (int) Math.ceil(sourceHeight * rate);
        }

        try {
            inStream = new FileInputStream(sourcePath);
            BufferedImage sourcebuffImg = ImageIO.read(inStream);
            Thumbnails.of(sourcebuffImg).size(realWidth, realHeight).outputQuality(1.0F).toFile(destPath);

        } catch (Exception e) {
            throw new BusinessProcessException(e.getMessage(), e);
        } finally {
            if (null != destStream) {
                destStream.close();
            }
            if (null != inStream) {
                inStream.close();
            }
        }

        return new Integer[] { realWidth, realHeight };
    }
}
