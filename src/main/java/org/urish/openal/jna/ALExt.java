package org.urish.openal.jna;

import com.sun.jna.Library;

public interface ALExt extends Library {
	public static final int AL_FORMAT_IMA_ADPCM_MONO16_EXT = 0x10000;
	public static final int AL_FORMAT_IMA_ADPCM_STEREO16_EXT = 0x10001;
	public static final int AL_FORMAT_WAVE_EXT = 0x10002;
	public static final int AL_FORMAT_VORBIS_EXT = 0x10003;
	public static final int AL_FORMAT_QUAD8_LOKI = 0x10004;
	public static final int AL_FORMAT_QUAD16_LOKI = 0x10005;
	public static final int AL_FORMAT_MONO_FLOAT32 = 0x10010;
	public static final int AL_FORMAT_STEREO_FLOAT32 = 0x10011;
	public static final int AL_FORMAT_MONO_DOUBLE_EXT = 0x10012;
	public static final int AL_FORMAT_STEREO_DOUBLE_EXT = 0x10013;
	public static final int AL_FORMAT_MONO_MULAW_EXT = 0x10014;
	public static final int AL_FORMAT_STEREO_MULAW_EXT = 0x10015;
	public static final int AL_FORMAT_MONO_ALAW_EXT = 0x10016;
	public static final int AL_FORMAT_STEREO_ALAW_EXT = 0x10017;

	public static final int ALC_CHAN_MAIN_LOKI = 0x500001;
	public static final int ALC_CHAN_PCM_LOKI = 0x500002;
	public static final int ALC_CHAN_CD_LOKI = 0x500003;

	public static final int AL_FORMAT_QUAD8 = 0x1204;
	public static final int AL_FORMAT_QUAD16 = 0x1205;
	public static final int AL_FORMAT_QUAD32 = 0x1206;
	public static final int AL_FORMAT_REAR8 = 0x1207;
	public static final int AL_FORMAT_REAR16 = 0x1208;
	public static final int AL_FORMAT_REAR32 = 0x1209;
	public static final int AL_FORMAT_51CHN8 = 0x120A;
	public static final int AL_FORMAT_51CHN16 = 0x120B;
	public static final int AL_FORMAT_51CHN32 = 0x120C;
	public static final int AL_FORMAT_61CHN8 = 0x120D;
	public static final int AL_FORMAT_61CHN16 = 0x120E;
	public static final int AL_FORMAT_61CHN32 = 0x120F;
	public static final int AL_FORMAT_71CHN8 = 0x1210;
	public static final int AL_FORMAT_71CHN16 = 0x1211;
	public static final int AL_FORMAT_71CHN32 = 0x1212;

	public static final int AL_FORMAT_MONO_MULAW = 0x10014;
	public static final int AL_FORMAT_STEREO_MULAW = 0x10015;
	public static final int AL_FORMAT_QUAD_MULAW = 0x10021;
	public static final int AL_FORMAT_REAR_MULAW = 0x10022;
	public static final int AL_FORMAT_51CHN_MULAW = 0x10023;
	public static final int AL_FORMAT_61CHN_MULAW = 0x10024;
	public static final int AL_FORMAT_71CHN_MULAW = 0x10025;

	public static final int AL_FORMAT_MONO_IMA4 = 0x1300;
	public static final int AL_FORMAT_STEREO_IMA4 = 0x1301;

	// typedef ALvoid (*PFNALBUFFERDATASTATICPROC)(const
	// ALint,ALenum,ALvoid*,ALsizei,ALsizei);
	void alBufferDataStatic(int buffer, /* ALenum */int format, byte[] data, /* ALsizei */int len, /* ALsizei */int freq);

	public static final int ALC_CONNECTED = 0x313;

	// typedef ALCboolean (*PFNALCSETTHREADCONTEXTPROC)(ALCcontext *context);
	// typedef ALCcontext* (*PFNALCGETTHREADCONTEXTPROC)(void);
	boolean alcSetThreadContext(ALCcontext context);

	ALCcontext alcGetThreadContext();

