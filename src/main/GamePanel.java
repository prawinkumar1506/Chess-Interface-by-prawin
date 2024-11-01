package main;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

//import main.ChessGame;

//  import java.awt.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

public class GamePanel extends JPanel implements MouseListener , Runnable  {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board b = new Board();
    ChessPiece selectedPiece = null;
    List<int[]> possibleMovesOfSelectedPiece = new ArrayList<>();
    ChessGame game = null;
    private int[] mouseStart = null;  // Stores the mouseStart position
    private int[] mouseEnd = null;


    public GamePanel(ChessGame game) {
        this.game = game;
        addMouseListener(this);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));  // This is valid in JPanel
        setBackground(Color.BLACK);
    }

    public void launchGame()
    {
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void run() {

        // GAME LOOP
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
    
        while (gameThread != null) {
            currentTime = System.nanoTime();
    
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
    
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    private void update()
    {
        b.boardGrid = game.getBoardPieces();
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        b.draw(g2);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            int x = e.getX() / 100;
            int y = (e.getY()) / 100;
            if (mouseStart == null &&  b.getPieceAt(y,x) == null){
                return;
            }

            if (mouseStart == null) {
                mouseStart = new int[]{x, y};
                selectedPiece = b.getPieceAt(y,x);
                possibleMovesOfSelectedPiece =  b.getPieceAt(y,x).getLegalMoves(game.getChessBoard());
                b.setPossibleSquares(possibleMovesOfSelectedPiece);
                System.out.println("Start: " + mouseStart[0] + " " + mouseStart[1]);
            } else {
                mouseEnd = new int[]{x, y};
                System.out.println("End: " + mouseEnd[0] + " " + mouseEnd[1]);
                if (game.processMouseMove(mouseStart, mouseEnd)) {
                    //System.out.println("works");
                    ArrayList<int[]> sq = new ArrayList<>();
                    sq.add(new int[]{x, y});
                    sq.add(new int[]{mouseStart[0], mouseStart[1]});
                    b.setPreviousMoveSquares(sq);
                    b.setPossibleSquares(new ArrayList<>());
                }

                // Reset for next move
                mouseStart = null;
                mouseEnd = null;
            }
        }
        if  (e.getButton() == MouseEvent.BUTTON2){

        }
    }


    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
}
