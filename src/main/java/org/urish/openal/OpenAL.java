package org.urish.openal;

import org.urish.openal.jna.ALFactory;

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
		if (device != null) {
			device = new Device(factory, deviceName);
		}
		if (context != null) {
			context = new Context(device);
		}
	}

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

	public Device getDevice() {
		return device;
	}

	public Context getContext() {
		return context;
	}

	public Source createSource() throws ALException {
		return new Source(factory);
	}

	public Buffer createBuffer() throws ALException {
		return new Buffer(factory);
	}
}
