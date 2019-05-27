package de.felix.chess.piece;

import de.felix.chess.Board;

public class Pawn extends Piece {

	private boolean isFirstMove = true;

	public Pawn(final Board board, final Side side, final int x, final int y) {
		super(board, "pawn", side, x, y);
	}

	@Override
	public int[][] getMoves() {
		final int[][] moves = new int[8][8];

		if(isFirstMove) {
			moves[y - 2][x] = 1;
		}

		if((y - 1) > -1) {
			moves[y - 1][x] = board.getPieceAt(x, y - 1) != null ? 0 : 1;

			if((x - 1) > -1) {
				moves[y - 1][x - 1] = 2;
			}

			if((x + 1) < 8) {
				moves[y - 1][x + 1] = 2;
			}
		}

		return moves;
	}

	@Override
	public int[][] getEnemySideMoves() {
		final int[][] moves = new int[8][8];

		if(isFirstMove) {
			moves[y + 2][x] = 1;
		}

		if((y + 1) < 8) {
			moves[y + 1][x] = board.getPieceAt(x, y + 1) != null ? 0 : 1;

			if((x - 1) > -1) {
				moves[y + 1][x - 1] = 2;
			}

			if((x + 1) < 8) {
				moves[y + 1][x + 1] = 2;
			}
		}

		return moves;
	}

	@Override
	public void moved() {
		super.moved();

		isFirstMove = false;
	}

}
