package main;
import javax.swing.JFrame;

// vimport main.ChessGame;

public class Main
{
    public static void main(String args[])
    {
        JFrame frame  = new JFrame("Chess Game :");
        //frame.setResizable(false);
        ChessGame game = new ChessGame();
        //MouseClickListener mouse = new MouseClickListener(game);
        GamePanel gp = new GamePanel(game);
        frame.add(gp);
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        gp.launchGame();
        //game.play();
    }
}