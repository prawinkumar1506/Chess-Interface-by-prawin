package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
//add this move class CHANGE1
// Add this class at the top level (outside ChessGame class)
class Move {
    private int fromX;
    private int fromY;
    private int toX;
    private int toY;

    public Move(int fromX, int fromY, int toX, int toY) {
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }

    public int getFromX() { return fromX; }
    public int getFromY() { return fromY; }
    public int getToX() { return toX; }
    public int getToY() { return toY; }
}

abstract class ChessPiece {
    protected String color;
    protected String name;
    protected int row;
    protected int col;
    

    public abstract String getType(); // Make it abstract

    public ChessPiece(String color, String name, int row, int col) {
        this.color = color;
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public abstract List<int[]> getLegalMoves(ChessBoard board);

    public String getColor() {
        return color;
    }

    public String toString() {
        return name + "-" + color.charAt(0);
    }

    public void move(int newRow, int newCol) {
        this.row = newRow;
        this.col = newCol;
    }
}

/*class Pawn extends ChessPiece {
    private boolean hasMoved = false;

    public Pawn(String color, int row, int col) {
        super(color, "P", row, col);
    }

    @Override
    public List<int[]> getLegalMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int direction = color.equals("White") ? 1 : -1;

        // Move forward
        if (board.isValidPosition(row + direction, col) && board.getPiece(row + direction, col) == null) {
            moves.add(new int[]{row + direction, col});

            // Double move on first turn
            if (!hasMoved && board.isValidPosition(row + 2 * direction, col) && board.getPiece(row + 2 * direction, col) == null) {
                moves.add(new int[]{row + 2 * direction, col});
            }
        }

        // Capture diagonally
        for (int colOffset : new int[]{-1, 1}) {
            if (board.isValidPosition(row + direction, col + colOffset)) {
                ChessPiece piece = board.getPiece(row + direction, col + colOffset);
                if (piece != null && !piece.getColor().equals(color)) {
                    moves.add(new int[]{row + direction, col + colOffset});
                }
            }
        }

        return moves;
    }

    @Override
    public void move(int newRow, int newCol) {
        super.move(newRow, newCol);
        hasMoved = true;
    }

    @Override
    public String getType() {
        return (color.equals("White")) ? "wp" : "bp"; // wp for white pawn, bp for black pawn
    }
}*/

class Pawn extends ChessPiece {
    private boolean hasMoved = false;

    public Pawn(String color, int row, int col) {
        super(color, "P", row, col);
    }

    @Override
    public List<int[]> getLegalMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int direction = color.equals("White") ? 1 : -1;
        int startRow = color.equals("White") ? 1 : 6;

        // Move forward
        if (board.isValidPosition(row + direction, col) && board.getPiece(row + direction, col) == null) {
            moves.add(new int[]{row + direction, col});

            // Double move on first turn
            if (row == startRow && board.isValidPosition(row + 2 * direction, col) && board.getPiece(row + 2 * direction, col) == null) {
                moves.add(new int[]{row + 2 * direction, col});
            }
        }

        // Capture diagonally
        for (int colOffset : new int[]{-1, 1}) {
            if (board.isValidPosition(row + direction, col + colOffset)) {
                ChessPiece piece = board.getPiece(row + direction, col + colOffset);
                if (piece != null && !piece.getColor().equals(color)) {
                    moves.add(new int[]{row + direction, col + colOffset});
                }
            }
        }

        return moves;
    }

    @Override
    public void move(int newRow, int newCol) {
        super.move(newRow, newCol);
        hasMoved = true;
    }

    @Override
    public String getType() {
        return (color.equals("White")) ? "wp" : "bp"; // wp for white pawn, bp for black pawn
    }
}

class Rook extends ChessPiece {
    boolean hasMoved = false;

    public boolean hasMoved() {
        return hasMoved;
    }

    public Rook(String color, int row, int col) {
        super(color, "R", row, col);
    }

