package org.urish.openal.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.ByteByReference;
import com.sun.jna.ptr.DoubleByReference;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;

public interface AL extends Library {
	public final static AL instance = (AL) Native.loadLibrary("soft_oal", AL.class);

	/* "no distance model" or "no buffer" */
	public final static int AL_NONE = 0;

	/* Boolean False. */
	public final static int AL_FALSE = 0;

	/** Boolean True. */
	public final static int AL_TRUE = 1;

	/** Indicate Source has relative coordinates. */
	public final static int AL_SOURCE_RELATIVE = 0x202;

	/**
	 * Directional source, inner cone angle, in degrees. Range: [0-360] Default:
	 * 360
	 */
	public final static int AL_CONE_INNER_ANGLE = 0x1001;

	/**
	 * Directional source, outer cone angle, in degrees. Range: [0-360] Default:
	 * 360
	 */
	public final static int AL_CONE_OUTER_ANGLE = 0x1002;

	/**
	 * Specify the pitch to be applied at source. Range: [0.5-2.0] Default: 1.0
	 */
	public final static int AL_PITCH = 0x1003;

	/**
	 * Specify the current location in three dimensional space. OpenAL, like
	 * OpenGL, uses a right handed coordinate system, where in a frontal default
	 * view X (thumb) points right, Y points up (index finger), and Z points
	 * towards the viewer/camera (middle finger). To switch from a left handed
	 * coordinate system, flip the sign on the Z coordinate. Listener position
	 * is always in the world coordinate system.
	 */
	public final static int AL_POSITION = 0x1004;

	/** Specify the current direction. */
	public final static int AL_DIRECTION = 0x1005;

	/** Specify the current velocity in three dimensional space. */
	public final static int AL_VELOCITY = 0x1006;

	/**
	 * Indicate whether source is looping. Type: boolean? Range: [AL_TRUE,
	 * AL_FALSE] Default: FALSE.
	 */
	public final static int AL_LOOPING = 0x1007;

	/**
	 * Indicate the buffer to provide sound samples. Type: int. Range: any valid
	 * Buffer id.
	 */
	public final static int AL_BUFFER = 0x1009;

	/**
	 * Indicate the gain (volume amplification) applied. Type: float. Range:
	 * ]0.0- ] A value of 1.0 means un-attenuated/unchanged. Each division by 2
	 * equals an attenuation of -6dB. Each multiplicaton with 2 equals an
	 * amplification of +6dB. A value of 0.0 is meaningless with respect to a
	 * logarithmic scale; it is interpreted as zero volume - the channel is
	 * effectively disabled.
	 */
	public final static int AL_GAIN = 0x100A;

	/*
	 * Indicate minimum source attenuation Type: float Range: [0.0 - 1.0]
	 * 
	 * Logarthmic
	 */
	public final static int AL_MIN_GAIN = 0x100D;

	/**
	 * Indicate maximum source attenuation Type: float Range: [0.0 - 1.0]
	 * 
	 * Logarthmic
	 */
	public final static int AL_MAX_GAIN = 0x100E;

	/**
	 * Indicate listener orientation.
	 * 
	 * at/up
	 */
	public final static int AL_ORIENTATION = 0x100F;

	/**
	 * Source state information.
	 */
	public final static int AL_SOURCE_STATE = 0x1010;
	public final static int AL_INITIAL = 0x1011;
	public final static int AL_PLAYING = 0x1012;
	public final static int AL_PAUSED = 0x1013;
	public final static int AL_STOPPED = 0x1014;

	/**
	 * Buffer Queue params
	 */
	public final static int AL_BUFFERS_QUEUED = 0x1015;
	public final static int AL_BUFFERS_PROCESSED = 0x1016;

	/**
	 * Source buffer position information
	 */
	public final static int AL_SEC_OFFSET = 0x1024;
	public final static int AL_SAMPLE_OFFSET = 0x1025;
	public final static int AL_BYTE_OFFSET = 0x1026;

	/*
	 * Source type (Static, Streaming or undetermined) Source is Static if a
	 * Buffer has been attached using AL_BUFFER Source is Streaming if one or
	 * more Buffers have been attached using alSourceQueueBuffers Source is
	 * undetermined when it has the NULL buffer attached
	 */
	public final static int AL_SOURCE_TYPE = 0x1027;
	public final static int AL_STATIC = 0x1028;
	public final static int AL_STREAMING = 0x1029;
	public final static int AL_UNDETERMINED = 0x1030;

