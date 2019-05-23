package de.felix.chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import de.felix.chess.piece.Pawn;
import de.felix.chess.piece.Piece;
import de.felix.chess.piece.Piece.Side;
import de.felix.chess.piece.Tower;

@SuppressWarnings("serial")
public final class Board extends JPanel implements Runnable {

	public static final int FIELD_SIZE = 100;

	private static final int UPDATES = 16;
	private static final Color[] COLORS = {new Color(0x759652), new Color(0xebebd0)};
	private static final Color VALID_MOVE = new Color(0xb9c845);
	private static final Color VALID_KILL_MOVE = new Color(0xc64545);
	private static final Piece[][] FIELDS = new Piece[8][8];

	private final Side side = Side.WHITE;

	private Piece selected;

	public Board() {
		setPreferredSize(new Dimension(8 * FIELD_SIZE, 8 * FIELD_SIZE));

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(final MouseEvent e) {
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

		});

		setup();
	}

	private void setup() {
		// other side

		for(int o = 0; o < 8; o++) {
			addPiece(new Pawn(this, side.other(), o, 1));
		}

		addPiece(new Tower(this, side.other(), 0, 0));
		addPiece(new Tower(this, side.other(), 7, 0));

		// my side

		for(int o = 0; o < 8; o++) {
			addPiece(new Pawn(this, side, o, 6));
		}

		addPiece(new Tower(this, side, 0, 7));
		addPiece(new Tower(this, side, 7, 7));
	}

	private void addPiece(final Piece piece) {
		FIELDS[piece.getY()][piece.getX()] = piece;
	}

	public Piece getPieceAt(final int x, final int y) {
		return FIELDS[y][x];
	}

	public void setPiece(final Piece piece, final int x, final int y) {
		FIELDS[y][x] = piece;
	}

	@Override
	protected void paintComponent(final Graphics _g) {
		final Graphics2D g = (Graphics2D) _g;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int c = 0;

		int[][] moves = new int[8][8];

		if(selected != null) {
			moves = selected.getMoves();
		}

		for(int x = 0; x < 8; x++) {
			for(int y = 0; y < 8; y++) {
				if(moves[y][x] == 0) {
					g.setColor((c % 2) == 0 ? COLORS[1] : COLORS[0]);
				} else {
					if(moves[y][x] == 1) {
						g.setColor(VALID_MOVE);
					} else if((moves[y][x] == 2)) {
						if(getPieceAt(x, y) != null) {
							g.setColor(VALID_KILL_MOVE);
						} else {
							g.setColor((c % 2) == 0 ? COLORS[1] : COLORS[0]);
						}
					} else if(moves[y][x] == 3) {
						if(getPieceAt(x, y) != null) {
							g.setColor(VALID_KILL_MOVE);
						} else {
							g.setColor(VALID_MOVE);
						}
					}
				}

				g.fillRect(x * FIELD_SIZE, y * FIELD_SIZE, FIELD_SIZE, FIELD_SIZE);

				g.setColor((c % 2) == 0 ? COLORS[1] : COLORS[0]);

				g.drawRect((x * FIELD_SIZE) + 1, (y * FIELD_SIZE) + 1, FIELD_SIZE - 2, FIELD_SIZE - 2);

				if(FIELDS[y][x] != null) {
					FIELDS[y][x].render(g);
				}

				c ++;
			}

			c ++;
		}
	}

	@Override
	public void run() {
		final long updateInterval = 1000 / UPDATES;

		System.out.println("Interval time: " + updateInterval + " ms");

		long currentTickCollection = 0;
		int frames = 0;

		while(true) {
			final long now = System.currentTimeMillis();

			repaint();

			frames ++;

			final long updateTime = System.currentTimeMillis() - now;

			currentTickCollection += updateInterval - updateTime;

			if(currentTickCollection >= 1000) {
				currentTickCollection -= 1000;

				if(OnlineChess.debug) {
					System.out.println(frames);
				}

				frames = 0;
			}

			try {
				Thread.sleep(Math.max(0l, updateInterval - updateTime));
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
