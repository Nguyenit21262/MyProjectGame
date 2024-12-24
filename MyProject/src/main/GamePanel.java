package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60;
    Thread gamThread;
    PlayManager pm;

    
    public GamePanel() {

        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);
        //key listener
        this.addKeyListener(new KeyHandle());
        this.setFocusable(true);

        pm = new PlayManager();

        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_X) { // Khi nhấn phím 'x'
                    if (pm.gameOver) { // Nếu game đang kết thúc
                        restartGame(); // Khởi động lại game
                    }
                }
            }
        });
    }

    public void launchGame(){
        gamThread = new Thread(this);
        gamThread.start();

    }


    @Override
    public void run() {
        //game loop
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gamThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta --;
            }
        }
    }

    public void update(){
        if (KeyHandle.pausePressed == false && pm.gameOver == false){
        pm.update();
        }
    }

    private void restartGame() {
        if (gamThread != null) {
            gamThread.interrupt(); // Dừng thread cũ
            gamThread = null;
        }

        pm.reset(); // Reset trạng thái trong PlayManager

        gamThread = new Thread(this); // Tạo thread mới
        gamThread.start(); // Bắt đầu lại game
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        pm.drawAreaPlay(g2);
        //pm.drawScoreArea(g2);

        if (pm.gameOver) {
            g2.setFont(new Font("Arial", Font.BOLD, 50));
            g2.setColor(Color.RED);
            g2.drawString("GAME OVER", WIDTH / 2 - 150, HEIGHT / 2 - 50);
            g2.setFont(new Font("Arial", Font.PLAIN, 20));
            g2.setColor(Color.WHITE);
            g2.drawString("Press 'x' to restart", WIDTH / 2 - 100, HEIGHT / 2);
        }
    }
}


