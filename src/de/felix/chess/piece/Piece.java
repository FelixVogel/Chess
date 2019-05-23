package de.felix.chess.piece;

import static de.felix.chess.Board.FIELD_SIZE;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.felix.chess.Board;

public abstract class Piece {

	private static final Color SELECTED = new Color(0xf6f682);

	private final String id;
	private final BufferedImage texture;

	protected final Board board;
	protected final Side side;

	protected int x, y;
	protected boolean selected = false;

	public Piece(final Board board, final String id, final Side side, final int x, final int y) {
		this.id = id;
		this.board = board;
		this.side = side;
		this.x = x;
		this.y = y;

		BufferedImage texture = null;

		try {
			texture = ImageIO.read(Piece.class.getResource("/textures/" + (side == Side.WHITE ? "w_" : "b_") + id + ".png"));
		} catch (final IOException e) {
			e.printStackTrace();
		}

		this.texture = texture;
	}

	public final String getID() {
		return id;
	}

	public final int getX() {
		return x;
	}

	public final int getY() {
		return y;
	}

	public abstract int[][] getMoves();

	public final void move(final int x, final int y) {
		board.setPiece(null, this.x, this.y);

		this.x = x;
		this.y = y;

		board.setPiece(this, this.x, this.y);

		moved();
	}

	public void moved() {
		setSelected(false);
	}

	public final Side getSide() {
		return side;
	}

	public final Piece setSelected(final boolean selected) {
		this.selected = selected;

		return  this;
	}

	public final boolean click(final int cx, final int cy) {
		final int[][] moves = getMoves();

		if(moves[cy][cx] != 0) {
			if(moves[cy][cx] == 1) {
				move(cx, cy);

				return true;
			} else if(moves[cy][cx] == 2 && board.getPieceAt(cx, cy) != null && board.getPieceAt(cx, cy).getSide() != side) {
				move(cx, cy);

				return true;
			} else if(moves[cy][cx] == 3) {
				if(board.getPieceAt(cx, cy) == null) {
					move(cx, cy);

					return true;
				} else if(board.getPieceAt(cx, cy) != null) {
					if(board.getPieceAt(cx, cy).getSide() != side) {
						move(cx, cy);

						return true;
					} else {
						return false;
					}
				}
			}
		}

		return false;
	}

	public final void render(final Graphics2D g) {
		if(texture != null) {
			if(selected) {
				final Color prev = g.getColor();

				g.setColor(SELECTED);

				g.fillRect(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);

				g.setColor(prev);
			}

			g.drawImage(texture, x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE, null);
		}
	}

	public static enum Side {
		WHITE,
		BLACK,
		;

		public Side other() {
			return this == WHITE ? BLACK : WHITE;
		}
	}

}
