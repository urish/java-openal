package org.urish.openal;

import javax.sound.sampled.AudioFormat;

import org.urish.openal.jna.AL;
import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

/**
 *
 * @author Romain PETIT <tokazio@esyo.net>
 */
public class Source {

    /**
     *
     */
    private int STREAMING_BUFFER_SIZE = 1024;

    /**
     *
     */
    private final AL al;

    /**
     *
     */
    private final int sourceId;

    /**
     *
     */
    private boolean closed = false;

    /**
     *
     * @param factory
     * @throws ALException
     */
    public Source(ALFactory factory) throws ALException {
	al = factory.al;
	IntByReference sourceIdHolder = new IntByReference(0);
	al.alGenSources(1, sourceIdHolder);
	checkForError();
	sourceId = sourceIdHolder.getValue();
    }

    /**
     * Set streaming buffer size in bytes (1024 by default)
     * Used in createOutputStream
     * @param aBufferSize Buffer size in bytes.
     */
    private void setStreamingBufferSize(int aBufferSize){
	STREAMING_BUFFER_SIZE = aBufferSize;
    }
    
    /**
     * 
     * @return Buffer size in bytes (1024 by default).
     */
    public int getStreamingBufferSize(){
	return STREAMING_BUFFER_SIZE;
    }
    
    /**
     *
     * @return
     */
    public int getSourceId() {
	return sourceId;
    }

    /**
     *
     * @throws ALException
     */
    public void play() throws ALException {
	al.alSourcePlay(sourceId);
	checkForError();
    }

    /**
     *
     * @throws ALException
     */
    public void pause() throws ALException {
	al.alSourcePause(sourceId);
	checkForError();
    }

    /**
     *
     * @throws ALException
     */
    public void stop() throws ALException {
	al.alSourceStop(sourceId);
	checkForError();
    }

    /**
     *
     * @throws ALException
     */
    public void rewind() throws ALException {
	al.alSourceRewind(sourceId);
	checkForError();
    }

    /**
     *
     */
    public void close() {
	if (!closed) {
	    IntByReference sourceIdHolder = new IntByReference(sourceId);
	    al.alDeleteSources(1, sourceIdHolder);
	    closed = true;
	}
    }

    /**
     *
     * @throws java.lang.Throwable
     */
    @Override
    protected void finalize() throws Throwable {
	try {
	    close();
	} finally {
	    super.finalize();
	}
    }

    /**
     *
     * @param buffer
     * @throws ALException
     */
    public void queueBuffer(Buffer buffer) throws ALException {
	queueBuffers(new Buffer[]{buffer});
    }

    /**
     *
     * @param buffers
     * @throws ALException
     */
    public void queueBuffers(Buffer[] buffers) throws ALException {
	int[] bufferIds = new int[buffers.length];
	for (int i = 0; i < buffers.length; i++) {
	    bufferIds[i] = buffers[i].getBufferId();
	}
	al.alSourceQueueBuffers(sourceId, buffers.length, bufferIds);
	checkForError();
    }

    /**
     *
     * @param buffer
     * @throws ALException
     */
    public void unqueueBuffer(Buffer buffer) throws ALException {
	unqueueBuffers(new Buffer[]{buffer});
    }

    /**
     *
     * @param buffers
     * @throws ALException
     */
    public void unqueueBuffers(Buffer[] buffers) throws ALException {
	int[] bufferIds = new int[buffers.length];
	for (int i = 0; i < buffers.length; i++) {
	    bufferIds[i] = buffers[i].getBufferId();
	}
	al.alSourceUnqueueBuffers(sourceId, bufferIds.length, bufferIds);
	checkForError();
    }

    /**
     *
     * @return @throws ALException
     */
    public int getQueuedBufferCount() throws ALException {
	return getIntParam(AL.AL_BUFFERS_QUEUED);
    }

    /**
     *
     * @return @throws ALException
     */
    public int getProcessedBufferCount() throws ALException {
	return getIntParam(AL.AL_BUFFERS_PROCESSED);
    }

    /**
     *
     * @return @throws ALException
     */
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
     *
     * @return
     * @throws org.urish.openal.ALException
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

    /**
     *
     * @param param
     * @return
     * @throws ALException
     */
    public float getFloatParam(int param) throws ALException {
	FloatByReference result = new FloatByReference(0f);
	al.alGetSourcef(sourceId, param, result);
	checkForError();
	return result.getValue();
    }

    /**
     *
     * @param param
     * @param value
     * @throws ALException
     */
    public void setFloatParam(int param, float value) throws ALException {
	al.alSourcef(sourceId, param, value);
	checkForError();
    }

    /**
     *
     * @param param
     * @return
     * @throws ALException
     */
    public Tuple3F getFloat3Param(int param) throws ALException {
	FloatByReference v1 = new FloatByReference(0f);
	FloatByReference v2 = new FloatByReference(0f);
	FloatByReference v3 = new FloatByReference(0f);
	al.alGetSource3f(sourceId, param, v1, v2, v3);
	checkForError();
	return new Tuple3F(v1.getValue(), v2.getValue(), v3.getValue());
    }

