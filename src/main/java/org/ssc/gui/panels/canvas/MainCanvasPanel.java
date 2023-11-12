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

    public MainCanvasPanel() {
        setLayout(null);
        setBackground(Color.black);

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
    }

    public void addBlock(BlockPanel blockPanel) {
        blockPanel.rescale(getHeight());
        add(blockPanel);
    }
}
