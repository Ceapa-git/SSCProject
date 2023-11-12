package org.ssc.gui;

import org.ssc.model.Block;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class BlockPanel extends JPanel {
    private final Block block;
    private final ArrayList<ImageComponent> imageComponents;
    private Point moveOffset;
    private Point position;
    private double oldRatio = 1;
    private final BlockPanel This = this;
    private boolean snapped;
    private static final int snapRadius = 10;
    private int inType;

    public BlockPanel(Block block) {
        setLayout(null);
        setOpaque(false);
        this.block = block;
        this.block.setBlockPanel(this);
        String file = block.getBlockName() + ".txt";
        try (InputStream inputStream = this.getClass().getResourceAsStream(file)) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
            int n = Integer.parseInt(reader.readLine());
            imageComponents = new ArrayList<>(n);
            imageComponents.add(new ImageComponent(reader.readLine(), reader.readLine(), reader.readLine(), true));
            for (int i = 1; i < n; i++) {
                imageComponents.add(new ImageComponent(reader.readLine(), reader.readLine(), reader.readLine()));
            }
            n = Integer.parseInt(reader.readLine());
            for (int i = 0; i < n; i++) {
                var s = reader.readLine().split("\\s+");
                int connectionType = Integer.parseInt(s[0]);
                int index = Integer.parseInt(s[1]);
                int x = Integer.parseInt(s[2]);
                int y = Integer.parseInt(s[3]);
                int xLoc = Integer.parseInt(s[4]);
                int yLoc = Integer.parseInt(s[5]);
                imageComponents.get(index).addSnapPoint(connectionType, x, y, xLoc, yLoc);
                if (connectionType == 2) imageComponents.get(index).setSnapIndex(Integer.parseInt(s[6]));
                if (connectionType == 1) this.inType = 1;
                if (connectionType == 3) this.inType = 3;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        imageComponents.get(0).addDrag(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                moveOffset = e.getPoint();
                getParent().setComponentZOrder(This, 0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                moveOffset = e.getPoint();
                getParent().setComponentZOrder(This, 0);
            }
        }, new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - moveOffset.x;
                int dy = e.getY() - moveOffset.y;
                if (!snapped) {
                    Point inSnapPoint = This.getSnapPoint(inType).get(0);
                    for (Component component : getParent().getComponents()) {
                        if (component == This) continue;
                        if (component instanceof BlockPanel blockPanel) {
                            ArrayList<Point> outSnapPoints = blockPanel.getSnapPoint(inType - 1);
                            ArrayList<Point> outSnapLocations = blockPanel.getSnapLocation(inType - 1);
                            ArrayList<Integer> outSnapIndexes = blockPanel.getSnapIndex(inType - 1);
                            for (int i = 0; i < outSnapPoints.size(); i++) {
                                Point outSnapPoint = outSnapPoints.get(i);
                                if (outSnapPoint.distance(inSnapPoint) <= snapRadius) {
                                    if (inType == 1 && blockPanel.block.getNext() == null) {
                                        This.snapped = true;
                                        blockPanel.block.setNext(This.block);
                                        This.block.setPrevious(blockPanel.block);
                                    } else if (inType == 3 && blockPanel.block.getConnection(outSnapIndexes.get(i)) == null) {
                                        This.snapped = true;
                                        blockPanel.block.setConnection(This.block, outSnapIndexes.get(i));
                                        This.block.setPrevious(blockPanel.block);
                                    }
                                    if (This.snapped) {
                                        Point point = outSnapLocations.get(i);
                                        dx = (int) (point.getX() - getX());
                                        dy = (int) (point.getY() - getY());
                                        break;
                                    }
                                }
                            }
                            if (This.snapped) break;
                        }
                    }
                    recursiveMove(dx, dy);
                } else if (dx * dx + dy * dy >= snapRadius * snapRadius) {
                    snapped = false;
                    Block previous = This.block.getPrevious();
                    if (This.block == previous.getNext()) {
                        previous.setNext(null);
                    } else {
                        previous.removeConnection(This.block);
                    }
                    This.block.setPrevious(null);
                    recursiveMove(dx, dy);
                }
                revalidate();
                repaint();
            }
        });
        this.snapped = this.block.getPrevious() != null;
        //stretchImage(imageComponents.get(0),100,50);
        add(imageComponents.get(0).getDraggableArea());
        setSize(getMaxWidth(), getMaxHeight());
    }

    private void recursiveMove(int dx, int dy) {
        position = new Point(getX() + dx, getY() + dy);
        setLocation(position);
        if (this.block.getNext() != null && this.block.getNext().getBlockPanel() != null)
            this.block.getNext().getBlockPanel().recursiveMove(dx, dy);
        for (Block block : this.block.getConnections()) {
            if (block != null && block.getBlockPanel() != null)
                block.getBlockPanel().recursiveMove(dx, dy);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ImageComponent imageComponent : imageComponents) {
            g.drawImage(imageComponent.getImage(), (int) imageComponent.getPosition().getX(), (int) imageComponent.getPosition().getY(), imageComponent.getWidth(), imageComponent.getHeight(), this);
        }
        for (ImageComponent imageComponent : imageComponents) {
            if(imageComponent.isText()){
                g.drawImage(imageComponent.getText(), (int) imageComponent.getPosition().getX() + imageComponent.getTextOffset(), 0, this);
            }
        }

        //DEBUG connection points
        g.setColor(Color.WHITE);
        for (int i = 0; i < 4; i++) {
            for (Point point : getSnapPoint(i)) {
                point.translate(-getX(), -getY());
                g.drawRect(point.x, point.y, 1, 1);
            }
        }
    }

    public void rescale(int height) {
        double ratio = height / 1080.0;
        for (ImageComponent imageComponent : imageComponents)
            imageComponent.rescale(ratio);
        int newWidth = (int) (getMaxWidth() * ratio);
        int newHeight = (int) (getMaxHeight() * ratio);
        int newX = (int) (getX() * (ratio / oldRatio));
        int newY = (int) (getY() * (ratio / oldRatio));
        setSize(newWidth, newHeight);
        setLocation(newX, newY);
        oldRatio = ratio;
    }

    public void setPosition(int x, int y) {
        setLocation((int) (x * oldRatio), (int) (y * oldRatio));
    }

    private int getMaxWidth() {
        return Arrays.stream(imageComponents.toArray())
                .mapToInt(ImageComponent::getMaxX)
                .max().orElse(0);
    }

    private int getMaxHeight() {
        return Arrays.stream(imageComponents.toArray())
                .mapToInt(ImageComponent::getMaxY)
                .max().orElse(0);
    }

    private void stretchImage(ImageComponent imageComponent, int x, int y) {
        imageComponent.stretch(x, y);
        for (Integer index : imageComponent.getStretchX()) {
            imageComponents.get(index).moveOriginalPosition(x, 0);
        }
        for (Integer index : imageComponent.getStretchY()) {
            imageComponents.get(index).moveOriginalPosition(0, y);
        }
    }

    public ArrayList<Point> getSnapPoint(int type) {
        ArrayList<Point> points = new ArrayList<>();
        for (ImageComponent component : this.imageComponents) {
            if (component.getSnapType() == type) {
                Point point = component.getSnapPoint();
                if (point != null) {
                    point.translate(getX(), getY());
                    points.add(point);
                }
            }
        }
        return points;
    }

    public ArrayList<Point> getSnapLocation(int type) {
        ArrayList<Point> points = new ArrayList<>();
        for (ImageComponent component : this.imageComponents) {
            if (component.getSnapType() == type) {
                Point point = component.getSnapLocation();
                if (point != null) {
                    point.translate(getX(), getY());
                    points.add(point);
                }
            }
        }
        return points;
    }

    public ArrayList<Integer> getSnapIndex(int type) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (ImageComponent component : this.imageComponents) {
            if (component.getSnapType() == type) {
                indexes.add(component.getSnapIndex());
            }
        }
        return indexes;
    }
}
