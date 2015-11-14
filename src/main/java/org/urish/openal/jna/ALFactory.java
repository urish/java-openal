package org.urish.openal.jna;

import java.io.File;
import java.io.FileNotFoundException;

import com.sun.jna.Native;

public class ALFactory {
	private static final String DEFAULT_DLL_NAME = "openal";

	public final AL al;
	public final ALC alc;
	public final ALExt alext;

	public ALFactory() {
		al = (AL) Native.loadLibrary(DEFAULT_DLL_NAME, AL.class);
		alc = (ALC) Native.loadLibrary(DEFAULT_DLL_NAME, ALC.class);
		alext = (ALExt) Native.loadLibrary(DEFAULT_DLL_NAME, ALExt.class);
	}

	public ALFactory(File dllPath) throws FileNotFoundException {
		String dllName = DEFAULT_DLL_NAME;
		if (dllPath != null) {
			if (!dllPath.exists()) {
				throw new FileNotFoundException(dllPath.getAbsolutePath());
			}
			System.setProperty("jna.library.path", dllPath.getParent());
			dllName = dllPath.getName();
		}

		al = (AL) Native.loadLibrary(dllName, AL.class);
		alc = (ALC) Native.loadLibrary(dllName, ALC.class);
		alext = (ALExt) Native.loadLibrary(dllName, ALExt.class);
	}
}