	/** Sound samples: format specifier. */
	public final static int AL_FORMAT_MONO8 = 0x1100;
	public final static int AL_FORMAT_MONO16 = 0x1101;
	public final static int AL_FORMAT_STEREO8 = 0x1102;
	public final static int AL_FORMAT_STEREO16 = 0x1103;

	/**
	 * source specific reference distance Type: float Range: 0.0 - +inf
	 * 
	 * At 0.0, no distance attenuation occurs. Default is 1.0.
	 */
	public final static int AL_REFERENCE_DISTANCE = 0x1020;

	/**
	 * source specific rolloff factor Type: float Range: 0.0 - +inf
	 * 
	 */
	public final static int AL_ROLLOFF_FACTOR = 0x1021;

	/**
	 * Directional source, outer cone gain.
	 * 
	 * Default: 0.0 Range: [0.0 - 1.0] Logarithmic
	 */
	public final static int AL_CONE_OUTER_GAIN = 0x1022;

	/**
	 * Indicate distance above which sources are not attenuated using the
	 * inverse clamped distance model.
	 * 
	 * Default: +inf Type: float Range: 0.0 - +inf
	 */
	public final static int AL_MAX_DISTANCE = 0x1023;

	/**
	 * Sound samples: frequency, in units of Hertz [Hz]. This is the number of
	 * samples per second. Half of the sample frequency marks the maximum
	 * significant frequency component.
	 */
	public final static int AL_FREQUENCY = 0x2001;
	public final static int AL_BITS = 0x2002;
	public final static int AL_CHANNELS = 0x2003;
	public final static int AL_SIZE = 0x2004;

	/**
	 * Buffer state.
	 * 
	 * Not supported for public use (yet).
	 */
	public final static int AL_UNUSED = 0x2010;
	public final static int AL_PENDING = 0x2011;
	public final static int AL_PROCESSED = 0x2012;

	/** Errors: No Error. */
	public final static int AL_NO_ERROR = 0;

	/**
	 * Invalid Name paramater passed to AL call.
	 */
	public final static int AL_INVALID_NAME = 0xA001;

	/**
	 * Invalid parameter passed to AL call.
	 */
	public final static int AL_INVALID_ENUM = 0xA002;

	/**
	 * Invalid enum parameter value.
	 */
	public final static int AL_INVALID_VALUE = 0xA003;

	/**
	 * Illegal call.
	 */
	public final static int AL_INVALID_OPERATION = 0xA004;

	/**
	 * No mojo.
	 */
	public final static int AL_OUT_OF_MEMORY = 0xA005;

	/** Context strings: Vendor Name. */
	public final static int AL_VENDOR = 0xB001;
	public final static int AL_VERSION = 0xB002;
	public final static int AL_RENDERER = 0xB003;
	public final static int AL_EXTENSIONS = 0xB004;

	/** Global tweakage. */

	/**
	 * Doppler scale. Default 1.0
	 */
	public final static int AL_DOPPLER_FACTOR = 0xC000;

	/**
	 * Tweaks speed of propagation.
	 */
	public final static int AL_DOPPLER_VELOCITY = 0xC001;

	/**
	 * Speed of Sound in units per second
	 */
	public final static int AL_SPEED_OF_SOUND = 0xC003;

	/**
	 * Distance models
	 * 
	 * used in conjunction with DistanceModel
	 * 
	 * implicit: NONE, which disances distance attenuation.
	 */
	public final static int AL_DISTANCE_MODEL = 0xD000;
	public final static int AL_INVERSE_DISTANCE = 0xD001;
	public final static int AL_INVERSE_DISTANCE_CLAMPED = 0xD002;
	public final static int AL_LINEAR_DISTANCE = 0xD003;
	public final static int AL_LINEAR_DISTANCE_CLAMPED = 0xD004;
	public final static int AL_EXPONENT_DISTANCE = 0xD005;
	public final static int AL_EXPONENT_DISTANCE_CLAMPED = 0xD006;

	/*
	 * Renderer State management
	 */
	void alEnable( /* ALenum */int capability);

	void alDisable( /* ALenum */int capability);

	boolean alIsEnabled( /* ALenum */int capability);

	/*
	 * State retrieval
	 */
	Pointer alGetString( /* ALenum */int param);

	void alGetBooleanv( /* ALenum */int param, ByteByReference data);