    @Override
    public List<int[]> getLegalMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];
                if (!board.isValidPosition(newRow, newCol)) break;

                ChessPiece piece = board.getPiece(newRow, newCol);
                if (piece == null) {
                    moves.add(new int[]{newRow, newCol});
                } else {
                    if (!piece.getColor().equals(color)) {
                        moves.add(new int[]{newRow, newCol});
                    }
                    break;
                }
            }
        }
        return moves;
    }
    public String getType() {
        return (color.equals("White")) ? "wr" : "br";
    }

    @Override
    public void move(int newRow, int newCol) {
        super.move(newRow, newCol); // Call the superclass method to update position
        hasMoved = true; // Mark the rook as moved
    }
}

class Knight extends ChessPiece {
    public Knight(String color, int row, int col) {
        super(color, "N", row, col);
    }

    @Override
    public List<int[]> getLegalMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] knightMoves = {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

        for (int[] move : knightMoves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (board.isValidPosition(newRow, newCol)) {
                ChessPiece piece = board.getPiece(newRow, newCol);
                if (piece == null || !piece.getColor().equals(color)) {
                    moves.add(new int[]{newRow, newCol});
                }
            }
        }

        return moves;
    }

    public String getType() {
        return (color.equals("White")) ? "wn" : "bn";
    }
}

class Bishop extends ChessPiece {
    public Bishop(String color, int row, int col) {
        super(color, "B", row, col);
    }

    @Override
    public List<int[]> getLegalMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];
                if (!board.isValidPosition(newRow, newCol)) break;

                ChessPiece piece = board.getPiece(newRow, newCol);
                if (piece == null) {
                    moves.add(new int[]{newRow, newCol});
                } else {
                    if (!piece.getColor().equals(color)) {
                        moves.add(new int[]{newRow, newCol});
                    }
                    break;
                }
            }
        }

        return moves;
    }
    public String getType() {
        return (color.equals("White")) ? "wb" : "bb";
    }
}

class Queen extends ChessPiece {
    public Queen(String color, int row, int col) {
        super(color, "Q", row, col);
    }

    @Override
    public List<int[]> getLegalMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int newRow = row + i * dir[0];
                int newCol = col + i * dir[1];
                if (!board.isValidPosition(newRow, newCol)) break;

                ChessPiece piece = board.getPiece(newRow, newCol);
                if (piece == null) {
                    moves.add(new int[]{newRow, newCol});
                } else {
                    if (!piece.getColor().equals(color)) {
                        moves.add(new int[]{newRow, newCol});
                    }
                    break;
                }
            }
        }

        return moves;
    }

    public String getType() {
        return (color.equals("White")) ? "wq" : "bq";
    }
}

class King extends ChessPiece {
    boolean hasMoved = false;
    boolean castle = true;

    public boolean hasMoved() {
        return hasMoved;
    }

    public King(String color, int row, int col) {
        super(color, "K", row, col);
    }

    @Override
    public List<int[]> getLegalMoves(ChessBoard board) {
        List<int[]> moves = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        // Standard king moves
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (board.isValidPosition(newRow, newCol)) {
                ChessPiece piece = board.getPiece(newRow, newCol);
                if (piece == null || !piece.getColor().equals(getColor())) {
                    moves.add(new int[]{newRow, newCol});
                }
            }
        }

    // Castling moves
    if (castle && !hasMoved()) {
        // Kingside castling
        if (board.canCastleKingside(getColor())) {
            System.out.println(this.color+" King can KingSidecastle now.");
            moves.add(new int[]{row, col + 2}); // Move to g-file
        }
        // Queenside castling
        if (board.canCastleQueenside(getColor())) {
            System.out.println(this.color+" King can QueenSideCastle now.");
            moves.add(new int[]{row, col - 2}); // Move to c-file
        }
    }

    return moves;
}

    public String getType() {
        return (color.equals("White")) ? "wk" : "bk";
    }

    @Override
    public void move(int newRow, int newCol) {
        super.move(newRow, newCol);
        hasMoved = true; // Mark the king as moved
        castle = false;
        System.out.println("Cannot Castle "+this.color+" King anymore");
    }
}

class ChessBoard {
    private ChessPiece[][] board;
    

    public ChessBoard() {
        board = new ChessPiece[8][8];
        setupBoard();
    }

    private void setupBoard() {
        // Set up pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("White", 1, i);
            board[6][i] = new Pawn("Black", 6, i);
        }

