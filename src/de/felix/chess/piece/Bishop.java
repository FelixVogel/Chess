package de.felix.chess.piece;

import de.felix.chess.Board;

public class Bishop extends Piece {

	public Bishop(final Board board, final Side side, final int x, final int y) {
		super(board, "bishop", side, x, y);
	}

	@Override
	public int[][] getMoves() {
		final int[][] moves = new int[8][8];

		for(int oX = x + 1, oY = y + 1; oX < 8 && oY < 8; oX++, oY++) {
			if(board.getPieceAt(oX, oY) != null) {
				if(board.getPieceAt(oX, oY).side != side) {
					moves[oY][oX] = 3;
				}

				break;
			}

			moves[oY][oX] = 3;
		}

		for(int oX = x - 1, oY = y - 1; oX > -1 && oY > -1; oX--, oY--) {
			if(board.getPieceAt(oX, oY) != null) {
				if(board.getPieceAt(oX, oY).side != side) {
					moves[oY][oX] = 3;
				}

				break;
			}

			moves[oY][oX] = 3;
		}

		for(int oX = x - 1, oY = y + 1; oX > -1 && oY < 8; oX--, oY++) {
			if(board.getPieceAt(oX, oY) != null) {
				if(board.getPieceAt(oX, oY).side != side) {
					moves[oY][oX] = 3;
				}

				break;
			}

			moves[oY][oX] = 3;
		}

		for(int oX = x + 1, oY = y - 1; oX < 8 && oY > -1; oX++, oY--) {
			if(board.getPieceAt(oX, oY) != null) {
				if(board.getPieceAt(oX, oY).side != side) {
					moves[oY][oX] = 3;
				}

				break;
			}

			moves[oY][oX] = 3;
		}

		return moves;
	}

}
