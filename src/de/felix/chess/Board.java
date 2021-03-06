package de.felix.chess;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;

import de.felix.chess.piece.Bishop;
import de.felix.chess.piece.King;
import de.felix.chess.piece.Knight;
import de.felix.chess.piece.Pawn;
import de.felix.chess.piece.Piece;
import de.felix.chess.piece.Piece.Side;
import de.felix.chess.piece.Queen;
import de.felix.chess.piece.Rook;

/**
 * Represents a Chess Board
 *
 * @author Felix Vogel
 */
public final class Board implements Renderable {

	public static final int FIELD_SIZE = 100;

	private static final Color[] COLORS = {new Color(0x759652), new Color(0xebebd0)};
	private static final Color SELECTED = new Color(0xf6f682);
	private static final Color VALID_MOVE = new Color(0xb9c845);
	private static final Color VALID_KILL_MOVE = new Color(0xc64545);
	private static final Piece[][] FIELDS = new Piece[8][8];

	private Side side;
	private Piece selected;

	public Board() {
		setup();
	}

	/**
	 * Setup the Chess Board
	 */
	private void setup() {
		side = Side.WHITE;

		// other side

		for(int o = 0; o < 8; o++) {
			addPiece(new Pawn(this, side.other(), o, 1));
		}

		addPiece(new Rook(this, side.other(), 0, 0));
		addPiece(new Knight(this, side.other(), 1, 0));
		addPiece(new Bishop(this, side.other(), 2, 0));
		addPiece(new King(this, side.other(), 3, 0));
		addPiece(new Queen(this, side.other(), 4, 0));
		addPiece(new Bishop(this, side.other(), 5, 0));
		addPiece(new Knight(this, side.other(), 6, 0));
		addPiece(new Rook(this, side.other(), 7, 0));

		// my side

		for(int o = 0; o < 8; o++) {
			addPiece(new Pawn(this, side, o, 6));
		}

		addPiece(new Rook(this, side, 0, 7));
		addPiece(new Knight(this, side, 1, 7));
		addPiece(new Bishop(this, side, 2, 7));
		addPiece(new King(this, side, 3, 7));
		addPiece(new Queen(this, side, 4, 7));
		addPiece(new Bishop(this, side, 5, 7));
		addPiece(new Knight(this, side, 6, 7));
		addPiece(new Rook(this, side, 7, 7));
	}

	/**
	 * Adds a {@link Piece} to this {@link Board} without firing {@link Piece#moved()}
	 *
	 * @param piece
	 */
	private void addPiece(final Piece piece) {
		FIELDS[piece.getY()][piece.getX()] = piece;
	}

	/**
	 * When this {@link Board} has been clicked
	 *
	 * @param e The {@link MouseEvent} passed down from the {@link Container}
	 */
	public void click(final MouseEvent e) {
		final int x = e.getX() / FIELD_SIZE;
		final int y = e.getY() / FIELD_SIZE;

		if((x < 8) && (x > -1) && (y < 8) && (y > -1)) {
			if((selected != null) && selected.click(x, y)) {
				selected = null;

				return;
			}

			if(FIELDS[y][x] != null) {
				if(FIELDS[y][x].getSide() == side) {
					if(selected != null) {
						selected.setSelected(false);
					}

					System.out.println("Selected: " + x + " | " + y);

					selected = FIELDS[y][x].setSelected(true);
				}
			}
		}
	}

	/**
	 * Get the play side of your {@link Board}
	 *
	 * @return The play side
	 */
	public Side getPlaySide() {
		return side;
	}

	/**
	 * Get a {@link Piece} at the defined position
	 *
	 * @param x The X field position
	 * @param y The Y field position
	 * @return The {@link Piece} at this position, can be null
	 */
	public Piece getPieceAt(final int x, final int y) {
		return FIELDS[y][x];
	}

	/**
	 * Fill the defined position with the defined {@link Piece}
	 *
	 * @param piece The {@link Piece} to set at the defined position
	 * @param x The X field position
	 * @param y The Y field position
	 */
	public void setPiece(final Piece piece, final int x, final int y) {
		FIELDS[y][x] = piece;
	}

	@Override
	public void render(final Graphics2D g) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int c = 0;

		int[][] moves = new int[8][8];

		if(selected != null) {
			moves = selected.getMoves();
		}

		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				final Color fieldColor = (c % 2) == 0 ? COLORS[1] : COLORS[0];

				if(moves[y][x] == 0) {
					g.setColor(fieldColor);
				} else {
					if(moves[y][x] == 1) {
						g.setColor(VALID_MOVE);
					} else if(moves[y][x] == 2) {
						if((getPieceAt(x, y) != null) && (getPieceAt(x, y).getSide() != selected.getSide())) {
							g.setColor(VALID_KILL_MOVE);
						} else {
							g.setColor(fieldColor);
						}
					} else if(moves[y][x] == 3) {
						if(getPieceAt(x, y) != null) {
							if(getPieceAt(x, y).getSide() != selected.getSide()) {
								g.setColor(VALID_KILL_MOVE);
							} else {
								g.setColor(fieldColor);
							}
						} else {
							g.setColor(VALID_MOVE);
						}
					}
				}

				g.fillRect(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);

				g.setColor(fieldColor);

				g.drawRect((x * FIELD_SIZE) + 1, (y * FIELD_SIZE) + 1, FIELD_SIZE - 2, FIELD_SIZE - 2);

				if(FIELDS[y][x] != null) {
					if(FIELDS[y][x].isSelected()) {
						g.setColor(SELECTED);

						g.fillRect(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);

						g.setColor(fieldColor);

						g.drawRect((x * FIELD_SIZE) + 1, (y * FIELD_SIZE) + 1, FIELD_SIZE - 2, FIELD_SIZE - 2);
					}

					if(FIELDS[y][x].isInDanger()) {
						g.setColor(VALID_KILL_MOVE);

						g.fillRect(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);

						g.setColor(fieldColor);

						g.drawRect((x * FIELD_SIZE) + 1, (y * FIELD_SIZE) + 1, FIELD_SIZE - 2, FIELD_SIZE - 2);
					}

					FIELDS[y][x].render(g);
				}

				c ++;
			}

			c ++;
		}
	}

}