	public static final int AL_SOURCE_DISTANCE_MODEL = 0x200;

	public static final int AL_BYTE_RW_OFFSETS_SOFT = 0x1031;
	public static final int AL_SAMPLE_RW_OFFSETS_SOFT = 0x1032;

	// typedef ALvoid (*PFNALBUFFERSUBDATASOFTPROC)(ALuint,ALenum,const
	// ALvoid*,ALsizei,ALsizei);
	void alBufferSubDataSOFT(int buffer,/* ALenum */int format, byte[] data, /* ALsizei */int offset,/* ALsizei */
			int length);

	public static final int AL_LOOP_POINTS_SOFT = 0x2015;

	public static final String AL_EXT_FOLDBACK_NAME = "AL_EXT_FOLDBACK";
	public static final int AL_FOLDBACK_EVENT_BLOCK = 0x4112;
	public static final int AL_FOLDBACK_EVENT_START = 0x4111;
	public static final int AL_FOLDBACK_EVENT_STOP = 0x4113;
	public static final int AL_FOLDBACK_MODE_MONO = 0x4101;
	public static final int AL_FOLDBACK_MODE_STEREO = 0x4102;
	// typedef void (*LPALFOLDBACKCALLBACK)(ALenum,ALsizei);
	// typedef void
	// (*LPALREQUESTFOLDBACKSTART)(ALenum,ALsizei,ALsizei,ALfloat*,LPALFOLDBACKCALLBACK);
	// typedef void (*LPALREQUESTFOLDBACKSTOP)(void);
	// void alRequestFoldbackStart(/*ALenum*/int mode,/*ALsizei*/int
	// count,/*ALsizei*/int length, float[] mem, LPALFOLDBACKCALLBACK callback);
	// void alRequestFoldbackStop();

	public static final int AL_DEDICATED_GAIN = 0x0001;
	public static final int AL_EFFECT_DEDICATED_DIALOGUE = 0x9001;
	public static final int AL_EFFECT_DEDICATED_LOW_FREQUENCY_EFFECT = 0x9000;

	/* Channel configurations */
	public static final int AL_MONO_SOFT = 0x1500;
	public static final int AL_STEREO_SOFT = 0x1501;
	public static final int AL_REAR_SOFT = 0x1502;
	public static final int AL_QUAD_SOFT = 0x1503;
	public static final int AL_5POINT1_SOFT = 0x1504;
	public static final int AL_6POINT1_SOFT = 0x1505;
	public static final int AL_7POINT1_SOFT = 0x1506;

	/* Sample types */
	public static final int AL_BYTE_SOFT = 0x1400;
	public static final int AL_UNSIGNED_BYTE_SOFT = 0x1401;
	public static final int AL_SHORT_SOFT = 0x1402;
	public static final int AL_UNSIGNED_SHORT_SOFT = 0x1403;
	public static final int AL_INT_SOFT = 0x1404;
	public static final int AL_UNSIGNED_INT_SOFT = 0x1405;
	public static final int AL_FLOAT_SOFT = 0x1406;
	public static final int AL_DOUBLE_SOFT = 0x1407;
	public static final int AL_BYTE3_SOFT = 0x1408;
	public static final int AL_UNSIGNED_BYTE3_SOFT = 0x1409;

	/* Storage formats */
	public static final int AL_MONO8_SOFT = 0x1100;
	public static final int AL_MONO16_SOFT = 0x1101;
	public static final int AL_MONO32F_SOFT = 0x10010;
	public static final int AL_STEREO8_SOFT = 0x1102;
	public static final int AL_STEREO16_SOFT = 0x1103;
	public static final int AL_STEREO32F_SOFT = 0x10011;
	public static final int AL_QUAD8_SOFT = 0x1204;
	public static final int AL_QUAD16_SOFT = 0x1205;
	public static final int AL_QUAD32F_SOFT = 0x1206;
	public static final int AL_REAR8_SOFT = 0x1207;
	public static final int AL_REAR16_SOFT = 0x1208;
	public static final int AL_REAR32F_SOFT = 0x1209;
	public static final int AL_5POINT1_8_SOFT = 0x120A;
	public static final int AL_5POINT1_16_SOFT = 0x120B;
	public static final int AL_5POINT1_32F_SOFT = 0x120C;
	public static final int AL_6POINT1_8_SOFT = 0x120D;
	public static final int AL_6POINT1_16_SOFT = 0x120E;
	public static final int AL_6POINT1_32F_SOFT = 0x120F;
	public static final int AL_7POINT1_8_SOFT = 0x1210;
	public static final int AL_7POINT1_16_SOFT = 0x1211;
	public static final int AL_7POINT1_32F_SOFT = 0x1212;

