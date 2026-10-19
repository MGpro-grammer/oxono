package io.github.mgprogrammer.oxono.model;

import io.github.mgprogrammer.oxono.model.Game;
import io.github.mgprogrammer.oxono.model.Player;
import io.github.mgprogrammer.oxono.model.Pos;
import io.github.mgprogrammer.oxono.model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void isMoveValidMove() {
        Symbol symbol = Symbol.X; //totem in (2 ; 2) so we want to move it to (2 ; 3)
        Game game = new Game();
        Pos validPos = new Pos(2, 3);

        assertTrue(game.move(symbol, validPos));
        assertFalse(game.isEmptyOfBoard(validPos));
    }

    @Test
    public void isMoveInvalidPosition() {
        Symbol symbol = Symbol.O;
        Game game = new Game();
        Pos invalidPos = new Pos(-1, -1);

        assertFalse(game.move(symbol, invalidPos));
    }

    @Test
    public void isMoveOccupiedPosition() {
        Symbol symbol = Symbol.O;
        Game game = new Game();
        Pos occupiedPos = new Pos(3, 3);

        game.move(symbol, occupiedPos);
        assertFalse(game.move(symbol, occupiedPos));
    }

    @Test
    public void isMoveWithUnavailableToken() {
        Symbol symbol = Symbol.X;
        Game game = new Game();
        Player player = game.getCurrentPlayer();

        while (!player.isEmptyTokenX()) {
            player.decrease(Symbol.X);
        }

        assertFalse(game.move(symbol, new Pos(0, 0)));
    }

    @Test
    public void isUndoWorking() {
        Symbol symbol = Symbol.X;
        Game game = new Game();

        boolean isNotEmptyBeforeTheUndoAndBeforeMove = game.isEmptyOfBoard(new Pos(2, 2));
        game.move(symbol, new Pos(2, 4));
        boolean isEmptyBeforeTheUndoAndAfterMove = game.isEmptyOfBoard(new Pos(2, 2));
        game.undo();
        boolean isEmptyAfterTheUndo = game.isEmptyOfBoard(new Pos(2, 2));
        assertFalse(isNotEmptyBeforeTheUndoAndBeforeMove);
        assertTrue(isEmptyBeforeTheUndoAndAfterMove);
        assertFalse(isEmptyAfterTheUndo);
    }

    @Test
    public void isRedoWorkingAfterAnUndo() {
        Symbol symbol = Symbol.X;
        Game game = new Game();

        boolean isNotEmptyBeforeTheUndoAndBeforeMove = game.isEmptyOfBoard(new Pos(2, 2));
        game.move(symbol, new Pos(2, 4));
        boolean isEmptyBeforeTheUndoAndAfterMove = game.isEmptyOfBoard(new Pos(2, 2));
        game.undo();
        boolean isEmptyAfterTheUndo = game.isEmptyOfBoard(new Pos(2, 2));
        game.redo();
        boolean isNotEmptyAfterTheUndoAndTheRedo = game.isEmptyOfBoard(new Pos(2, 2));
        assertFalse(isNotEmptyBeforeTheUndoAndBeforeMove);
        assertTrue(isEmptyBeforeTheUndoAndAfterMove);
        assertFalse(isEmptyAfterTheUndo);
        assertTrue(isNotEmptyAfterTheUndoAndTheRedo);
    }

    @Test
    public void isDrawWorking() {
        Game game = new Game();
        Player playerBlack = game.getPlayerBlack();
        Player playerPink = game.getPlayerPink();

        while (!playerBlack.isEmptyToken() || !playerPink.isEmptyToken()) {
            playerBlack.decrease(Symbol.X);
            playerBlack.decrease(Symbol.O);
            playerPink.decrease(Symbol.X);
            playerPink.decrease(Symbol.O);
        }

        assertTrue(game.draw());
    }

    @Test
    public void insertFailsForInvalidPosition() {
        Game game = new Game();
        Pos invalidPos = new Pos(-1, -1);

        assertFalse(game.insert(invalidPos));
    }

    @Test
    public void insertFailsForOccupiedPosition() {
        Game game = new Game();
        Pos occupiedPos = new Pos(1, 1);

        game.insert(occupiedPos);
        assertFalse(game.insert(occupiedPos));
    }

    @Test
    public void insertSucceedsForValidPosition() {
        Game game = new Game();
        game.move(Symbol.X, new Pos(2, 3));
        Pos validInsertPos = new Pos(2, 2);

        assertTrue(game.insert(validInsertPos));
        assertFalse(game.isEmptyOfBoard(validInsertPos));
    }
}