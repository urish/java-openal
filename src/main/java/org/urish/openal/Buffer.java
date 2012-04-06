package org.urish.openal;

import org.urish.openal.jna.AL;
import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

import com.sun.jna.ptr.IntByReference;

public class Buffer {
	private final AL al;
	private final int bufferId;

	public Buffer(ALFactory factory) throws ALException {
		al = factory.al;
		IntByReference bufferIdHolder = new IntByReference(0);
		al.alGenBuffers(1, bufferIdHolder);
		Util.checkForALError(al);
		bufferId = bufferIdHolder.getValue();
	}

	Buffer(AL al, int bufferId) {
		this.al = al;
		this.bufferId = bufferId;
	}

	public void close() {
		IntByReference bufferIdHolder = new IntByReference(bufferId);
		al.alDeleteBuffers(1, bufferIdHolder);
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
