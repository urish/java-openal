Java OpenAL JNA Wrapper by Uri Shaked
=====================================

Copyright (c) 2012 Uri Shaked ([Website](http://www.urish.org))

Released under the open source GPL+CE (Classpath Exception) License.

You can use this wrapper with the free OpenAL Soft implementation found in
[The OpenAL Soft Homepage](http://kcat.strangesoft.net/openal.html)
The above page includes a source package, as well as pre-compiled win32 dll.

The library enables you to easily integrate true 3D surround sound into your applications. 
It supports stereo 3D sound, quadraphonic (4-speaker) configuration, 5.1 and 7.1 surround speaker configurations.

Example Code:
-------------
The following code shows how to load and play a simple wave file:

### Basic example - Load and play a wave file

	OpenAL openal = new OpenAL();
	Source source = openal.createSource(new File("sample.wav"));
	source.play();
		
### Advanced example - Set source parameters

	source.setGain(0.75f); 		// 75% volume
	source.setPitch(0.85f); 	// 85% of the original pitch
	source.setPosition(-1, 0, 0); // -1 means 1 unit to the left
	source.setLooping(true); 	// Loop the sound effect
	Thread.sleep(10000);     	// Wait for 10 seconds

### Finally, clean up

	source.close();
	openal.close();
	
You can create multiple sources and play them simultaneously. You can also change any of the source parameters
while the source is playing.

Using the library in your project
---------------------------------

To use this library with your project, the OpenAL DLL/shared library must be available on your system's path.
By default, the library uses the OpenAL Soft implementation, which can be downloaded from 
[here](http://kcat.strangesoft.net/openal.html).

### Maven users

Add the following dependency to your pom.xml:

	<dependency>
		<groupId>org.urish.openal</groupId>
		<artifactId>java-openal</artifactId>
		<version>1.0.0</version>
	</dependency>

### Download the jars directly (without Maven)

If you do not use maven with your project, you can download the latest release jars directly from the [Maven Central Repository](http://repo1.maven.org/maven2/org/urish/openal/java-openal/).
You will also need to add the JNA jars to your classpath. You can download JNA from [here](http://java.net/projects/jna/downloads).
 