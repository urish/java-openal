package org.urish.openal;

import org.urish.openal.jna.AL;
import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

public class Source {
	private final AL al;
	private final int sourceId;

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

	public void close() {
		IntByReference sourceIdHolder = new IntByReference(sourceId);
		al.alDeleteSources(1, sourceIdHolder);
	}

	public void pause() throws ALException {
		al.alSourcePause(sourceId);
		checkForError();
	}

	public void stop() throws ALException {
		al.alSourceStop(sourceId);
		checkForError();
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

	public float getGain() throws ALException {
		return getFloatParam(AL.AL_GAIN);
	}

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

	public void SetLooping(boolean looping) throws ALException {
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
