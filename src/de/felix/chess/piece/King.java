package de.felix.chess.piece;

import de.felix.chess.Board;

public class King extends Piece {

	public King(final Board board, final Side side, final int x, final int y) {
		super(board, "king", side, x, y);
	}

	@Override
	public boolean isInDanger() {
		return (side == board.getPlaySide()) && (getAllEnemyMoves()[y][x] > 1);
	}

	@Override
	public int[][] getMoves() {
		final int[][] moves = new int[8][8], enemyMoves = getAllEnemyMoves();

		for(int oY = y - 1; oY < ((y - 1) + 3); oY++) {
			for(int oX = x - 1; oX < ((x - 1) + 3); oX++) {
				if((oY > -1) && (oY < 8) && (oX > -1) && (oX < 8)) {
					moves[oY][oX] = enemyMoves[oY][oX] <= 1 ? 3 : 0;
				}
			}
		}

		return moves;
	}

	@Override
	public int[][] getEnemySideMoves() {
		final int[][] moves = new int[8][8];

		for(int oY = y - 1; oY < ((y - 1) + 3); oY++) {
			for(int oX = x - 1; oX < ((x - 1) + 3); oX++) {
				if((oY > -1) && (oY < 8) && (oX > -1) && (oX < 8)) {
					moves[oY][oX] = 3;
				}
			}
		}

		return moves;
	}

}
