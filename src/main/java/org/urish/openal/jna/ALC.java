package org.urish.openal.jna;

import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public interface ALC extends Library {
	/**
	 * followed by <int> Hz
	 */
	public final static int ALC_FREQUENCY = 0x1007;

	/**
	 * followed by <int> Hz
	 */
	public final static int ALC_REFRESH = 0x1008;

	/**
	 * followed by AL_TRUE, AL_FALSE
	 */
	public final static int ALC_SYNC = 0x1009;

	/**
	 * followed by <int> Num of requested Mono (3D) Sources
	 */
	public final static int ALC_MONO_SOURCES = 0x1010;

	/**
	 * followed by <int> Num of requested Stereo Sources
	 */
	public final static int ALC_STEREO_SOURCES = 0x1011;

	/**
	 * errors
	 */

	/**
	 * No error
	 */
	public final static int ALC_NO_ERROR = 0;

	/**
	 * No device
	 */
	public final static int ALC_INVALID_DEVICE = 0xA001;

	/**
	 * invalid context ID
	 */
	public final static int ALC_INVALID_CONTEXT = 0xA002;

	/**
	 * bad enum
	 */
	public final static int ALC_INVALID_ENUM = 0xA003;

	/**
	 * bad value
	 */
	public final static int ALC_INVALID_VALUE = 0xA004;

	/**
	 * Out of memory.
	 */
	public final static int ALC_OUT_OF_MEMORY = 0xA005;

	/**
	 * The Specifier string for default device
	 */
	public final static int ALC_DEFAULT_DEVICE_SPECIFIER = 0x1004;
	public final static int ALC_DEVICE_SPECIFIER = 0x1005;
	public final static int ALC_EXTENSIONS = 0x1006;

	public final static int ALC_MAJOR_VERSION = 0x1000;
	public final static int ALC_MINOR_VERSION = 0x1001;

	public final static int ALC_ATTRIBUTES_SIZE = 0x1002;
	public final static int ALC_ALL_ATTRIBUTES = 0x1003;

	/**
	 * Capture extension
	 */
	public final static int ALC_EXT_CAPTURE = 1;
	public final static int ALC_CAPTURE_DEVICE_SPECIFIER = 0x310;
	public final static int ALC_CAPTURE_DEFAULT_DEVICE_SPECIFIER = 0x311;
	public final static int ALC_CAPTURE_SAMPLES = 0x312;

	/**
	 * ALC_ENUMERATE_ALL_EXT enums
	 */
	public final static int ALC_ENUMERATE_ALL_EXT = 1;
	public final static int ALC_DEFAULT_ALL_DEVICES_SPECIFIER = 0x1012;
	public final static int ALC_ALL_DEVICES_SPECIFIER = 0x1013;

		/*
	 * Context Management
	 */
	public ALCcontext alcCreateContext(ALCdevice device, IntByReference attrlist);

	public boolean alcMakeContextCurrent(ALCcontext context);

	public void alcProcessContext(ALCcontext context);

	public void alcSuspendContext(ALCcontext context);

	public void alcDestroyContext(ALCcontext context);

	public ALCcontext alcGetCurrentContext();

	public ALCdevice alcGetContextsDevice(ALCcontext context);

	/*
	 * Device Management
	 */
	public ALCdevice alcOpenDevice(String devicename);

	public boolean alcCloseDevice(ALCdevice device);

	/*
	 * Error support. Obtain the most recent Context error
	 */
	public/* ALCenum */int alcGetError(ALCdevice device);

	/*
	 * Extension support. Query for the presence of an extension, and obtain any
	 * appropriate function pointers and enum values.
	 */
	public boolean alcIsExtensionPresent(ALCdevice device, String extname);

	public/* void * */int alcGetProcAddress(ALCdevice device, String funcname);

	public/* ALCenum */int alcGetEnumValue(ALCdevice device, String enumname);

	/*
	 * Query functions
	 */
	public Pointer alcGetString(ALCdevice device, /* ALCenum */int param);

	public void alcGetIntegerv(ALCdevice device, /* ALCenum */int param, /* ALCsizei */int size, IntByReference data);

	/*
	 * Capture functions
	 */
	public ALCdevice alcCaptureOpenDevice(String devicename, int frequency, /* ALCenum */int format, /* ALCsizei */
			int buffersize);

	public boolean alcCaptureCloseDevice(ALCdevice device);

	public void alcCaptureStart(ALCdevice device);

	public void alcCaptureStop(ALCdevice device);

	public void alcCaptureSamples(ALCdevice device, /* ALCvoid* */int buffer, /* ALCsizei */int samples);
}