    /**
     *
     * @param param
     * @param value
     * @throws ALException
     */
    public void setFloat3Param(int param, Tuple3F value) throws ALException {
	al.alSource3f(sourceId, param, value.v1, value.v2, value.v3);
	checkForError();
    }

    /**
     *
     * @param param
     * @return
     * @throws ALException
     */
    public int getIntParam(int param) throws ALException {
	IntByReference result = new IntByReference(0);
	al.alGetSourcei(sourceId, param, result);
	checkForError();
	return result.getValue();
    }

    /**
     *
     * @param param
     * @param value
     * @throws ALException
     */
    public void setIntParam(int param, int value) throws ALException {
	al.alSourcei(sourceId, param, value);
	checkForError();
    }

    /**
     *
     * @return @throws ALException
     */
    public Buffer getBuffer() throws ALException {
	int bufferId = getIntParam(AL.AL_BUFFER);
	return new Buffer(al, bufferId);
    }

    /**
     *
     * @param buffer
     * @throws ALException
     */
    public void setBuffer(Buffer buffer) throws ALException {
	setIntParam(AL.AL_BUFFER, buffer.getBufferId());
    }

    /**
     * Returns the current gain value of the source.
     *
     * @return
     * @throws org.urish.openal.ALException
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
     *
     * @param gain
     * @throws org.urish.openal.ALException
     */
    public void setGain(float gain) throws ALException {
	setFloatParam(AL.AL_GAIN, gain);
    }

    /**
     * Specify the pitch to be applied at source. Range: [0.5-2.0] Default: 1.0
     *
     * @return
     * @throws org.urish.openal.ALException
     */
    public float getPitch() throws ALException {
	return getFloatParam(AL.AL_PITCH);
    }

    /**
     * Specify the pitch to be applied at source. Range: [0.5-2.0] Default: 1.0
     *
     * @param pitch
     * @throws org.urish.openal.ALException
     */
    public void setPitch(float pitch) throws ALException {
	setFloatParam(AL.AL_PITCH, pitch);
    }

    /**
     *
     * @return @throws ALException
     */
    public boolean isLooping() throws ALException {
	return getIntParam(AL.AL_LOOPING) == AL.AL_TRUE;
    }

    /**
     *
     * @param looping
     * @throws ALException
     */
    public void setLooping(boolean looping) throws ALException {
	setIntParam(AL.AL_LOOPING, looping ? AL.AL_TRUE : AL.AL_FALSE);
    }

    /**
     * Returns the current location in three dimensional space. For more
     * information, see {@link setPosition(org.urish.openal.Tuple3F)}.
     *
     * @return
     * @throws org.urish.openal.ALException
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
     *
     * @param position
     * @throws org.urish.openal.ALException
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
     *
     * @param x
     * @param y
     * @param z
     * @throws org.urish.openal.ALException
     */
    public void setPosition(float x, float y, float z) throws ALException {
	setFloat3Param(AL.AL_POSITION, new Tuple3F(x, y, z));
    }

    /**
     * Returns the current velocity in three dimensional space.
     *
     * @return
     * @throws org.urish.openal.ALException
     */
    public Tuple3F getVelocity() throws ALException {
	return getFloat3Param(AL.AL_VELOCITY);
    }

    /**
     * Specify the current velocity in three dimensional space.
     *
     * @param velocity
     * @throws org.urish.openal.ALException
     */
    public void setVelocity(Tuple3F velocity) throws ALException {
	setFloat3Param(AL.AL_VELOCITY, velocity);
    }

    /**
     *
     * @param format
     * @return
     * @throws ALException
     * @deprecated use createOutputStream with number/size of buffers
     */
    @Deprecated
    public SourceBufferedOutputStream createOutputStream(AudioFormat format) throws ALException {
	return new SourceBufferedOutputStream(new SourceOutputStream(al, this, format), STREAMING_BUFFER_SIZE);
    }
    
    /**
     *
     * @param format
     * @param numberOfBuffer
     * @param aBufferSize
     * @return
     * @throws ALException
     */
    public SourceBufferedOutputStream createOutputStream(AudioFormat format, int numberOfBuffer, int aBufferSize) throws ALException {
	setStreamingBufferSize(aBufferSize);
	return new SourceBufferedOutputStream(new SourceOutputStream(al, this, format, numberOfBuffer), STREAMING_BUFFER_SIZE);
    }

    /**
     *
     * @throws ALException
     */
    private void checkForError() throws ALException {
	Util.checkForALError(al);
    }

    /**
     *
     * @param other
     * @return
     */
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

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
	return getSourceId() * 11;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
	return "ALSource[" + getSourceId() + "]";
    }
}
