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
class SourceOutputStream extends OutputStream {
	private static final int CYCLIC_BUFFER_COUNT = 2;

	private final Source source;
	private final AudioFormat format;
	private final Buffer[] buffers = new Buffer[CYCLIC_BUFFER_COUNT];

	private int queueHead = 0;
	private int queueTail = 0;

	public SourceOutputStream(AL al, Source source, AudioFormat format) throws ALException {
		super();
		this.source = source;
		this.format = format;
		for (int i = 0; i < buffers.length; i++) {
			buffers[i] = new Buffer(al);
		}
	}

	@Override
	public void write(int b) throws IOException {
		write(new byte[] { (byte) b }, 0, 1);
	}

	@Override
	public void write(byte[] b) throws IOException {
		write(b, 0, b.length);
	}

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

	@Override
	public void close() throws IOException {
		for (Buffer buffer : buffers) {
			buffer.close();
		}
	}
}
