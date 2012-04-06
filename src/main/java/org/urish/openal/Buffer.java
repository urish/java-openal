package org.urish.openal;

import javax.sound.sampled.AudioFormat;

import org.urish.openal.jna.AL;
import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

public class Buffer {
	private final AL al;
	private final int bufferId;
	private boolean closed = false;

	public Buffer(ALFactory factory) throws ALException {
		this(factory.al);
	}

	public Buffer(AL al) throws ALException {
		this.al = al;
		int[] bufferIds = { 0 };
		al.alGenBuffers(1, bufferIds);
		Util.checkForALError(al);
		bufferId = bufferIds[0];
	}

	Buffer(AL al, int bufferId) {
		this.al = al;
		this.bufferId = bufferId;
	}

	public void close() {
		if (!closed) {
			int[] bufferIds = { bufferId };
			al.alDeleteBuffers(1, bufferIds);
			closed = true;
		}
	}
	
	@Override
	protected void finalize() {
		close();
	}

	public void addBufferData(AudioFormat format, byte[] data) throws ALException {
		addBufferData(format, data, data.length);
	}

	public void addBufferData(AudioFormat format, byte[] data, int size) throws ALException {
		int audioFormat = AL.AL_FALSE;
		if (format.getSampleSizeInBits() == 8) {
			if (format.getChannels() == 1) {
				audioFormat = AL.AL_FORMAT_MONO8;
			} else if (format.getChannels() == 2) {
				audioFormat = AL.AL_FORMAT_STEREO8;
			}
		} else if (format.getSampleSizeInBits() == 16) {
			if (format.getChannels() == 1) {
				audioFormat = AL.AL_FORMAT_MONO16;
			} else if (format.getChannels() == 2) {
				audioFormat = AL.AL_FORMAT_STEREO16;
			}
		}
		if (audioFormat == AL.AL_FALSE) {
			throw new ALException("Unsuppported audio format: " + format);
		}

		addBufferData(audioFormat, data, size, (int) format.getSampleRate());
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
