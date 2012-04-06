package org.urish.openal;

import org.urish.openal.jna.AL;
import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

public class Buffer {
	private final AL al;
	private final int bufferId;

	public Buffer(ALFactory factory) throws ALException {
		this(factory.al);
	}
	
	public Buffer(AL al) throws ALException {
		this.al = al;
		int[] bufferIds = {0};
		al.alGenBuffers(1, bufferIds);
		Util.checkForALError(al);
		bufferId = bufferIds[0];
	}


	Buffer(AL al, int bufferId) {
		this.al = al;
		this.bufferId = bufferId;
	}

	public void close() {
		int[] bufferIds = {bufferId};
		al.alDeleteBuffers(1, bufferIds);
	}

	public void addBufferData(int format, byte[] data, int size, int sampleRate) throws ALException {
		al.alBufferData(bufferId, format, data, size, sampleRate);
		Util.checkForALError(al);
	}

	public int getBufferId() {
		return bufferId;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Buffer)) {
			return false;
		}
		return ((Buffer) other).getBufferId() == getBufferId();
	}

	@Override
	public int hashCode() {
		return getBufferId() * 11;
	}

	@Override
	public String toString() {
		return "ALBuffer[" + getBufferId() + "]";
	}
}