        // Set up other pieces
        board[0][0] = new Rook("White", 0, 0);
        board[0][7] = new Rook("White", 0, 7);
        board[7][0] = new Rook("Black", 7, 0);
        board[7][7] = new Rook("Black", 7, 7);

        board[0][1] = new Knight("White", 0, 1);
        board[0][6] = new Knight("White", 0, 6);
        board[7][1] = new Knight("Black", 7, 1);
        board[7][6] = new Knight("Black", 7, 6);

        board[0][2] = new Bishop("White", 0, 2);
        board[0][5] = new Bishop("White", 0, 5);
        board[7][2] = new Bishop("Black", 7, 2);
        board[7][5] = new Bishop("Black", 7, 5);

        board[0][3] = new Queen("White", 0, 3);
        board[7][3] = new Queen("Black", 7, 3);

        board[0][4] = new King("White", 0, 4);
        board[7][4] = new King("Black", 7, 4);
    }

    public ChessPiece[][] getBoard() {
        return board;
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    public ChessPiece getPiece(int row, int col) {
        return board[row][col];
    }

    public void movePiece(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece piece = board[startRow][startCol];
        board[endRow][endCol] = piece;
        piece.move(endRow, endCol);
        board[startRow][startCol] = null;
    }
    
    public void movePieceWithValidation(int startRow, int startCol, int endRow, int endCol) {
        ChessPiece piece = getPiece(startRow, startCol);
    
        // Check if the piece exists
        if (piece == null) {
            System.out.println("Invalid move: No piece at starting position.");
            return; // Invalid move
        }
    
        // Handle castling for the King
        if (piece instanceof King) {
            King king = (King) piece;
    
            // Kingside castling (e1 to g1)
            if (startCol == 4 && endCol == 6 && !king.hasMoved()) {
                Rook rook = (Rook) getPiece(startRow, 7); // Rook on h1
                if (rook != null && !rook.hasMoved() && canCastleKingside(king.getColor())) {
                    // Perform the castling
                    movePiece(startRow, startCol, endRow, endCol); // Move king to g1
                    movePiece(startRow, 7, startRow, 5); // Move rook to f1
                    king.move(endRow, endCol); // Update king's position
                    rook.move(startRow, 5); // Update rook's position
                    return; // Castling successful
                }
            }
    
             // Queenside castling (e1 to c1)
            if (startCol == 4 && endCol == 2 && !king.hasMoved()) {
                Rook rook = (Rook) getPiece(startRow, 0); // Rook on a1
                if (rook != null && !rook.hasMoved() && canCastleQueenside(king.getColor())) {
                    // Perform the queenside castling
                    movePiece(startRow, startCol, endRow, endCol); // Move king to c1
                    movePiece(startRow, 0, startRow, 3); // Move rook to d1
                    king.move(endRow, endCol); // Update king's position and disable further castling
                    rook.move(startRow, 3); // Update rook's position
                    return; // Castling successful
                }
            }
        }

        List<int[]> legalMoves = piece.getLegalMoves(this);
    
        // Check if the desired move is legal
        boolean isLegalMove = legalMoves.stream()
            .anyMatch(move -> move[0] == endRow && move[1] == endCol);
    
        if (!isLegalMove) {
            System.out.println("Invalid move: Move is not legal.");
            return; // Invalid move
        }
    
        // Normal piece movement logic
        movePiece(startRow, startCol, endRow, endCol); // Move the piece
    }

    public boolean isInCheck(String color) {
        // Find the king
        int kingRow = -1, kingCol = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] instanceof King && board[i][j].getColor().equals(color)) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
            if (kingRow != -1) break;
        }

        // Check if any opponent piece can attack the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(color)) {
                    List<int[]> moves = piece.getLegalMoves(this);
                    for (int[] move : moves) {
                        if (move[0] == kingRow && move[1] == kingCol) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(String color) {
        if (!isInCheck(color)) return false;

        // Check if any move can get the king out of check
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(color)) {
                    List<int[]> moves = piece.getLegalMoves(this);
                    for (int[] move : moves) {
                        // Try the move
                        ChessPiece capturedPiece = board[move[0]][move[1]];
                        movePiece(i, j, move[0], move[1]);

                        // Check if the king is still in check
                        boolean stillInCheck = isInCheck(color);

                        // Undo the move
                        movePiece(move[0], move[1], i, j);
                        board[move[0]][move[1]] = capturedPiece;

                        if (!stillInCheck) {
                            return false; // Found a legal move that escapes check
                        }
                    }
                }
            }
        }
        return true; // No legal moves to escape check
    }

    public boolean isStalemate(String color) {
        if (isInCheck(color)) return false;

        // Check if there are any legal moves
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor().equals(color)) {
                    List<int[]> moves = piece.getLegalMoves(this);
                    for (int[] move : moves) {
                        // Try the move
                        ChessPiece capturedPiece = board[move[0]][move[1]];
                        movePiece(i, j, move[0], move[1]);

                        // Check if the move puts the king in check
                        boolean putsSelfInCheck = isInCheck(color);

                        // Undo the move
                        movePiece(move[0], move[1], i, j);
                        board[move[0]][move[1]] = capturedPiece;

                        if (!putsSelfInCheck) {
                            return false; // Found a legal move
                        }
                    }
                }
            }
        }
        return true; // No legal moves available
    }

    public boolean canCastleKingside(String color) {
        int kingRow = (color.equals("White")) ? 0 : 7;
        int kingCol = 4; // King's starting position
        int rookCol = 7; // Kingside rook
    
        ChessPiece king = getPiece(kingRow, kingCol);
        ChessPiece rook = getPiece(kingRow, rookCol);
    
        if (king == null || !(king instanceof King) || rook == null || !(rook instanceof Rook)) {
            return false;
        }
    
        if (((King) king).hasMoved() || ((Rook) rook).hasMoved()) {
            return false;
        }
    
        for (int i = 5; i < 7; i++) {
            if (getPiece(kingRow, i) != null) {
                return false; // Blocked by another piece
            }
        }
    
        if (isAttacked(kingRow, kingCol + 1, (color.equals("White")) ? "Black" : "White") ||
            isAttacked(kingRow, kingCol + 2, (color.equals("White")) ? "Black" : "White")) {
            return false; // Squares are attacked
        }
    
        return true; // Kingside castling is possible
    }
    
    public boolean canCastleQueenside(String color) {
        int kingRow = (color.equals("White")) ? 0 : 7;
        int kingCol = 4; // King's starting position
        int rookCol = 0; // Queenside rook
    
        ChessPiece king = getPiece(kingRow, kingCol);
        ChessPiece rook = getPiece(kingRow, rookCol);
    
        if (king == null || !(king instanceof King) || rook == null || !(rook instanceof Rook)) {
            return false;
        }
    
        // Only disable castling if either the King or the queenside rook has moved
        if (((King) king).hasMoved() || ((Rook) rook).hasMoved()) {
            return false;
        }
    
        // Check that squares between king and rook are clear
        for (int i = 1; i < 4; i++) {
            if (getPiece(kingRow, i) != null) {
                return false; // Path blocked by another piece
            }
        }
    
        // Ensure the path is not under attack
        if (isAttacked(kingRow, kingCol - 1, (color.equals("White")) ? "Black" : "White") ||
            isAttacked(kingRow, kingCol - 2, (color.equals("White")) ? "Black" : "White")) {
            System.out.println("Cannot castle queen side for "+king.color+" king");
            return false; // Path squares are under attack
        }
    
        return true; // Queenside castling is possible
    }
    

    public boolean canCastle(String color) {
        return canCastleKingside(color) || canCastleQueenside(color);
    }
    
    // Helper method to check if a square is attacked
    private boolean isAttacked(int row, int col, String color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = getPiece(i, j);
                if (piece != null && piece.getColor().equals(color)) {
                    if(piece instanceof King)
                    {
                        ((King)piece).castle = false;
                    }
                    List<int[]> moves = piece.getLegalMoves(this);
                    if(piece instanceof King)
                    {
                        ((King)piece).castle = true;
                    }
                    for (int[] move : moves) {
                        if (move[0] == row && move[1] == col) {
                            System.out.println("The path for the "+piece.color+" king is getting attacked.");
                            return true; // This square is attacked
                        }
                    }
                }
            }
        }
        return false; // Not attacked
    }

    public void printBoard() {
        System.out.println("  a   b   c   d   e   f   g   h");
        for (int i = 7; i >= 0; i--) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] == null ? ".   " : board[i][j] + " ");
            }
            System.out.println(i + 1);
        }
        System.out.println("  a   b   c   d   e   f   g   h");
    }

    public void setPiece(int row, int col, ChessPiece capturedPieceToCheck) {
        board[row][col] = capturedPieceToCheck;
    }
}

