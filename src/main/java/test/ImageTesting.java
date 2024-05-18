package test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageTesting {

    public static boolean shouldUpdateColor(int x, int y, boolean checkLeft, boolean checkRight, boolean checkUp, boolean checkDown, Color color, BufferedImage img) throws Exception {

        if (checkLeft && checkRight) {
            throw new Exception("Invalid configuration passed: left/right");
        }

        if (checkUp && checkDown) {
            throw new Exception("Invalid configuration passed: up/down");
        }

        int xAdjustment = 0;
        int yAdjustment = 0;

        if (checkLeft) {
            xAdjustment -= 1;
        }

        if (checkRight) {
            xAdjustment += 1;
        }

        if (checkUp) {
            yAdjustment -= 1;
        }

        if (checkDown) {
            yAdjustment += 1;
        }

        Color otherColor = new Color(img.getRGB(x + xAdjustment, y + yAdjustment));

        return color.equals(otherColor);
    }

    public static void updateColor(int x, int y, BufferedImage output) {
        output.setRGB(x, y, Color.BLACK.getRGB());
    }

    public static void main(String[] args) throws Exception {

        BufferedImage img = ImageIO.read(new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\test_provinces.bmp"));
        BufferedImage output = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {


                boolean canCheckLeft = x != 0;
                boolean canCheckRight = x != img.getWidth() - 1;
                boolean canCheckUp = y != 0;
                boolean canCheckDown = y != img.getHeight() - 1;

                Color color = new Color(img.getRGB(x, y));

                /* By default, a pixel is transparent/white. We only want to change the color to black if one adjacent (including corners) on the original image is a same color. */
                if (canCheckLeft && canCheckUp && !shouldUpdateColor(x, y, true, false, true, false, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }

                if (canCheckUp && !shouldUpdateColor(x, y, false, false, true, false, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }

                if (canCheckRight && canCheckUp && !shouldUpdateColor(x, y, false, true, true, false, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }

                if (canCheckLeft && !shouldUpdateColor(x, y, true, false, false, false, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }

                if (canCheckRight && !shouldUpdateColor(x, y, false, true, false, false, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }

                if (canCheckLeft && canCheckDown && !shouldUpdateColor(x, y, true, false, false, true, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }

                if (canCheckDown && !shouldUpdateColor(x, y, false, false, false, true, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }

                if (canCheckRight && canCheckDown && !shouldUpdateColor(x, y, false, true, false, true, color, img)) {
                    updateColor(x, y, output);
                    continue;
                }
            }
        }

        // Make a transparent one (png) and one with a white background (bmp)
        ImageIO.write(output, "PNG", new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\test_output.png"));
        ImageIO.write(output, "BMP", new File("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\test_output.bmp"));
    }
}
