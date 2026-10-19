package io.github.mgprogrammer.oxono.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a board used in a game, consisting of a grid with pieces. The board tracks
 * the positions of two special pieces called Totems, and provides methods to interact
 * with and validate moves, inserts, and win conditions.
 */
public class Board {

    private final Piece[][] grid;
    private Pos posTotemX;
    private Pos posTotemO;

    /**
     * Constructs a new Board object, initializing a 6x6 grid with pieces set at predefined positions.
     *
     * The grid is initialized as a 6x6 array of Piece objects. Two Totem objects,
     * one with a symbol of X and the other with a symbol of O, are placed at positions (2, 2)
     * and (3, 3) respectively. The corresponding positions are also saved in fields
     * to track the locations of these Totem objects.
     */
    public Board() {
        this.grid = new Piece[6][6];

        this.grid[2][2] = new Totem(Symbol.X);
        this.grid[3][3] = new Totem(Symbol.O);

        this.posTotemX = new Pos(2, 2);
        this.posTotemO = new Pos(3, 3);
    }

    /**
     * Constructs a new game board of the specified size. The board is initialized with a grid
     * of pieces. Two totems are placed at the center of the board, one representing Symbol X
     * and the other Symbol O.
     *
     * @param size the size of the board. It must be an even positive integer; otherwise,
     *             an IllegalArgumentException is thrown.
     * @throws IllegalArgumentException if the size provided is not an even integer.
     */
    public Board(int size) {
        if (size % 2 != 0) {
            throw new IllegalArgumentException("Size of the grid is not even, it's a odd...");
        }

        this.grid = new Piece[size][size];

        int middle = (size - 1) / 2;
        this.grid[middle][middle] = new Totem(Symbol.X);
        this.grid[middle + 1][middle + 1] = new Totem(Symbol.O);
        
        this.posTotemX = new Pos(middle, middle);
        this.posTotemO = new Pos(middle + 1, middle + 1);

    }

    /**
     * Retrieves the piece located at the specified position on the board.
     *
     * @param pos the position on the board from which to retrieve the piece
     * @return the piece located at the specified position, or null if the position is empty
     */
    public Piece getPiece(Pos pos) {
        return this.grid[pos.x()][pos.y()];
    }

    /**
     * Retrieves the size of the grid.
     *
     * @return the size of the grid, represented by the length of the first dimension of the grid array.
     */
    public int getSize() {
        return this.grid.length;
    }

    /**
     * Checks if the given position is within the bounds of the board.
     *
     * @param pos The position to check, represented as a {@code Pos} object.
     *            It contains the x and y coordinates of the position.
     * @return {@code true} if the position is within the bounds of the board;
     *         {@code false} otherwise.
     */
    public boolean isInside(Pos pos) {
        return pos.x() > -1 && pos.x() < getSize() && pos.y() > -1 && pos.y() < getSize();
    }

    /**
     * Retrieves the position of the totem associated with the specified symbol.
     *
     * @param symbol the symbol whose associated totem position is to be retrieved; expected values are Symbol.X or Symbol.O
     * @return the position of the totem for the given symbol
     */
    public Pos getPosTotem(Symbol symbol) {
        if (Symbol.X == symbol) {
            return posTotemX;
        }
        return posTotemO;
    }

    /**
     * Checks whether the specified board position is empty.
     *
     * @param pos the position to check, represented as a {@code Pos} object with x and y coordinates
     * @return {@code true} if the specified position is empty (i.e., does not contain any piece), {@code false} otherwise
     */
    public boolean isEmpty(Pos pos) {
        return this.grid[pos.x()][pos.y()] == null;
    }

