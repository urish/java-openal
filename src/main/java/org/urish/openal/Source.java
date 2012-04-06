package org.urish.openal;

import java.io.BufferedOutputStream;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;

import org.urish.openal.jna.AL;
import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

public class Source {
	private static final int STREAMING_BUFFER_SIZE = 2048;
	
	private final AL al;
	private final int sourceId;
	private boolean closed = false;

	public Source(ALFactory factory) throws ALException {
		al = factory.al;
		IntByReference sourceIdHolder = new IntByReference(0);
		al.alGenSources(1, sourceIdHolder);
		checkForError();
		sourceId = sourceIdHolder.getValue();
	}

	public int getSourceId() {
		return sourceId;
	}

	public void play() throws ALException {
		al.alSourcePlay(sourceId);
		checkForError();
	}

	public void pause() throws ALException {
		al.alSourcePause(sourceId);
		checkForError();
	}

	public void stop() throws ALException {
		al.alSourceStop(sourceId);
		checkForError();
	}

	public void rewind() throws ALException {
		al.alSourceRewind(sourceId);
		checkForError();
	}

	public void close() {
		if (!closed) {
			IntByReference sourceIdHolder = new IntByReference(sourceId);
			al.alDeleteSources(1, sourceIdHolder);
			closed = true;
		}
	}

	@Override
	protected void finalize() {
		close();
	}

	public void queueBuffer(Buffer buffer) throws ALException {
		queueBuffers(new Buffer[] { buffer });
	}

	public void queueBuffers(Buffer[] buffers) throws ALException {
		int[] bufferIds = new int[buffers.length];
		for (int i = 0; i < buffers.length; i++) {
			bufferIds[i] = buffers[i].getBufferId();
		}
		al.alSourceQueueBuffers(sourceId, buffers.length, bufferIds);
		checkForError();
	}

	public void unqueueBuffer(Buffer buffer) throws ALException {
		unqueueBuffers(new Buffer[] { buffer });
	}

	public void unqueueBuffers(Buffer[] buffers) throws ALException {
		int[] bufferIds = new int[buffers.length];
		for (int i = 0; i < buffers.length; i++) {
			bufferIds[i] = buffers[i].getBufferId();
		}
		al.alSourceUnqueueBuffers(sourceId, bufferIds.length, bufferIds);
		checkForError();
	}

	public int getQueuedBufferCount() throws ALException {
		return getIntParam(AL.AL_BUFFERS_QUEUED);
	}

	public int getProcessedBufferCount() throws ALException {
		return getIntParam(AL.AL_BUFFERS_PROCESSED);
	}

	public SourceState getSourceState() throws ALException {
		int sourceState = getIntParam(AL.AL_SOURCE_STATE);
		switch (sourceState) {
		case AL.AL_INITIAL:
			return SourceState.INITIAL;

		case AL.AL_PLAYING:
			return SourceState.PLAYING;

		case AL.AL_STOPPED:
			return SourceState.STOPPED;

		case AL.AL_PAUSED:
			return SourceState.PAUSED;

		default:
			throw new ALException("Unknown source state value encountered: " + sourceState);
		}
	}

	/**
	 * Source type (Static, Streaming or undetermined). Source is STATIC if a
	 * Buffer has been attached using AL_BUFFER. Source is STREAMING if one or
	 * more Buffers have been attached using alSourceQueueBuffers. Source is
	 * UNDETERMINED when it has the NULL buffer attached.
	 */
	public SourceType getSourceType() throws ALException {
		int sourceType = getIntParam(AL.AL_SOURCE_TYPE);
		switch (sourceType) {
		case AL.AL_STATIC:
			return SourceType.STATIC;
		case AL.AL_STREAMING:
			return SourceType.STREAMING;
		case AL.AL_UNDETERMINED:
			return SourceType.UNDETERMINED;
		default:
			throw new ALException("Unknown source type value encountered: " + sourceType);
		}
	}

	public float getFloatParam(int param) throws ALException {
		FloatByReference result = new FloatByReference(0f);
		al.alGetSourcef(sourceId, param, result);
		checkForError();
		return result.getValue();
	}

	public void setFloatParam(int param, float value) throws ALException {
		al.alSourcef(sourceId, param, value);
		checkForError();
	}

	public Tuple3F getFloat3Param(int param) throws ALException {
		FloatByReference v1 = new FloatByReference(0f);
		FloatByReference v2 = new FloatByReference(0f);
		FloatByReference v3 = new FloatByReference(0f);
		al.alGetSource3f(sourceId, param, v1, v2, v3);
		checkForError();
		return new Tuple3F(v1.getValue(), v2.getValue(), v3.getValue());
	}

