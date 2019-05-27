package de.felix.chess.piece;

import de.felix.chess.Board;

public class Queen extends Piece {

	private final Piece bishop, tower;

	public Queen(final Board board, final Side side, final int x, final int y) {
		super(board, "queen", side, x, y);

		bishop = new Bishop(board, side, x, y);
		tower = new Rook(board, side, x, y);
	}

	@Override
	public int[][] getMoves() {
		final int[][] moves = new int[8][8];

		copyMoves(tower.getMoves(), moves);
		copyMoves(bishop.getMoves(), moves);

		return moves;
	}

	@Override
	public int[][] getEnemySideMoves() {
		return getMoves();
	}

	@Override
	public void moved() {
		super.moved();

		bishop.x = x;
		bishop.y = y;

		tower.x = x;
		tower.y = y;
	}

}