    /**
     * Computes and returns a list of neighboring positions around the totem
     * position of a given symbol that are within the board and currently empty.
     *
     * @param symbol The symbol whose totem's neighboring positions are to be computed.
     *               It can be either Symbol.X or Symbol.O.
     * @return A list of positions representing the valid neighboring positions
     *         around the totem of the given symbol.
     */
    private List<Pos> neighborsTotem(Symbol symbol) {
        List<Pos> neighbors = new ArrayList<>();
        int[] addX = {-1, 0, 0, 1};
        int[] addY = {0, -1, 1, 0};

        for (int i = 0; i < 4; i++) {
            Pos neighbor = new Pos(getPosTotem(symbol).x() + addX[i], getPosTotem(symbol).y() + addY[i]);

            if (isInside(neighbor) && isEmpty(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    /**
     * Updates the designated totem position for the specified symbol.
     * If the symbol is {@code X}, the position is stored as the totem position for {@code X}.
     * Otherwise, the position is stored as the totem position for {@code O}.
     *
     * @param symbol the symbol whose totem position is being set, either {@code Symbol.X} or {@code Symbol.O}
     * @param pos the position to associate with the symbol's totem
     */
    private void setPosTotem(Symbol symbol,Pos pos) {
        if (Symbol.X == symbol) {
            this.posTotemX = pos;
        }
        else {
            this.posTotemO = pos;
        }
    }

    /**
     * Moves a totem represented by the given symbol from its current position
     * to a new specified position on the board.
     *
     * @param symbol the symbol representing the totem being moved
     * @param newPos the new position to which the totem is moved
     * @param previousPos the current position of the totem before moving
     */
    protected void doMove(Symbol symbol, Pos newPos, Pos previousPos) {
        this.grid[previousPos.x()][previousPos.y()] = null;
        this.grid[newPos.x()][newPos.y()] = new Totem(symbol);
        setPosTotem(symbol, newPos);
    }

    /**
     * Checks if the move to the specified position is valid for the given symbol.
     * A move is considered valid if it follows the rules for horizontal or vertical movement.
     *
     * @param symbol the symbol representing the player to make the move
     * @param newPos the new position to move the symbol's totem to
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(Symbol symbol, Pos newPos) {
        if (getPosTotem(symbol).x() == newPos.x()) {
            return isValidMoveHorizontal(symbol, newPos);
        }
        else if (getPosTotem(symbol).y() == newPos.y()) {
            return isValidMoveVertical(symbol, newPos);
        }
        return false;
    }

    /**
     * Checks whether a horizontal move from the totem's current position to the specified new position is valid.
     * The method ensures that all positions in the horizontal path between the current position of the totem
     * and the new position are empty.
     *
     * @param symbol the symbol of the moving player, which determines the totem's current position.
     * @param newPos the new position to which the totem attempts to move.
     * @return true if all the positions in the horizontal path between the current totem position and the new position are empty,
     *         false otherwise.
     */
    private boolean isValidMoveHorizontal(Symbol symbol, Pos newPos) {
        boolean isValid = true;
        int begin = getPosTotem(symbol).y();
        int end = newPos.y();
        int step = begin < end ? 1 : -1;

        for (int y = begin + step; y != end; y += step) {
            if (!isEmpty(new Pos(getPosTotem(symbol).x(), y))) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Checks if a vertical move to a new position is valid for the given symbol.
     * A vertical move is considered valid if all positions along the path from the current
     * position of the symbol's totem to the new position are empty.
     *
     * @param symbol the symbol representing the player (e.g., X or O)
     * @param newPos the new position to which the symbol's totem is being moved
     * @return true if the vertical move is valid, false otherwise
     */
    private boolean isValidMoveVertical(Symbol symbol, Pos newPos) {
        boolean isValid = true;
        int begin = getPosTotem(symbol).x();
        int end = newPos.x();
        int step = begin < end ? 1 : -1;

        for (int x = begin + step; x != end; x += step) {
            if (!isEmpty(new Pos(x, getPosTotem(symbol).y()))) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Inserts a specified piece at a given position on the board.
     *
     * @param piece the game piece to be inserted
     * @param pos the position where the piece should be placed
     */
    protected void doInsert(Piece piece, Pos pos) {
        this.grid[pos.x()][pos.y()] = piece;
    }

    /**
     * Checks if the insertion of a piece at the given position is valid based on the symbol's neighbors.
     *
     * @param symbol the symbol to check for valid insertion (e.g., X or O).
     * @param pos the position to validate for insertion.
     * @return true if the position is a valid insertion point; false otherwise.
     */
    public boolean isValidInsert(Symbol symbol, Pos pos) {
        List<Pos> neighbors = neighborsTotem(symbol);
        for (Pos neighbor : neighbors) {
            if (neighbor.x() == pos.x() && neighbor.y() == pos.y()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is a winning condition in the current state of the board.
     * This method verifies both horizontal and vertical win conditions
     * by delegating the checks to the respective private methods
     * {@code checkWinHorizontal} and {@code checkWinVertical}.
     *
     * @return true if a winning condition is detected either horizontally
     *         or vertically, false otherwise.
     */
    public boolean checkWin() {
        return checkWinHorizontal() || checkWinVertical();
    }

    /**
     * Checks for a winning condition by evaluating sequences of four consecutive
     * pieces horizontally on the game board. This method iterates through each row
     * and examines every possible horizontal sequence of four positions within the
     * constraints of the board size. It uses the {@code checkFourInLine} method
     * to determine if a valid sequence is found.
     *
     * @return {@code true} if a winning horizontal sequence of four pieces is found,
     *         {@code false} otherwise.
     */
    private boolean checkWinHorizontal() {
        for (int x = 0; x < getSize(); x++) {
            for (int y = 0; y < (getSize() - 3); y++) {
                if (checkFourInLine(new Pos(x, y), 0, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks for a vertical win condition in the game grid.
     * Iterates through the grid and tests consecutive positions vertically
     * to determine if there are four consecutive pieces meeting the win criteria
     * (e.g., same symbol or color) starting from the given position.
     * Uses the {@code checkFourInLine} helper method to evaluate each group of four positions.
     *
     * @return true if a vertical win is found, false otherwise
     */
    private boolean checkWinVertical() {
        for (int x = 0; x < (getSize() - 3); x++) {
            for (int y = 0; y < getSize(); y++) {
                if (checkFourInLine(new Pos(x, y), 1, 0)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if four pieces in a line, starting at the specified position,
     * match based on their symbols or colors.
     * The line is determined by the direction provided using the dx and dy offsets.
     *
     * @param pos the starting position of the line to check
     * @param dx the horizontal movement offset to determine the direction of the line
     * @param dy the vertical movement offset to determine the direction of the line
     * @return true if four consecutive pieces in the specified direction have
     *         matching symbols or colors, false otherwise
     */
    private boolean checkFourInLine(Pos pos, int dx, int dy) {
        Piece first = this.grid[pos.x()][pos.y()];
        if (first == null || first instanceof Totem) {
            return false;
        }

        boolean fourSymbol = true;
        boolean fourColor = true;

        for (int i = 1; i < 4; i++) {
            int x = pos.x() + i * dx;
            int y = pos.y() + i * dy;

            Piece current = getPiece(new Pos(x, y));

            if (current == null || current instanceof Totem) {
                return false;
            }

            fourSymbol = fourSymbol && (first.getSymbol() == current.getSymbol());
            fourColor = fourColor && (first.getColor() == current.getColor());

            if (!fourSymbol && !fourColor) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if the given symbol's position is fully enclosed, meaning all its
     * orthogonally neighboring positions are either not empty or not on the grid.
     *
     * @param symbol the symbol whose position is being checked for being enclosed
     * @return true if the symbol's position is fully enclosed, otherwise false
     */
    public boolean isEnclosed(Symbol symbol) {
        List<Pos> neighbors = neighborsTotem(symbol);

        for (Pos neighbor : neighbors) {
            if ((neighbor.x() == getPosTotem(symbol).x() || neighbor.y() == getPosTotem(symbol).y()) && isEmpty(neighbor)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines whether a move to the specified position is valid for a given symbol
     * while being enclosed within certain constraints (horizontal or vertical alignment).
     *
     * @param symbol The symbol representing the piece (e.g., X or O).
     * @param newPos The new position to validate as a potential move.
     * @return true if the move is valid under the enclosed constraints; false otherwise.
     */
    public boolean isValidMoveEnclosed(Symbol symbol, Pos newPos) {
        if (getPosTotem(symbol).x() == newPos.x()) {
            return isValidMoveEnclosedTotemHorizontal(symbol, newPos);
        }
        else if (getPosTotem(symbol).y() == newPos.y()) {
            return isValidMoveEnclosedTotemVertical(symbol, newPos);
        }
        return false;
    }

    /**
     * Validates if the specified move is a valid vertical move for an enclosed totem.
     * The move is considered valid if it maintains the totem within its vertical column
     * and reaches the intended position while meeting the game's rules.
     *
     * @param symbol the symbol of the totem to validate the move for
     * @param newPos the new position to validate the move toward
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMoveEnclosedTotemVertical(Symbol symbol, Pos newPos) {
        if (getPosTotem(symbol).y() != newPos.y()) {
            return false;
        }

        int begin = getPosTotem(symbol).x();
        int end = newPos.x();
        int step = begin < end ? 1 : -1;
        boolean isFound = false;

        Pos firstEmptyPos = null;

        for (int x = begin + step; x != end + step && !isFound; x += step) {
            Pos currentPos = new Pos(x, getPosTotem(symbol).y());

            if (!isInside(currentPos)) {
                return false;
            }

            if (isEmpty(currentPos)) {
                firstEmptyPos = currentPos;
                isFound = true;
            }
        }
        return firstEmptyPos != null && firstEmptyPos.x() == newPos.x() && firstEmptyPos.y() == newPos.y();
    }

    /**
     * Checks if a move for the totem represented by the given symbol is valid
     * when moving horizontally to a new position in an enclosed scenario.
     *
     * @param symbol the symbol representing the totem (e.g., X or O)
     * @param newPos the new position to which the totem is trying to move horizontally
     * @return true if the move is valid and the totem can move to the new position; false otherwise
     */
    private boolean isValidMoveEnclosedTotemHorizontal(Symbol symbol, Pos newPos) {
        if (getPosTotem(symbol).x() != newPos.x()) {
            return false;
        }

        int begin = getPosTotem(symbol).y();
        int end = newPos.y();
        int step = begin < end ? 1 : -1;
        boolean isFound = false;

        Pos firstEmptyPos = null;

        for (int y = begin + step; y != end + step && !isFound; y += step) {
            Pos currentPos = new Pos(getPosTotem(symbol).x(), y);

            if (!isInside(currentPos)) {
                return false;
            }

            if (isEmpty(currentPos)) {
                firstEmptyPos = currentPos;
                isFound = true;
            }
        }
        return firstEmptyPos != null && firstEmptyPos.x() == newPos.x() && firstEmptyPos.y() == newPos.y();
    }

    /**
     * Determines if the totem associated with the specified symbol
     * is blocked both horizontally and vertically on the board.
     *
     * @param symbol the symbol representing the totem (e.g., X or O) to be checked.
     * @return true if the totem is blocked both horizontally and vertically;
     *         false otherwise.
     */
    public boolean isTotemBlocked(Symbol symbol) {
        return isTotemBlockedHorizontal(symbol) && isTotemBlockedVertical(symbol);
    }

    /**
     * Checks if the totem associated with the given symbol is blocked
     * horizontally, meaning there are no empty positions in the specified
     * row of the grid.
     *
     * @param symbol the symbol whose totem is being checked for blockage. It can
     *               be either Symbol.X or Symbol.O.
     * @return true if the row corresponding to the totem's x position is fully
     *         occupied (i.e., no empty positions); false otherwise.
     */
    private boolean isTotemBlockedHorizontal(Symbol symbol) {
        for (int y = 0; y < getSize(); y++) {
            if (isEmpty(new Pos(getPosTotem(symbol).x(), y))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the column corresponding to the totem position of the specified symbol is blocked.
     * A column is considered blocked if all its cells within the board's size are not empty.
     *
     * @param symbol the symbol of the totem to check (e.g., Symbol.X or Symbol.O).
     * @return true if the column of the totem is blocked, otherwise false.
     */
    private boolean isTotemBlockedVertical(Symbol symbol) {
        for (int x = 0; x < getSize(); x++) {
            if (isEmpty(new Pos(x, getPosTotem(symbol).y()))) {
                return false;
            }
        }
        return true;
    }
}
