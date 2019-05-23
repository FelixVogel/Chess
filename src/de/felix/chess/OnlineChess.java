package de.felix.chess;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class OnlineChess {

	public static boolean debug = false;

	public static void main(final String[] args) {
		for(final String arg : args) {
			if(arg.equals("-debug")) {
				debug = true;
			}
		}

		EventQueue.invokeLater(() -> {
			final JFrame frame = new JFrame("Chess");

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			final ChessBoardRender render = new ChessBoardRender(new Board());

			frame.setContentPane(render);

			frame.pack();

			frame.setVisible(true);

			final Thread t = new Thread(render);

			t.setName("GAME_LOOP");

			t.start();
		});
	}

	@SuppressWarnings("serial")
	static class ChessBoardRender extends JPanel implements Runnable {

		private static final int UPDATES = 16;

		private final Board board;

		public ChessBoardRender(final Board board) {
			this.board = board;

			setPreferredSize(new Dimension(8 * Board.FIELD_SIZE, 8 * Board.FIELD_SIZE));

			addMouseListener(new MouseAdapter() {

				@Override
				public void mouseReleased(final MouseEvent e) {
					board.click(e);
				}

			});
		}

		@Override
		protected void paintComponent(final Graphics g) {
			super.paintComponent(g);

			board.render((Graphics2D) g);
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

}
