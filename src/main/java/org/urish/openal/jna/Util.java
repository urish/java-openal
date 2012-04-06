package org.urish.openal.jna;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.urish.openal.ALException;

import com.sun.jna.Pointer;

public class Util {
	public static String getString(Pointer pointer) {
		if (pointer == null) {
			return null;
		}
		return pointer.getString(0, false);
	}

	public static List<String> getStrings(Pointer pointerToStrings) {
		List<String> result = new LinkedList<String>();
		int offset = 0;
		String current = pointerToStrings.getString(offset, false);
		while (current.length() > 0) {
			result.add(current);
			offset += current.length() + 1;
			current = pointerToStrings.getString(offset, false);
		}
		return result;
	}

	public static void checkForALError(AL al) throws ALException {
		int errorCode = al.alGetError();
		if (errorCode != AL.AL_NO_ERROR) {
			throw createALException(al, errorCode);
		}

	}

	public static ALException createALException(AL al) {
		return createALException(al, al.alGetError());
	}

	private static ALException createALException(AL al, int errorCode) {
		return new ALException("AL Error " + String.format("0x%x", errorCode) + ": "
				+ getString(al.alGetString(errorCode)));
	}

	public static void checkForALCError(ALC alc, ALCdevice device) throws ALException {
		int errorCode = alc.alcGetError(device);
		if (errorCode != ALC.ALC_NO_ERROR) {
			throw createALCException(alc, device, errorCode);
		}
	}

	public static ALException createALCException(ALC alc, ALCdevice device) {
		return createALCException(alc, device, alc.alcGetError(device));
	}
	
	private static ALException createALCException(ALC alc, ALCdevice device, int errorCode) {
		return new ALException("ALC Error " + String.format("%x", errorCode) + ": "
				+ getString(alc.alcGetString(device, errorCode)));
	}

	public static byte[] readStreamContents(InputStream input) throws IOException {
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[4096];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			result.write(buffer, 0, n);
		}
		return result.toByteArray();
	}
}
