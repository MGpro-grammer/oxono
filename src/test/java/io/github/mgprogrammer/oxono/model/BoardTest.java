package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    public void isTheMoveMethodIsMovingInBoard() {
        Board board = new Board();
        Pos currentPosTotemX = new Pos(2, 2);
        Pos nextPosTotemX = new Pos(2, 3);
        board.doMove(Symbol.X, nextPosTotemX, currentPosTotemX);
        assertTrue(board.isEmpty(currentPosTotemX) && !board.isEmpty(nextPosTotemX));
    }

    @Test
    public void isTheInsertMethodIsInsertingInBoard() {
        Board board = new Board();
        Pos insertPos = new Pos(0 , 0);
        boolean isEmptyBeforeThePlacement = board.isEmpty(insertPos);
        board.doInsert(new Token(Symbol.X, Color.PINK), insertPos);
        boolean isEmptyAfterThePlacement = board.isEmpty(insertPos);
        assertTrue(isEmptyBeforeThePlacement && !isEmptyAfterThePlacement);
    }

    @Test
    public void isValidMoveHorizontalLeftAndRight() {
        Board board = new Board();
        Pos moveLeftTotemX = new Pos(2, 0);
        Pos moveRightTotemY = new Pos(2, 5);
        assertTrue(board.isValidMove(Symbol.X, moveLeftTotemX) && board.isValidMove(Symbol.X, moveRightTotemY));
    }

    @Test
    public void isValidMoveVerticalUpAndDown() {
        Board board = new Board();
        Pos moveUpTotemX = new Pos(0, 2);
        Pos moveDownTotemY = new Pos(5, 2);
        assertTrue(board.isValidMove(Symbol.X, moveUpTotemX) && board.isValidMove(Symbol.X, moveDownTotemY));
    }

    @Test
    public void isNotValidIfTokenBetweenNextPosAndTotem() {
        Board board = new Board();
        board.doInsert(new Token(Symbol.X, Color.PINK), new Pos(1, 2));
        assertFalse(board.isValidMove(Symbol.X, new Pos(0, 2)));
    }

    @Test
    public void isValidToInsertTokenNearToTotem() {
        Board board = new Board();
        boolean isInsertToken = board.isValidInsert(Symbol.X, new Pos(2, 1));
        //near to the Totem X locate in (2, 2)
        assertTrue(isInsertToken);
    }

    @Test
    public void isNotValidToInsertTokenFarFromTotem() {
        Board board = new Board();
        boolean isInsertToken = board.isValidInsert(Symbol.X, new Pos(2, 0));
        // there is an empty space between the token and the Totem X
        assertFalse(isInsertToken);
    }

    @Test
    public void isCheckWinVerticalWorking() {
        Board board = new Board();
        Token token = new Token(Symbol.X, Color.BLACK);
        board.doInsert(token, new Pos(1, 1));
        board.doInsert(token, new Pos(1, 2));
        board.doInsert(token, new Pos(1, 3));
        board.doInsert(token, new Pos(1, 4));
        assertTrue(board.checkWin());
    }

    @Test
    public void isCheckWinHorizontalWorking() {
        Board board = new Board();
        Token token = new Token(Symbol.X, Color.BLACK);
        board.doInsert(token, new Pos(2, 4));
        board.doInsert(token, new Pos(3, 4));
        board.doInsert(token, new Pos(4, 4));
        board.doInsert(token, new Pos(5, 4));
        assertTrue(board.checkWin());
    }
}