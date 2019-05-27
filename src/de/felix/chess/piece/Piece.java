package de.felix.chess.piece;

import static de.felix.chess.Board.FIELD_SIZE;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.felix.chess.Board;
import de.felix.chess.Renderable;

public abstract class Piece implements Renderable {

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

	/**
	 * Get the ID of this {@link Piece}
	 *
	 * @return The identifier of this {@link Piece}
	 */
	public final String getID() {
		return id;
	}

	/**
	 * Get the current X field position this {@link Piece} is on
	 *
	 * @return The current X field position
	 */
	public final int getX() {
		return x;
	}

	/**
	 * Get the current Y field position this {@link Piece} is on
	 *
	 * @return The current Y field position
	 */
	public final int getY() {
		return y;
	}

	/**
	 * Gets all valid moves this {@link Piece} can make
	 *
	 * @return The valid moves this {@link Piece} can make
	 */
	public abstract int[][] getMoves();

	/**
	 * Get all moves the enemy side {@link Piece} can make
	 *
	 * @return The valid moves the enemy side {@link Piece} can make
	 */
	public abstract int[][] getEnemySideMoves();

	/**
	 * Gets all moves the entire enemy side can make
	 *
	 * @return All moves the enemy side can make
	 */
	public final int[][] getAllEnemyMoves() {
		final int[][] enemyMoves = new int[8][8];

		for(int fX = 0; fX < 8; fX++) {
			for(int fY = 0; fY < 8; fY++) {
				if((board.getPieceAt(fX, fY) != null) && (board.getPieceAt(fX, fY).side != side)) {
					copyMoves(board.getPieceAt(fX, fY).getEnemySideMoves(), enemyMoves);
				}
			}
		}

		return enemyMoves;
	}

	/**
	 * Move this {@link Piece} to a new field
	 *
	 * @param x The X position of the new field
	 * @param y The Y position of the new field
	 */
	public final void move(final int x, final int y) {
		board.setPiece(null, this.x, this.y);

		this.x = x;
		this.y = y;

		board.setPiece(this, this.x, this.y);

		moved();
	}

	/**
	 * When a {@link Piece} was moved
	 */
	public void moved() {
		setSelected(false);
	}

	/**
	 * Get the {@link Side} this {@link Piece} is on
	 *
	 * @return The {@link Side} this {@link Piece} is on
	 */
	public final Side getSide() {
		return side;
	}

	/**
	 * Is this {@link Piece} selected?
	 *
	 * @return true, if this {@link Piece} is selected, false otherwise
	 */
	public final boolean isSelected() {
		return selected;
	}

	/**
	 * Is this {@link Piece} in danger? <br>
	 * This method returns false by default
	 *
	 * @return true, if this {@link Piece} is in danger, false otherwise
	 */
	public boolean isInDanger() {
		return false;
	}

	/**
	 * Set the selected state of this {@link Piece}
	 *
	 * @param selected If this {@link Piece} is selected or not
	 * @return This {@link Piece}
	 */
	public final Piece setSelected(final boolean selected) {
		this.selected = selected;

		return  this;
	}

	/**
	 * Copies the moved from the source array to the destination array,
	 * values are not overwritten, but rather replaced with a higher value
	 *
	 * @param src The source array
	 * @param dest The destination array
	 */
	public final void copyMoves(final int[][] src, final int[][] dest) {
		for(int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				if(dest[y][x] < src[y][x]) {
					dest[y][x] = src[y][x];
				}
			}
		}
	}

	/**
	 * Called when a click has been made regarding this {@link Piece}
	 *
	 * @param cx The field X position that has been clicked
	 * @param cy The field Y position that has been clicked
	 * @return Weather this action was successful or not
	 */
	public final boolean click(final int cx, final int cy) {
		final int[][] moves = getMoves();

		if(moves[cy][cx] != 0) {
			if(moves[cy][cx] == 1) {
				move(cx, cy);

				return true;
			} else if((moves[cy][cx] == 2) && (board.getPieceAt(cx, cy) != null) && (board.getPieceAt(cx, cy).getSide() != side)) {
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

	@Override
	public final void render(final Graphics2D g) {
		if(texture != null) {
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
