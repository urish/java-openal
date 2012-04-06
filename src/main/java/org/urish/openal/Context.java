package org.urish.openal;

import org.urish.openal.jna.ALC;
import org.urish.openal.jna.ALCcontext;

public class Context {
	final ALC alc;
	private final ALCcontext context;

	public Context(Device device) throws ALException {
		alc = device.alc;
		context = alc.alcCreateContext(device.device, null);
		if (context == null) {
			throw new ALException("Could not create context");
		}
		if (!alc.alcMakeContextCurrent(context)) {
			alc.alcDestroyContext(context);
			throw new ALException("Could not make context current");
		}
		device.checkForError();
	}
	
	public void close() {
		alc.alcDestroyContext(context);
	}
}
