package org.ssc.gui;

import org.ssc.model.Block;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;
import java.util.Objects;

public class BlockPanel extends JPanel {
    private final Image originalImage;
    private Image image;
    private final JLabel draggableArea;
    private Point moveOffset;
    private final Block block;
    private Point position;
    private double oldRatio;

    public BlockPanel(Block block) {
        setLayout(null);
        setOpaque(false);
        this.block= block;
        String file = block.getBlockName() + ".txt";
        URL url = null;
        try(InputStream inputStream = this.getClass().getResourceAsStream(file))
        {
            if(inputStream == null) {
                url = this.getClass().getResource("Placeholder.drawio.png");
                assert url != null;
                originalImage = new ImageIcon(url).getImage();
            }
            else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                int n = Integer.parseInt(reader.readLine());
                String line;
                String[] tokens;
                //for
                line = reader.readLine();
                tokens = line.split("\\s+");
                url = this.getClass().getResource(tokens[0]);
                assert url != null;
                originalImage = new ImageIcon(url).getImage();
            }
        }
        catch (Exception e){
            throw new RuntimeException(e);
        }

        image = new ImageIcon(originalImage).getImage();
        draggableArea = new JLabel();
        draggableArea.setBounds(0, 0, image.getWidth(this), (int) (image.getHeight(this) * (80/(double)140)));
        draggableArea.setBorder(BorderFactory.createLineBorder(Color.RED));
        setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        draggableArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                moveOffset = e.getPoint();
            }
        });
        draggableArea.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                position = new Point(getX() + (e.getX() - moveOffset.x),getY() + (e.getY() - moveOffset.y));
                setLocation(position);
                revalidate();
                repaint();
            }
        });
        add(draggableArea);
        setSize(image.getWidth(this),image.getHeight(this));
        oldRatio=1;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this);
    }

    public void rescale(int height) {
        double ratio = height/1080.0;
        int newWidth=(int) (originalImage.getWidth(this)*ratio);
        int newHeight=(int) (originalImage.getHeight(this)*ratio);
        int newX=(int) (getX()*(ratio/oldRatio));
        int newY=(int) (getY()*(ratio/oldRatio));
        image = originalImage.getScaledInstance(newWidth, newHeight,Image.SCALE_SMOOTH);
        setSize(newWidth,newHeight);
        setLocation(newX,newY);
        draggableArea.setBounds(0, 0, newWidth, (int) (newHeight * (80/(double)140)));
        oldRatio = ratio;
    }

    public void setPosition(int x,int y){
        setLocation((int) (x*oldRatio), (int) (y*oldRatio));
    }
}
