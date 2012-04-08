package org.urish.openal;

import java.util.List;

import org.urish.openal.jna.ALC;
import org.urish.openal.jna.ALCdevice;
import org.urish.openal.jna.ALFactory;
import org.urish.openal.jna.Util;

import com.sun.jna.Pointer;

public class Device {
	final ALC alc;
	final ALCdevice device;
	private boolean closed = false;

	public Device(ALFactory factory) throws ALException {
		this(factory, null);
	}

	public Device(ALFactory factory, String name) throws ALException {
		alc = factory.alc;
		device = alc.alcOpenDevice(name);
		if (device == null) {
			throw new ALException("Failed to open ALC device " + name);
		}
	}

	public void close() {
		if (!closed) {
			alc.alcCloseDevice(device);
			closed = true;
		}
	}

	@Override
	protected void finalize() {
		close();
	}

	public static String defaultDevice(ALFactory factory) {
		return Util.getString(factory.alc.alcGetString(null, ALC.ALC_DEFAULT_DEVICE_SPECIFIER));
	}

	public static List<String> availableDevices(ALFactory factory) throws ALException {
		Pointer stringsPtr;
		if (factory.alc.alcIsExtensionPresent(null, "ALC_ENUMERATE_ALL_EXT")) {
			stringsPtr = factory.alc.alcGetString(null, ALC.ALC_ALL_DEVICES_SPECIFIER);
		} else {
			stringsPtr = factory.alc.alcGetString(null, ALC.ALC_DEVICE_SPECIFIER);
		}
		if (stringsPtr == null) {
			throw Util.createALCException(factory.alc, null);
		}
		return Util.getStrings(stringsPtr);
	}

	public void checkForError() throws ALException {
		Util.checkForALCError(alc, device);
	}
	
	@Override
	public String toString() {
		return "Device[" + device + "]";
	}
}
