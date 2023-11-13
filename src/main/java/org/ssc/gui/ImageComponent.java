package org.ssc.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Math.max;

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
    private final int hasText;
    private int textOffset = -1;
    private String text;
    private Image originalTextImage;
    private Image textImage;
    private static final Font originalFont;
    private static final int fontSize = 70;
    private static final int charWidth;
    private final BlockPanel blockPanel;

    static {
        try {
            InputStream inputStream = BlockPanel.class.getResourceAsStream("consola.ttf");
            Font f = Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(inputStream));
            originalFont = f.deriveFont(Font.PLAIN, fontSize);
            assert originalFont != null;
            BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
            Graphics2D tempGraphics = tempImage.createGraphics();
            tempGraphics.setFont(originalFont);
            FontMetrics fontMetrics = tempGraphics.getFontMetrics();
            charWidth = fontMetrics.stringWidth("a");
            tempGraphics.dispose();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ImageComponent(BlockPanel blockPanel, String line, String stretchable, String text) {
        this(blockPanel, line, stretchable, text, false);
    }

    public ImageComponent(BlockPanel blockPanel, String line, String stretchable, String text, boolean draggable) {
        this.draggable = draggable;
        this.blockPanel = blockPanel;
        try {
            String[] tokens = line.split("\\s+");
            URL url = this.getClass().getResource(tokens[0]);
            Rectangle crop = new Rectangle(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));

            BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(url));
            BufferedImage croppedImage = new BufferedImage(crop.width, crop.height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = croppedImage.createGraphics();
            g2d.drawImage(bufferedImage, 0, 0, crop.width, crop.height, crop.x, crop.y, crop.x + crop.width, crop.y + crop.height, null);
            g2d.setColor(Color.green);
            g2d.drawRect(0, 0, crop.width - 1, crop.height - 1);
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

            tokens = text.split("\\s+");
            this.hasText = Integer.parseInt(tokens[0]);
            if (this.hasText != 0) {
                this.textOffset = Integer.parseInt(tokens[1]);
                if(blockPanel.getBlockName().equals("Variable"))
                    this.text = blockPanel.getBlockStringValue();
                else
                    this.text = text.split(":")[1];
                int height = originalImage.getHeight(null);
                BufferedImage bufferedTextImage = new BufferedImage(this.text.length() * charWidth, height, BufferedImage.TYPE_INT_ARGB);
                g2d = bufferedTextImage.createGraphics();
                g2d.setFont(originalFont);
                g2d.setColor(Color.black);
                FontMetrics fontMetrics = g2d.getFontMetrics();
                int ascent = fontMetrics.getAscent();
                int descent = fontMetrics.getDescent();
                g2d.drawString(this.text, 0, (height - (ascent + descent)) / 2 + ascent);
                g2d.setColor(Color.cyan);
                g2d.drawRect(0, 0, this.text.length() * charWidth - 1, height - 1);
                g2d.dispose();
                originalTextImage = new ImageIcon(bufferedTextImage).getImage();
                textImage = new ImageIcon(bufferedTextImage).getImage();
            } else {
                this.text = "";
                originalTextImage = null;
                textImage = null;
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

        if (hasText != 0) redrawText();
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
        this.snapType = connectionType;
        this.snapPoint = new Point(x, y);
        this.snapLocation = new Point(xLoc, yLoc);
    }

    public void setSnapIndex(int index) {
        this.snapIndex = index;
    }

    public Point getSnapPoint() {
        if (this.snapType == -1) return null;
        Point point = new Point(snapPoint);
        point.translate((int) (stretch.getX() + movedPosition.x), (int) (stretch.getY() + movedPosition.y));
        return new Point((int) (point.x * ratio), (int) (point.y * ratio));
    }

    public int getSnapType() {
        return snapType;
    }

    public Point getSnapLocation() {
        if (this.snapType == -1) return null;
        Point point = new Point(this.snapLocation);
        point.translate((int) (stretch.getX() + movedPosition.x), (int) (stretch.getY() + movedPosition.y));
        return new Point((int) (point.x * ratio), (int) (point.y * ratio));
    }

    public Integer getSnapIndex() {
        if (this.snapType == -1) return null;
        return this.snapIndex;
    }

    public int isText() {
        return this.hasText;
    }

    public int getTextOffset() {
        return (int) (textOffset * ratio);
    }

    public Image getText() {
        return textImage;
    }

    private void redrawText() {
        int newWidth = (int) (originalTextImage.getWidth(null) * ratio);
        int newHeight = (int) (100 * ratio);
        textImage = originalTextImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    public void setTextString(String text){
        if(hasText == 0) return;
        this.text = text;
        int height = originalImage.getHeight(null);
        BufferedImage bufferedTextImage = new BufferedImage(max(this.text.length(), 1) * charWidth, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedTextImage.createGraphics();
        g2d.setFont(originalFont);
        g2d.setColor(Color.black);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int ascent = fontMetrics.getAscent();
        int descent = fontMetrics.getDescent();
        g2d.drawString(this.text, 0, (height - (ascent + descent)) / 2 + ascent);
        g2d.setColor(Color.cyan);
        g2d.drawRect(0, 0, this.text.length() * charWidth - 1, height - 1);
        g2d.dispose();
        originalTextImage = new ImageIcon(bufferedTextImage).getImage();
        blockPanel.stretchImage(this, this.text.length() * charWidth, 0);
        blockPanel.blockSetName(text);
        redrawText();
    }

    public String getTextString(){
        return text;
    }
}
