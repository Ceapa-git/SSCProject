package org.ssc.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ImageLabel extends JLabel {
    private ImageIcon imageIcon;
    public ImageLabel(String file) {
        BufferedImage myPicture;
        try {
            myPicture = ImageIO.read(Objects.requireNonNull(this.getClass().getResource(file)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        imageIcon = new ImageIcon(myPicture);
        setIcon(imageIcon);
    }

    public void scaleImage(int width, int height) {
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(image, imageIcon.getDescription());
        setIcon(imageIcon);
    }
}