public class ChessGame {
    private ChessBoard board;
    private String currentPlayer;
    //CHANGE2 add this moveHistory and copy the ChessGame constructor and replace it
    private List<String> moveHistory;

    public ChessGame() {
        board = new ChessBoard();
        currentPlayer = "White";
        moveHistory = new ArrayList<>();  // Initialize the list here
    }
    public ChessBoard getChessBoard(){
        return board;
    }
    public ChessPiece[][] getBoardPieces(){
        return board.getBoard();
    }

    /*public void play() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.printBoard();
            System.out.println(currentPlayer + "'s turn:");
            System.out.print("Enter move (e.g., e2 e4) or 'quit' to end the game: ");
            String move = scanner.nextLine();

            if (move.equalsIgnoreCase("quit")) {
                break;
            }

            String[] parts = move.split(" ");
            if (parts.length != 2) {
                System.out.println("Invalid input. Please use the format 'e2 e4'.");
                continue;
            }

            int startCol = parts[0].charAt(0) - 'a';
            int startRow = Character.getNumericValue(parts[0].charAt(1)) - 1;
            int endCol = parts[1].charAt(0) - 'a';
            int endRow = Character.getNumericValue(parts[1].charAt(1)) - 1;

            ChessPiece piece = board.getPiece(startRow, startCol);

            if (piece == null || !piece.getColor().equals(currentPlayer)) {
                System.out.println("Invalid move. Please select your own piece.");
                continue;
            }

            List<int[]> legalMoves = piece.getLegalMoves(board);
            boolean isLegalMove = false;
            for (int[] legalMove : legalMoves) {
                if (legalMove[0] == endRow && legalMove[1] == endCol) {
                    isLegalMove = true;
                    break;
                }
            }

            if (!isLegalMove) {
                System.out.println("Invalid move. Please try again.");
                continue;
            }

            // Make the move
            ChessPiece capturedPiece = board.getPiece(endRow, endCol);
            ChessPiece movingPiece = board.getPiece(startRow, startCol);
            board.movePiece(startRow, startCol, endRow, endCol);

            // Check if the move puts the current player in check
            if (board.isInCheck(currentPlayer)) {
                System.out.println("Invalid move. You cannot put yourself in check.");
                // Undo the move
                board.movePiece(endRow, endCol, startRow, startCol);
                movingPiece.move(startRow, startCol);
                if (capturedPiece != null) {
                    ChessPiece pieceAtEnd = board.getPiece(endRow, endCol);
                    if (pieceAtEnd != null) {
                        pieceAtEnd = capturedPiece;
                    } // Restore the captured piece
                }
                continue;
            }

            // Switch players
            currentPlayer = currentPlayer.equals("White") ? "Black" : "White";

            // Check for checkmate or stalemate
            if (board.isInCheck(currentPlayer)) {
                boolean isCheckmate = true;
                // Check if any move can get the player out of check
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessPiece pieceToCheck = board.getPiece(i, j);
                        if (pieceToCheck != null && pieceToCheck.getColor().equals(currentPlayer)) {
                            List<int[]> moves = pieceToCheck.getLegalMoves(board);
                            for (int[] move1 : moves) {
                                // Try the move
                                ChessPiece capturedPieceToCheck = board.getPiece(move1[0], move1[1]);
                                board.movePiece(i, j, move1[0], move1[1]);

                                // Check if the move gets the player out of check
                                if (!board.isInCheck(currentPlayer)) {
                                    isCheckmate = false;
                                }

                                // Undo the move
                                board.movePiece(move1[0], move1[1], i, j);
                                if (capturedPieceToCheck != null) {
                                    board.setPiece(move1[0], move1[1], capturedPieceToCheck);
                                }
                            }
                        }
                    }
                }

                if (isCheckmate) {
                    board.printBoard();
                    System.out.println("Checkmate! " + (currentPlayer.equals("White") ? "Black" : "White") + " wins!");
                    break;
                }
            } else if (board.isStalemate(currentPlayer)) {
                board.printBoard();
                System.out.println("Stalemate! The game is a draw.");
                break;
            }
        }

        scanner.close();
        System.out.println("Game over. Thank you for playing!");
    }*/
    // Existing constructor and methods...

