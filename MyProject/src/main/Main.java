package main;

import javax.swing.JFrame;

public class Main {
    public static void main (String [] args){
        JFrame window = new JFrame("Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                int confirm = javax.swing.JOptionPane.showConfirmDialog(
                    window,
                    "Are you sure you want to exit the game?",
                    "Exit Confirmation",
                    javax.swing.JOptionPane.YES_NO_OPTION
                );
        
                if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                    System.exit(1); 
                } else {
                    window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Giữ cửa sổ mở
                }
            }
        });

        //add game panel 
        GamePanel gp = new GamePanel();
        window.add(gp);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gp.launchGame();
    }
}