	void alGetIntegerv( /* ALenum */int param, IntByReference data);

	void alGetFloatv( /* ALenum */int param, FloatByReference data);

	void alGetDoublev( /* ALenum */int param, DoubleByReference data);

	boolean alGetBoolean( /* ALenum */int param);

	int alGetInteger( /* ALenum */int param);

	float alGetFloat( /* ALenum */int param);

	double alGetDouble( /* ALenum */int param);

	/*
	 * Error support. Obtain the most recent error generated in the AL state
	 * machine.
	 */
	/* ALenum */int alGetError();

	/*
	 * Extension support. Query for the presence of an extension, and obtain any
	 * appropriate function pointers and enum values.
	 */
	boolean alIsExtensionPresent(String extname);

	Pointer alGetProcAddress(String fname);

	/* ALenum */int alGetEnumValue(String ename);

	/*
	 * LISTENER Listener represents the location and orientation of the 'user'
	 * in 3D-space.
	 * 
	 * Properties include: -
	 * 
	 * Gain AL_GAIN float Position AL_POSITION float[3] Velocity AL_VELOCITY
	 * float[3] Orientation AL_ORIENTATION float[6] (Forward then Up vectors)
	 */

	/*
	 * Set Listener parameters
	 */
	void alListenerf( /* ALenum */int param, float value);

	void alListener3f( /* ALenum */int param, float value1, float value2, float value3);

	void alListenerfv( /* ALenum */int param, FloatByReference values);

	void alListeneri( /* ALenum */int param, int value);

	void alListener3i( /* ALenum */int param, int value1, int value2, int value3);

	void alListeneriv( /* ALenum */int param, IntByReference values);

	/*
	 * Get Listener parameters
	 */
	void alGetListenerf( /* ALenum */int param, FloatByReference value);

	void alGetListener3f( /* ALenum */int param, FloatByReference value1, FloatByReference value2, FloatByReference value3);

	void alGetListenerfv( /* ALenum */int param, FloatByReference values);

	void alGetListeneri( /* ALenum */int param, IntByReference value);

	void alGetListener3i( /* ALenum */int param, int value1, int value2, int value3);

	void alGetListeneriv( /* ALenum */int param, IntByReference values);

	/**
	 * SOURCE Sources represent individual sound objects in 3D-space. Sources
	 * take the PCM data provided in the specified Buffer, apply Source-specific
	 * modifications, and then submit them to be mixed according to spatial
	 * arrangement etc.
	 * 
	 * Properties include: -
	 * 
	 * Gain AL_GAIN float Min Gain AL_MIN_GAIN float Max Gain AL_MAX_GAIN float
	 * Position AL_POSITION float[3] Velocity AL_VELOCITY float[3] Direction
	 * AL_DIRECTION float[3] Head Relative Mode AL_SOURCE_RELATIVE int (AL_TRUE
	 * or AL_FALSE) Reference Distance AL_REFERENCE_DISTANCE float Max Distance
	 * AL_MAX_DISTANCE float RollOff Factor AL_ROLLOFF_FACTOR float Inner Angle
	 * AL_CONE_INNER_ANGLE int or float Outer Angle AL_CONE_OUTER_ANGLE int or
	 * float Cone Outer Gain AL_CONE_OUTER_GAIN int or float Pitch AL_PITCH
	 * float Looping AL_LOOPING int (AL_TRUE or AL_FALSE) MS Offset
	 * AL_MSEC_OFFSET int or float Byte Offset AL_BYTE_OFFSET int or float
	 * Sample Offset AL_SAMPLE_OFFSET int or float Attached Buffer AL_BUFFER int
	 * State (Query only) AL_SOURCE_STATE int Buffers Queued (Query only)
	 * AL_BUFFERS_QUEUED int Buffers Processed (Query only) AL_BUFFERS_PROCESSED
	 * int
	 */

	/* Create Source objects */
	void alGenSources( /* ALsizei */int n, IntByReference sources);

	/* Delete Source objects */
	void alDeleteSources( /* ALsizei */int n, IntByReference sources);

	/* Verify a handle is a valid Source */
	boolean alIsSource(int sid);

	/*
	 * Set Source parameters
	 */
	void alSourcef(int sid, /* ALenum */int param, float value);

	void alSource3f(int sid, /* ALenum */int param, float value1, float value2, float value3);

