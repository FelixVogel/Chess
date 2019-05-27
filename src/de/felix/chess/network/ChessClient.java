package de.felix.chess.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChessClient {

	private final Socket socket;

	public ChessClient(final String host, final int port) throws UnknownHostException, IOException {
		this(new Socket(host, port));
	}

	public ChessClient(final Socket socket) {
		this.socket = socket;
	}

}
