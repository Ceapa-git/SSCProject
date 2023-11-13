package org.ssc.gui.panels.canvas;

import org.ssc.gui.BlockPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainCanvasPanel extends JPanel {
    private Point offset;
    private BlockPanel focus;

    public MainCanvasPanel() {
        setLayout(null);
        setBackground(Color.black);
        setFocusable(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offset = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int deltaX = e.getX() - offset.x;
                int deltaY = e.getY() - offset.y;
                for (Component component : getComponents()) {
                    Point componentLocation = component.getLocation();
                    component.setLocation(componentLocation.x + deltaX, componentLocation.y + deltaY);
                }
                offset = e.getPoint();
                revalidate();
                repaint();
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Component component : getComponents()) {
                    if (component instanceof BlockPanel image) {
                        image.rescale(getHeight());
                    }
                }
            }
        });
        focus = null;
    }

    public void addBlock(BlockPanel blockPanel) {
        blockPanel.rescale(getHeight());
        add(blockPanel);
    }

    public void resetFocus() {
        focus = null;
    }

    public void sendChar(Character c) {
        if (focus == null) return;
        focus.sendChar(c);
    }

    public void setFocus(BlockPanel blockPanel) {
        focus = blockPanel;
    }
}