	public void setFloat3Param(int param, Tuple3F value) throws ALException {
		al.alSource3f(sourceId, param, value.v1, value.v2, value.v3);
		checkForError();
	}

	public int getIntParam(int param) throws ALException {
		IntByReference result = new IntByReference(0);
		al.alGetSourcei(sourceId, param, result);
		checkForError();
		return result.getValue();
	}

	public void setIntParam(int param, int value) throws ALException {
		al.alSourcei(sourceId, param, value);
		checkForError();
	}

	public Buffer getBuffer() throws ALException {
		int bufferId = getIntParam(AL.AL_BUFFER);
		return new Buffer(al, bufferId);
	}

	public void setBuffer(Buffer buffer) throws ALException {
		setIntParam(AL.AL_BUFFER, buffer.getBufferId());
	}

	/**
	 * Returns the current gain value of the source.
	 */
	public float getGain() throws ALException {
		return getFloatParam(AL.AL_GAIN);
	}

	/**
	 * Indicate the gain (volume amplification) applied. Range: [0.0-1.0] A
	 * value of 1.0 means un-attenuated/unchanged. Each division by 2 equals an
	 * attenuation of -6dB. Each multiplicaton with 2 equals an amplification of
	 * +6dB. A value of 0.0 is meaningless with respect to a logarithmic scale;
	 * it is interpreted as zero volume - the channel is effectively disabled.
	 */
	public void setGain(float gain) throws ALException {
		setFloatParam(AL.AL_GAIN, gain);
	}

	/**
	 * Specify the pitch to be applied at source. Range: [0.5-2.0] Default: 1.0
	 */
	public float getPitch() throws ALException {
		return getFloatParam(AL.AL_PITCH);
	}

	/**
	 * Specify the pitch to be applied at source. Range: [0.5-2.0] Default: 1.0
	 */
	public void setPitch(float pitch) throws ALException {
		setFloatParam(AL.AL_PITCH, pitch);
	}

	public boolean isLooping() throws ALException {
		return getIntParam(AL.AL_LOOPING) == AL.AL_TRUE;
	}

	public void setLooping(boolean looping) throws ALException {
		setIntParam(AL.AL_LOOPING, looping ? AL.AL_TRUE : AL.AL_FALSE);
	}

	/**
	 * Returns the current location in three dimensional space. For more
	 * information, see {@link setPosition(org.urish.openal.Tuple3F)}.
	 */
	public Tuple3F getPosition() throws ALException {
		return getFloat3Param(AL.AL_POSITION);
	}

	/**
	 * Specify the current location in three dimensional space. OpenAL, like
	 * OpenGL, uses a right handed coordinate system, where in a frontal default
	 * view X (thumb) points right, Y points up (index finger), and Z points
	 * towards the viewer/camera (middle finger). To switch from a left handed
	 * coordinate system, flip the sign on the Z coordinate. Listener position
	 * is always in the world coordinate system.
	 */
	public void setPosition(Tuple3F position) throws ALException {
		setFloat3Param(AL.AL_POSITION, position);
	}
	
	/**
	 * Specify the current location in three dimensional space. OpenAL, like
	 * OpenGL, uses a right handed coordinate system, where in a frontal default
	 * view X (thumb) points right, Y points up (index finger), and Z points
	 * towards the viewer/camera (middle finger). To switch from a left handed
	 * coordinate system, flip the sign on the Z coordinate. Listener position
	 * is always in the world coordinate system.
	 */
	public void setPosition(float x, float y, float z) throws ALException {
		setFloat3Param(AL.AL_POSITION, new Tuple3F(x, y, z));
	}

	/**
	 * Returns the current velocity in three dimensional space.
	 */
	public Tuple3F getVelocity() throws ALException {
		return getFloat3Param(AL.AL_VELOCITY);
	}

	/**
	 * Specify the current velocity in three dimensional space.
	 */
	public void setVelocity(Tuple3F velocity) throws ALException {
		setFloat3Param(AL.AL_VELOCITY, velocity);
	}
	
	public OutputStream createOutputStream(AudioFormat format) throws ALException {
		return new BufferedOutputStream(new SourceOutputStream(al, this, format), STREAMING_BUFFER_SIZE);
	}

	private void checkForError() throws ALException {
		Util.checkForALError(al);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Source)) {
			return false;
		}
		return ((Source) other).getSourceId() == getSourceId();
	}

	@Override
	public int hashCode() {
		return getSourceId() * 11;
	}

	@Override
	public String toString() {
		return "ALSource[" + getSourceId() + "]";
	}
}