	void alSourcefv(int sid, /* ALenum */int param, FloatByReference values);

	void alSourcei(int sid, /* ALenum */int param, int value);

	void alSource3i(int sid, /* ALenum */int param, int value1, int value2, int value3);

	void alSourceiv(int sid, /* ALenum */int param, IntByReference values);

	/*
	 * Get Source parameters
	 */
	void alGetSourcef(int sid, /* ALenum */int param, FloatByReference value);

	void alGetSource3f(int sid, /* ALenum */int param, FloatByReference value1, FloatByReference value2,
			FloatByReference value3);

	void alGetSourcefv(int sid, /* ALenum */int param, FloatByReference values);

	void alGetSourcei(int sid, /* ALenum */int param, IntByReference value);

	void alGetSource3i(int sid, /* ALenum */int param, IntByReference value1, IntByReference value2,
			IntByReference value3);

	void alGetSourceiv(int sid, /* ALenum */int param, IntByReference values);

	/*
	 * Source vector based playback calls
	 */

	/* Play, replay, or resume (if paused) a list of Sources */
	void alSourcePlayv( /* ALsizei */int ns, IntByReference sids);

	/* Stop a list of Sources */
	void alSourceStopv( /* ALsizei */int ns, IntByReference sids);

	/* Rewind a list of Sources */
	void alSourceRewindv( /* ALsizei */int ns, IntByReference sids);

	/* Pause a list of Sources */
	void alSourcePausev( /* ALsizei */int ns, IntByReference sids);

	/*
	 * Source based playback calls
	 */

	/* Play, replay, or resume a Source */
	void alSourcePlay(int sid);

	/* Stop a Source */
	void alSourceStop(int sid);

	/* Rewind a Source (set playback postiton to beginning) */
	void alSourceRewind(int sid);

	/* Pause a Source */
	void alSourcePause(int sid);

	/*
	 * Source Queuing
	 */
	void alSourceQueueBuffers(int sid, /* ALsizei */int numEntries, int[] bids);

	void alSourceUnqueueBuffers(int sid, /* ALsizei */int numEntries, int[] bids);

	/**
	 * BUFFER Buffer objects are storage space for sample data. Buffers are
	 * referred to by Sources. One Buffer can be used by multiple Sources.
	 * 
	 * Properties include: -
	 * 
	 * Frequency (Query only) AL_FREQUENCY int Size (Query only) AL_SIZE int
	 * Bits (Query only) AL_BITS int Channels (Query only) AL_CHANNELS int
	 */

	/* Create Buffer objects */
	void alGenBuffers( /* ALsizei */int n, int[] buffers);

	/* Delete Buffer objects */
	void alDeleteBuffers( /* ALsizei */int n, int[] buffers);

	/* Verify a handle is a valid Buffer */
	boolean alIsBuffer(int bid);

	/* Specify the data to be copied into a buffer */
	void alBufferData(int bid, /* ALenum */int format, byte[] data, /* ALsizei */int size, /* ALsizei */int freq);

	/*
	 * Set Buffer parameters
	 */
	void alBufferf(int bid, /* ALenum */int param, float value);

	void alBuffer3f(int bid, /* ALenum */int param, float value1, float value2, float value3);

	void alBufferfv(int bid, /* ALenum */int param, FloatByReference values);

	void alBufferi(int bid, /* ALenum */int param, int value);

	void alBuffer3i(int bid, /* ALenum */int param, int value1, int value2, int value3);

	void alBufferiv(int bid, /* ALenum */int param, IntByReference values);

	/*
	 * Get Buffer parameters
	 */
	void alGetBufferf(int bid, /* ALenum */int param, FloatByReference value);

	void alGetBuffer3f(int bid, /* ALenum */int param, FloatByReference value1, FloatByReference value2,
			FloatByReference value3);

	void alGetBufferfv(int bid, /* ALenum */int param, FloatByReference values);

	void alGetBufferi(int bid, /* ALenum */int param, IntByReference value);

	void alGetBuffer3i(int bid, /* ALenum */int param, IntByReference value1, IntByReference value2,
			IntByReference value3);

	void alGetBufferiv(int bid, /* ALenum */int param, IntByReference values);

	/*
	 * Global Parameters
	 */
	void alDopplerFactor(float value);

	void alDopplerVelocity(float value);

	void alSpeedOfSound(float value);

	void alDistanceModel( /* ALenum */int distanceModel);

}
