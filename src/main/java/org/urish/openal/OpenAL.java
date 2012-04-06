package org.urish.openal;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

/**
 * This class contains some boilerplate code for initializing/tearing down
 * OpenAL, as well as factory methods to obtaining OpenAL objects.
 */
public class OpenAL {
	private final ALFactory factory;
	private Device device;
	private Context context;

	public OpenAL() throws ALException {
		this(new ALFactory(), null);
	}

	public OpenAL(ALFactory factory, String deviceName) throws ALException {
		this.factory = factory;
		init(deviceName);
	}

	/**
	 * Initializes OpenAL. You usually don't need to call this method directly,
	 * as it's called automatically by the constructor.
	 * 
	 * @param deviceName
	 * @throws ALException
	 */
	public void init(String deviceName) throws ALException {
		if (device == null) {
			device = new Device(factory, deviceName);
		}
		if (context == null) {
			context = new Context(device);
		}
	}

	/**
	 * Cleans up the Context and Device objects.
	 */
	public void close() {
		if (context != null) {
			context.close();
			context = null;
		}
		if (device != null) {
			device.close();
			device = null;
		}
	}

	/**
	 * Returns the OpenAL device object
	 */
	public Device getDevice() {
		return device;
	}

	/**
	 * Returns the OpenAL context object
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * Creates a new OpenAL source and returns it.
	 */
	public Source createSource() throws ALException {
		return new Source(factory);
	}

	/**
	 * Creates a new OpenAL buffer and returns it.
	 */
	public Buffer createBuffer() throws ALException {
		return new Buffer(factory);
	}
	
	/**
	 * Utility method to create a source with a given wave file attached as a buffer.
	 * @param waveFile The file to load into the source's buffer
	 * @return A new OpenAL source
	 */
	public Source createSource(File waveFile) throws ALException, IOException, UnsupportedAudioFileException {
		return createSource(AudioSystem.getAudioInputStream(waveFile));
	}

	/**
	 * Utility method to create a source and preload audio data into its buffer
	 * @param url a URL of a wave file containing the audio data to load
	 * @return A new OpenAL source
	 */
	public Source createSource(URL url) throws ALException, IOException, UnsupportedAudioFileException {
		return createSource(AudioSystem.getAudioInputStream(url));
	}

	/**
	 * Utility method to create a source and preload audio data into its buffer
	 * @param audioStream The audio input stream to load into the new source's buffer
	 * @return A new OpenAL source
	 */
	public Source createSource(AudioInputStream audioInputStream) throws ALException, IOException, UnsupportedAudioFileException {
		Source source = createSource();
		Buffer buffer = createBuffer(audioInputStream);
		source.setBuffer(buffer);
		return source;
	}

	/**
	 * Creates a buffer and loads the given wave file input that buffer.
	 * @param waveFile The file to load into the new buffer
	 * @return A new buffer preloaded with the given audio file contents
	 */
	public Buffer createBuffer(File waveFile) throws IOException, UnsupportedAudioFileException, ALException {
		return createBuffer(AudioSystem.getAudioInputStream(waveFile));
	}

	/**
	 * Creates a buffer and fills it with data from the given audio input stream
	 * @param audioStream The audio input stream to load into the new buffer
	 * @return A new OpenAL buffer preloaded with the given audio file contents
	 */
	public Buffer createBuffer(AudioInputStream audioStream) throws ALException, IOException {
		Buffer result = createBuffer();
		result.addBufferData(audioStream.getFormat(), Util.readStreamContents(audioStream));
		return result;
	}
}
