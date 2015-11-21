package org.urish.openal;

import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;

import org.urish.openal.jna.AL;

/**
 * An output stream for easily streaming audio data to OpenAL source. You can
 * create a SourceOutputStream object by calling the createOutputStream() method
 * of the relevant Source object. The underlaying implementation uses a cyclic
 * buffer array for streaming the incoming audio data into the given OpenAL
 * source.
 *
 * @author Uri Shaked
 */
public class SourceOutputStream extends OutputStream {

    /**
     * 
     */
    private int CYCLIC_BUFFER_COUNT = 3;

    /**
     * 
     */
    private final Source source;
    
    /**
     * 
     */
    private final AudioFormat format;
    
    /**
     * 
     */
    private Buffer[] buffers;

    /**
     * 
     */
    private int queueHead = 0;
    
    /**
     * 
     */
    private int queueTail = 0;

    /**
     * 
     */
    private long samplesProcessed = 0;
    
    /**
     * 
     */
    private final AL al;

    /**
     * 
     * for compatibility
     * @param al
     * @param source
     * @param format
     * @throws ALException 
     * @deprecated Use SourceOutputStream with the numberOfBuffer param
     */
    @Deprecated
    public SourceOutputStream(AL al, Source source, AudioFormat format) throws ALException {
	super();
	this.al = al;
	this.source = source;
	this.format = format;
	initBuffers();
    }
    
    /**
     * 
     * @param al
     * @param source
     * @param format
     * @param numberOfBuffer
     * @throws ALException 
     */
    public SourceOutputStream(AL al, Source source, AudioFormat format, int numberOfBuffer) throws ALException {	
	super();
	this.al = al;
	this.source = source;
	this.format = format;
	setNumberOfBuffer(numberOfBuffer);
	initBuffers();
    }

    /**
     * Set number and buffer and initialize them
     * @param numberOfBuffer 
     */
    private void setNumberOfBuffer(int numberOfBuffer) throws ALException{
	CYCLIC_BUFFER_COUNT = numberOfBuffer;
	initBuffers();
    }
    
    /**
     * 
     * @return The number of buffer
     */
    public int getNumberOfBuffer(){
	return CYCLIC_BUFFER_COUNT;
    }
    
    /**
     * Initialize buffers (empty buffers)
     */
    private void initBuffers() throws ALException{
	buffers = new Buffer[CYCLIC_BUFFER_COUNT];
	for (int i = 0; i < buffers.length; i++) {
	    buffers[i] = new Buffer(al);
	}
    }
    
    /**
     * samplesProcessed/sampleRate/channels -> ms
     * @return 
     */
    public long getSamplesProcessed(){
	return this.samplesProcessed;
    }

    /**
     * 
     * @param b
     * @throws IOException 
     */
    @Override
    public void write(int b) throws IOException {
	write(new byte[]{(byte) b}, 0, 1);
    }

    /**
     * 
     * @param b
     * @throws IOException 
     */
    @Override
    public void write(byte[] b) throws IOException {
	write(b, 0, b.length);
    }

    /**
     * 
     * @param b
     * @param off
     * @param len
     * @throws IOException 
     */
    @Override
    public void write(byte[] b, int off, int len) throws IOException {
	try {
	    int queueSize = source.getQueuedBufferCount();
	    if (queueSize >= buffers.length) {
		while (source.getProcessedBufferCount() == 0) {
		    try {
			Thread.sleep(20);
		    } catch (InterruptedException e) {
			throw new IOException("IO Operation interrupted", e);
		    }
		}
		source.unqueueBuffer(buffers[queueTail]);

		samplesProcessed += buffers[queueTail].getIntParam(AL.AL_SIZE) / (buffers[queueTail].getIntParam(AL.AL_BITS) / 8);

		queueTail = (queueTail + 1) % buffers.length;
	    }
	    if (off != 0) {
		throw new IOException("Offsets other than 0 are not currently supported");
	    }
	    buffers[queueHead].addBufferData(format, b, len);
	    source.queueBuffer(buffers[queueHead]);
	    queueHead = (queueHead + 1) % buffers.length;
	    if (source.getSourceState() == SourceState.INITIAL) {
		source.play();
	    }
	} catch (ALException e) {
	    throw new IOException(e);
	}
    }

    /**
     * 
     * @throws IOException 
     */
    @Override
    public void close() throws IOException {
	for (Buffer buffer : buffers) {
	    buffer.close();
	}
    }
}
