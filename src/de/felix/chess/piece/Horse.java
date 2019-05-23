package de.felix.chess.piece;

import de.felix.chess.Board;

public class Horse extends Piece {

	public Horse(final Board board, final Side side, final int x, final int y) {
		super(board, "horse", side, x, y);
	}

	@Override
	public int[][] getMoves() {
		final int[][] moves = new int[8][8];

		return moves;
	}

}
