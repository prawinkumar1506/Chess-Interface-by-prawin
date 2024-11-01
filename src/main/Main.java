package main;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    private static JFrame mainFrame;
    private static ChessLearningPlatform platform;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowMainMenu(); // Create the main menu first
            showSplashScreen();      // Then show splash screen
        });
    }

    private static void showSplashScreen() {
        try {
            BufferedImage splashImage = ImageIO.read(new File("resources/splash.png.png"));
            JWindow splashWindow = new JWindow();
            splashWindow.getContentPane().add(new JLabel(new ImageIcon(splashImage)));
            splashWindow.pack();
            splashWindow.setLocationRelativeTo(null);
            splashWindow.setVisible(true);

            // Hide splash and show main frame after 2 seconds
            Timer timer = new Timer(2000, e -> {
                splashWindow.dispose();
                mainFrame.setVisible(true); // Show the main frame after splash screen
            });
            timer.setRepeats(false);
            timer.start();
        } catch (IOException e) {
            System.err.println("Could not load splash screen: " + e.getMessage());
            mainFrame.setVisible(true); // Show main frame even if splash screen fails
        }
    }

    private static void createAndShowMainMenu() {
        mainFrame = new JFrame("Chess Learning Platform");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setLocationRelativeTo(null);

        platform = new ChessLearningPlatform(mainFrame);
        mainFrame.setContentPane(platform);
        // Don't make it visible yet - will be made visible after splash screen
    }

    // This method will be called from ChessLearningPlatform to start your existing chess game
    public static void startExistingChessGame() {
        JFrame gameFrame = new JFrame("Chess Game");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ChessGame game = new ChessGame();
        GamePanel gp = new GamePanel(game);
        gameFrame.add(gp);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null); // Center the game window
        gameFrame.setVisible(true);

        gp.launchGame();
    }
}