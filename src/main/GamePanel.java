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

    // FPS!!
    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;//run program until you stop it


    //set player's default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;


    //first constructor
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        //if set to true, all the drawing from this component...
        //...will be done in an offscreen painting buffer...
        //...to enhance game's rendering performance.

        this.addKeyListener(keyH); //so the game panel recognizes the key input
        this.setFocusable(true); //w this, game panel can be 'focused' to receive key input.

    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //automatically calls the run method below
    }


    @Override
    public void run() {
        //game loop --codes that executes in every 'frame' in the fps
        while (gameThread != null) {
            // NOTE:
            // long currentTime = System.nanoTime();
            //--> returns the current value of the running
            // JVM's high resolution time source - in nanoseconds. 1mil nanosec = 1 sec

            //SLEEP STRATEGY -- GAME LOOP
            double drawInterval = (double) 1000000000 / FPS; //0.01666 seconds --> THE ALLOCATED TIME FOR A LOOP
            double nextDrawTime = System.nanoTime() + drawInterval;


            //1. update information (such as character positions)
            update();
            //2. draw the screen with the updated information
            repaint();

            //remaining time AFTER the program executes update() and repaint() in the given time
            //e.g. in our game, the allocated time is 0.01666 seconds.

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000; //sleep method only accepts milliseconds, so im converting from nano to milli

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime); //--> sleep pauses the game loop for the remaining time.

                nextDrawTime += drawInterval; //0.01666 seconds (one drawInterval) later will be the NEXT DRAW TIME

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //UPDATES the ucubik's coordinates
    public void update() {
        if (keyH.upPressed) {
            playerY -= playerSpeed;   //--> left corner is (0,0) so, y values increases as they go DOWN...
        } else if (keyH.downPressed) {
            playerY += playerSpeed;
        } else if (keyH.rightPressed) {
            playerX += playerSpeed;   //--> ...and increases as they go RIGHT
        } else if (keyH.leftPressed) {
            playerX -= playerSpeed;
        }
    }

    //built-in method
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.white);
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        //to save some memories...
        g2.dispose(); //dispose of this graphics context and release any system resources that is using it.
    }
}
