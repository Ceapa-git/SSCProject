package org.ssc.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

public class ImageComponent {
    private final Image originalImage;
    private Image image;
    private final JLabel draggableArea;
    private final Point originalPosition;
    private final Point movedPosition;
    private Point position;
    private Rectangle area;
    private final boolean draggable;
    private final boolean stretchable;
    private Point stretch;
    private final ArrayList<Integer> stretchX;
    private final ArrayList<Integer> stretchY;
    private Point snapPoint = null;
    private int snapType = -1;
    private double ratio = 1;
    private Point snapLocation = null;
    private int snapIndex = -1;

    public ImageComponent(String line, String stretchable) {
        this(line, stretchable, false);
    }

    public ImageComponent(String line, String stretchable, boolean draggable) {
        this.draggable = draggable;
        try {
            String[] tokens = line.split("\\s+");
            URL url = this.getClass().getResource(tokens[0]);
            Rectangle crop = new Rectangle(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));

            BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(url));
            BufferedImage croppedImage = new BufferedImage(crop.width, crop.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = croppedImage.createGraphics();
            g2d.drawImage(bufferedImage, 0, 0, crop.width, crop.height, crop.x, crop.y, crop.x + crop.width, crop.y + crop.height, null);
            g2d.dispose();

            originalImage = new ImageIcon(croppedImage).getImage();
            image = new ImageIcon(originalImage).getImage();

            if (this.draggable) {
                area = new Rectangle(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]), Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]));
                draggableArea = new JLabel();
                draggableArea.setBounds(area);
                draggableArea.setBorder(BorderFactory.createLineBorder(Color.RED));
                originalPosition = new Point(0, 0);
            } else {
                draggableArea = null;
                originalPosition = new Point(Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
            }
            stretch = new Point(0, 0);
            movedPosition = new Point(originalPosition);
            position = new Point(movedPosition);

            tokens = stretchable.split("\\s+");
            this.stretchable = Integer.parseInt(tokens[0]) == 1;
            if (this.stretchable) {
                stretchX = new ArrayList<>();
                stretchY = new ArrayList<>();
                int i = 1;
                boolean forX = true;
                while (i < tokens.length) {
                    if (tokens[i].charAt(0) == ':') forX = false;
                    else if (forX) stretchX.add(Integer.parseInt(tokens[i]));
                    else stretchY.add(Integer.parseInt(tokens[i]));
                    i++;
                }
            } else {
                stretchX = null;
                stretchY = null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Image getImage() {
        return image;
    }

    public void addDrag(MouseListener mouseListener, MouseMotionListener mouseMotionListener) {
        if (!draggable) return;
        draggableArea.addMouseListener(mouseListener);
        draggableArea.addMouseMotionListener(mouseMotionListener);
    }

    public JLabel getDraggableArea() {
        return draggableArea;
    }

    public int getOriginalWidth() {
        return originalImage.getWidth(null);
    }

    public int getOriginalHeight() {
        return originalImage.getHeight(null);
    }

    public int getWidth() {
        return image.getWidth(null);
    }

    public int getHeight() {
        return image.getHeight(null);
    }

    public void moveOriginalPosition(int x, int y) {
        movedPosition.setLocation(originalPosition);
        movedPosition.translate(x, y);
    }

    public Point getPosition() {
        return position;
    }

    public static int getMaxX(Object imageComponent) {
        return (int) (((ImageComponent) imageComponent).movedPosition.getX() +
                ((ImageComponent) imageComponent).getOriginalWidth() +
                ((ImageComponent) imageComponent).stretch.getX());
    }

    public static int getMaxY(Object imageComponent) {
        return (int) (((ImageComponent) imageComponent).movedPosition.getY() +
                ((ImageComponent) imageComponent).getOriginalHeight() +
                ((ImageComponent) imageComponent).stretch.getY());
    }

    public void rescale(double ratio) {
        int newWidth = (int) ((getOriginalWidth() + stretch.getX()) * ratio);
        int newHeight = (int) ((getOriginalHeight() + stretch.getY()) * ratio);
        image = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        if (draggable) {
            Rectangle newArea = new Rectangle(area);
            newArea.x = (int) (newArea.x * ratio);
            newArea.y = (int) (newArea.y * ratio);
            newArea.width = (int) ((newArea.width + (stretch.getX() * (newArea.width / (double) getOriginalWidth()))) * ratio);
            newArea.height = (int) ((newArea.height + (stretch.getY() * (newArea.height / (double) getOriginalHeight()))) * ratio);
            draggableArea.setBounds(newArea);
        }

        position = new Point(movedPosition);
        position.x = (int) (position.x * ratio);
        position.y = (int) (position.y * ratio);

        this.ratio = ratio;
    }

    public void stretch(int x, int y) {
        if (!stretchable) return;
        stretch = new Point(x, y);
    }

    public Integer[] getStretchX() {
        if (!stretchable || stretchX.isEmpty()) return new Integer[]{};
        return stretchX.toArray(new Integer[]{});
    }

    public Integer[] getStretchY() {
        if (!stretchable || stretchY.isEmpty()) return new Integer[]{};
        return stretchY.toArray(new Integer[]{});
    }

    public void addSnapPoint(int connectionType, int x, int y, int xLoc, int yLoc) {
        System.out.println("added ");
        System.out.println(connectionType);
        this.snapType = connectionType;
        this.snapPoint = new Point(x, y);
        this.snapLocation = new Point(xLoc, yLoc);
        System.out.println(snapPoint);
        System.out.println(snapLocation);
    }

    public void setSnapIndex(int index) {
        this.snapIndex = index;
    }

    public Point getSnapPoint() {
        if (this.snapType == -1) return null;
        Point point = new Point(snapPoint);
        point.translate((int) (stretch.getX() + originalPosition.x), (int) (stretch.getY() + originalPosition.y));
        return new Point((int) (point.x * ratio), (int) (point.y * ratio));
    }

    public int getSnapType() {
        return snapType;
    }

    public Point getSnapLocation() {
        if (this.snapType == -1) return null;
        Point point = new Point(this.snapLocation);
        point.translate((int) (stretch.getX() + originalPosition.x), (int) (stretch.getY() + originalPosition.y));
        return new Point((int) (point.x * ratio), (int) (point.y * ratio));
    }

    public Integer getSnapIndex() {
        if (this.snapType == -1) return null;
        return this.snapIndex;
    }
}
