package main;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16; //16x16 tile
    final int scale = 3; //to make it 48x48

    final int tileSize = originalTileSize * scale; //16x3 = 48 happens in here
    final int maxScreenCol = 16; //16 tiles horizontally
    final int maxScreenRow = 12; //12 tiles vertically
    final int screenWidth = tileSize * maxScreenCol; //48*16=768 pixels
    final int screenHeight = tileSize * maxScreenRow; //48*12=576 pixels


    Thread gameThread;//run program until you stop it


    //first constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        //if set to true, all the drawing from this component...
        //...will be done in an offscreen painting buffer...
        //...to enhance game's rendering performance.

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //automatically calls the run method below
    }


    @Override
    public void run() {
        //game loop --codes that executes in every 'frame' in the fps
        while (gameThread != null) {
            //1. update information (such as character positions)
            update();
            //2. draw the screen with the updated information
            repaint();
        }
    }
    public void update() {

    }

    //built-in method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(100, 100, tileSize, tileSize);

        //to save some memories...
        g2.dispose(); //dispose of this graphics context and release any system resources that is using it.
    }
}