	/* Buffer attributes */
	public static final int AL_INTERNAL_FORMAT_SOFT = 0x2008;
	public static final int AL_BYTE_LENGTH_SOFT = 0x2009;
	public static final int AL_SAMPLE_LENGTH_SOFT = 0x200A;
	public static final int AL_SEC_LENGTH_SOFT = 0x200B;

	// typedef void
	// (*LPALBUFFERSAMPLESSOFT)(ALuint,ALuint,ALenum,ALsizei,ALenum,ALenum,const
	// ALvoid*);
	// typedef void
	// (*LPALBUFFERSUBSAMPLESSOFT)(ALuint,ALsizei,ALsizei,ALenum,ALenum,const
	// ALvoid*);
	// typedef void
	// (*LPALGETBUFFERSAMPLESSOFT)(ALuint,ALsizei,ALsizei,ALenum,ALenum,ALvoid*);
	// typedef ALboolean (*LPALISBUFFERFORMATSUPPORTEDSOFT)(ALenum);

	void alBufferSamplesSOFT(int buffer, int samplerate, /* ALenum */int internalformat, /* ALsizei */int samples, /* ALenum */
			int channels, /* ALenum */int type, byte[] data);

	void alBufferSubSamplesSOFT(int buffer, /* ALsizei */int offset, /* ALsizei */int samples, /* ALenum */int channels, /* ALenum */
			int type, byte[] data);

	void alGetBufferSamplesSOFT(int buffer, /* ALsizei */int offset, /* ALsizei */int samples, /* ALenum */int channels, /* ALenum */
			int type, byte[] data);

	public static final int AL_DIRECT_CHANNELS_SOFT = 0x1033;

	public static final int ALC_SOFT_loopback = 1;

	/* Sample types */
	public static final int ALC_BYTE_SOFT = 0x1400;
	public static final int ALC_UNSIGNED_BYTE_SOFT = 0x1401;
	public static final int ALC_SHORT_SOFT = 0x1402;
	public static final int ALC_UNSIGNED_SHORT_SOFT = 0x1403;
	public static final int ALC_INT_SOFT = 0x1404;
	public static final int ALC_UNSIGNED_INT_SOFT = 0x1405;
	public static final int ALC_FLOAT_SOFT = 0x1406;

	/* Channel configurations */
	public static final int ALC_MONO_SOFT = 0x1500;
	public static final int ALC_STEREO_SOFT = 0x1501;
	public static final int ALC_QUAD_SOFT = 0x1503;
	public static final int ALC_5POINT1_SOFT = 0x1504;
	public static final int ALC_6POINT1_SOFT = 0x1505;
	public static final int ALC_7POINT1_SOFT = 0x1506;

	// typedef ALCdevice* (*LPALCLOOPBACKOPENDEVICESOFT)(const ALCchar*);
	// typedef ALCboolean
	// (*LPALCISRENDERFORMATSUPPORTEDSOFT)(ALCdevice*,ALCsizei,ALCenum,ALCenum);
	// typedef void (*LPALCRENDERSAMPLESSOFT)(ALCdevice*,ALCvoid*,ALCsizei);

	ALCdevice alcLoopbackOpenDeviceSOFT(String deviceName);

	boolean alcIsRenderFormatSupportedSOFT(ALCdevice device, /* ALCsizei */int freq, /* ALCenum */int channels,/* ALCenum */
			int type);

	void alcRenderSamplesSOFT(ALCdevice device, byte[] buffer, /* ALCsizei */int samples);
}
