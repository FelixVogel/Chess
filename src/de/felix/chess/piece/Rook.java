package de.felix.chess.piece;

import de.felix.chess.Board;

public class Rook extends Piece {

	public Rook(final Board board, final Side side, final int x, final int y) {
		super(board, "tower", side, x, y);
	}

	@Override
	public int[][] getMoves() {
		final int[][] moves = new int[8][8];

		for(int oX = x - 1; oX > -1; oX--) {
			if(board.getPieceAt(oX, y) != null) {
				if(board.getPieceAt(oX, y).side != side) {
					moves[y][oX] = 3;
				}

				break;
			}

			moves[y][oX] = 3;
		}

		for(int oX = x + 1; oX < 8; oX++) {
			if(board.getPieceAt(oX, y) != null) {
				if(board.getPieceAt(oX, y).side != side) {
					moves[y][oX] = 3;
				}

				break;
			}

			moves[y][oX] = 3;
		}

		for(int oY = y - 1; oY > -1; oY--) {
			if(board.getPieceAt(x, oY) != null) {
				if(board.getPieceAt(x, oY).side != side) {
					moves[oY][x] = 3;
				}

				break;
			}

			moves[oY][x] = 3;
		}

		for(int oY = y + 1; oY < 8; oY++) {
			if(board.getPieceAt(x, oY) != null) {
				if(board.getPieceAt(x, oY).side != side) {
					moves[oY][x] = 3;
				}

				break;
			}

			moves[oY][x] = 3;
		}

		return moves;
	}

	@Override
	public int[][] getEnemySideMoves() {
		return getMoves();
	}

}
