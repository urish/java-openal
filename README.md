Java OpenAL JNA Wrapper by Uri Shaked
=====================================

Copyright (c) 2012 Uri Shaked ([Website](http://www.urish.org))

Released under the open source GPL+CE (Classpath Exception) License.

You can use this wrapper with the free OpenAL Soft implementation found here:
* [OpenAL Soft Homepage](http://kcat.strangesoft.net/openal.html)
The above page includes a source package, as well as pre-compiled win32 dll.

Example Code:
-------------
The following code shows how to load and play a simple mono wave file:

### Step 1 - Load wave data into a byte array

	AudioInputStream ais = AudioSystem.getAudioInputStream(new File("sample.wav"))
	AudioFormat audioFormat = in.getFormat();
	byte[] waveBuffer = IOUtils.toByteArray(ais);
	ais.close()
	
### Step 2 - Initialize OpenAL, create a source a buffer

	OpenAL openal = new OpenAL();
	Source source = openal.createSource();
	Buffer buffer = openal.createBuffer();
	source.setBuffer(buffer)
	
### Step 3 - Play the sound, set gain, change pitch

	source.play()		 	// Play
	Thread.sleep(1000);
	source.setGain(0.5f); 	// 50% volume
	Thread.sleep(1000);
	source.setPitch(0.85f); // 85% of the original pitch
	
You can create as multiple sources and play them simultaneously.
 