    //CHANGE3 FROM HERE ADD TILL ProcessMouseMove class before the main method
    public void loadPGNFile(File pgnFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(pgnFile))) {
            StringBuilder pgnContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip metadata lines (those starting with '[')
                if (!line.trim().startsWith("[")) {
                    pgnContent.append(line).append(" ");
                }
            }
            parsePGN(pgnContent.toString());
        }
    }

    private void parsePGN(String pgnContent) {
        // Reset the board to initial position
        board = new ChessBoard();
        currentPlayer = "White";
        moveHistory.clear();

        // Remove comments and variations
        pgnContent = pgnContent.replaceAll("\\{[^}]*\\}", "");
        pgnContent = pgnContent.replaceAll("\\([^)]*\\)", "");

        // Split into tokens
        String[] tokens = pgnContent.split("\\s+");

        for (String token : tokens) {
            // Skip move numbers and game result
            if (token.matches("\\d+\\..*") || token.matches("1-0|0-1|1/2-1/2|\\*") || token.trim().isEmpty()) {
                continue;
            }

            // Process the move
            if (processMove(token)) {
                moveHistory.add(token);
                currentPlayer = currentPlayer.equals("White") ? "Black" : "White";
            }
        }
    }

    private boolean processMove(String moveNotation) {
        // Remove check/checkmate symbols
        moveNotation = moveNotation.replaceAll("[+#]", "");

        // Handle castling
        if (moveNotation.equals("O-O")) {
            return processCastling(true); // Kingside
        }
        if (moveNotation.equals("O-O-O")) {
            return processCastling(false); // Queenside
        }

        // Parse regular moves
        try {
            int toCol, toRow;
            int fromCol = -1, fromRow = -1;
            char pieceType = 'P';
            boolean isCapture = moveNotation.contains("x");

            // Remove capture symbol
            moveNotation = moveNotation.replace("x", "");

            // Check if move starts with a piece letter
            if (Character.isUpperCase(moveNotation.charAt(0))) {
                pieceType = moveNotation.charAt(0);
                moveNotation = moveNotation.substring(1);
            }

            // Get destination square
            String destination = moveNotation.substring(moveNotation.length() - 2);
            toCol = destination.charAt(0) - 'a';
            toRow = 8 - (destination.charAt(1) - '0');

            // Find the piece that can make this move
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    ChessPiece piece = board.getPiece(row, col);
                    if (piece != null &&
                            piece.getColor().equals(currentPlayer) &&
                            matchesPieceType(piece, pieceType)) {

                        if (isValidMove(row, col, toRow, toCol)) {
                            fromRow = row;
                            fromCol = col;
                            break;
                        }
                    }
                }
                if (fromRow != -1) break;
            }

            // Make the move if valid piece was found
            if (fromRow != -1 && fromCol != -1) {
                board.movePieceWithValidation(fromRow, fromCol, toRow, toCol);
                return true;
            }

        } catch (Exception e) {
            System.err.println("Error processing move: " + moveNotation);
            e.printStackTrace();
        }
        return false;
    }

    private boolean matchesPieceType(ChessPiece piece, char pieceType) {
        switch (pieceType) {
            case 'P': return piece instanceof Pawn;
            case 'R': return piece instanceof Rook;
            case 'N': return piece instanceof Knight;
            case 'B': return piece instanceof Bishop;
            case 'Q': return piece instanceof Queen;
            case 'K': return piece instanceof King;
            default: return false;
        }
    }

    private boolean processCastling(boolean kingSide) {
        int row = currentPlayer.equals("White") ? 0 : 7;

        // Check if king is in correct position
        ChessPiece king = board.getPiece(row, 4);
        if (!(king instanceof King) || !king.getColor().equals(currentPlayer)) {
            return false;
        }

        // Check if rook is in correct position
        int rookCol = kingSide ? 7 : 0;
        ChessPiece rook = board.getPiece(row, rookCol);
        if (!(rook instanceof Rook) || !rook.getColor().equals(currentPlayer)) {
            return false;
        }

        // Perform castling
        if (kingSide) {
            board.movePieceWithValidation(row, 4, row, 6); // Move king
            board.movePieceWithValidation(row, 7, row, 5); // Move rook
        } else {
            board.movePieceWithValidation(row, 4, row, 2); // Move king
            board.movePieceWithValidation(row, 0, row, 3); // Move rook
        }

        return true;
    }

    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Get the piece at the starting position
        ChessPiece piece = board.getPiece(fromRow, fromCol);
        if (piece == null) {
            return false;
        }

        // Check if the move is valid according to piece rules
        List<int[]> legalMoves = piece.getLegalMoves(board);
        for (int[] move : legalMoves) {
            if (move[0] == toRow && move[1] == toCol) {
                return true;
            }
        }
        return false;
    }
    public boolean processMouseMove(int[] start, int[] end) {
            int startCol = start[0];
            int startRow = start[1];
            int endCol = end[0];
            int endRow = end[1];

            ChessPiece piece = board.getPiece(startRow, startCol);

            if (piece == null || !piece.getColor().equals(currentPlayer)) {
                System.out.println("Invalid move. Please select your own piece.");
                //continue;
                return false;
            }

            List<int[]> legalMoves = piece.getLegalMoves(board);
            boolean isLegalMove = false;

            for (int[] legalMove : legalMoves) {
                if (legalMove[0] == endRow && legalMove[1] == endCol) {
                    isLegalMove = true;
                    break;
                }
            }

            if (!isLegalMove) {
                System.out.println("Invalid move. Please try again.");
                //continue;
                return false;
            }

            // Make the move
            ChessPiece capturedPiece = board.getPiece(endRow, endCol);
            ChessPiece movingPiece = board.getPiece(startRow, startCol);
            board.movePieceWithValidation(startRow, startCol, endRow, endCol);

            // Check if the move puts the current player in check
            if (board.isInCheck(currentPlayer)) {
                System.out.println("Invalid move. You cannot put yourself in check.");
                // Undo the move
                board.movePiece(endRow, endCol, startRow, startCol);
                movingPiece.move(startRow, startCol);
                if (capturedPiece != null) {
                    ChessPiece pieceAtEnd = board.getPiece(endRow, endCol);
                    if (pieceAtEnd != null) {
                        pieceAtEnd = capturedPiece;
                    } // Restore the captured piece
                }
                //continue;
                return false;
            }

            // Switch players
            currentPlayer = currentPlayer.equals("White") ? "Black" : "White";

            // Check for checkmate or stalemate
            if (board.isInCheck(currentPlayer)) {
                boolean isCheckmate = true;
                // Check if any move can get the player out of check
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        ChessPiece pieceToCheck = board.getPiece(i, j);
                        if (pieceToCheck != null && pieceToCheck.getColor().equals(currentPlayer)) {
                            List<int[]> moves = pieceToCheck.getLegalMoves(board);
                            for (int[] move1 : moves) {
                                // Try the move
                                ChessPiece capturedPieceToCheck = board.getPiece(move1[0], move1[1]);
                                board.movePiece(i, j, move1[0], move1[1]);

                                // Check if the move gets the player out of check
                                if (!board.isInCheck(currentPlayer)) {
                                    isCheckmate = false;
                                }

                                // Undo the move
                                board.movePiece(move1[0], move1[1], i, j);
                                if (capturedPieceToCheck != null) {
                                    board.setPiece(move1[0], move1[1], capturedPieceToCheck);
                                }
                            }
                        }
                    }
                }

                if (isCheckmate) {
                    board.printBoard();
                    System.out.println("Checkmate! " + (currentPlayer.equals("White") ? "Black" : "White") + " wins!");
                    //break;
                    return true;
                }
            } else if (board.isStalemate(currentPlayer)) {
                board.printBoard();
                System.out.println("Stalemate! The game is a draw.");
                //break;
                return true;
            }
            return true;
        }


    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        //game.play();

    }
}
