package de.felix.chess;

import java.awt.EventQueue;

import javax.swing.JFrame;

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

			final Board board = new Board();

			frame.setContentPane(board);

			frame.pack();

			frame.setVisible(true);

			final Thread t = new Thread(board);

			t.setName("GAME_LOOP");

			t.start();
		});
	}

}
