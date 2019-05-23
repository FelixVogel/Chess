package de.felix.chess.piece;

import de.felix.chess.Board;

public class Horse extends Piece {

	public Horse(final Board board, final Side side, final int x, final int y) {
		super(board, "horse", side, x, y);
	}

	@Override
	public int[][] getMoves() {
		final int[][] moves = new int[8][8];

		if(y - 2 > -1) {
			if(x - 1 > -1) {
				moves[y - 2][x - 1] = 3;
			}

			if(x + 1 < 8) {
				moves[y - 2][x + 1] = 3;
			}
		}

		if(y + 2 < 8) {
			if(x - 1 > -1) {
				moves[y + 2][x - 1] = 3;
			}

			if(x + 1 < 8) {
				moves[y + 2][x + 1] = 3;
			}
		}

		if(x - 2 > -1) {
			if(y - 1 > -1) {
				moves[y - 1][x - 2] = 3;
			}

			if(y + 1 < 8) {
				moves[y + 1][x - 2] = 3;
			}
		}

		if(x + 2 < 8) {
			if(y - 1 > -1) {
				moves[y - 1][x + 2] = 3;
			}

			if(y + 1 < 8) {
				moves[y + 1][x + 2] = 3;
			}
		}

		return moves;
	}

}
