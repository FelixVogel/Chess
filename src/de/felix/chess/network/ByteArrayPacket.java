package de.felix.chess.network;

public class ByteArrayPacket implements IPacket {

	private final byte[] data;

	public ByteArrayPacket(final byte[] data) {
		this.data = data;
	}

	@Override
	public byte[] getData() {
		return data;
	}

}
