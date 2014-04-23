package edu.grinnell.cs.greenbruns;
import java.io.File;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.applet.*;
import java.net.*;

import javax.swing.*;
import javax.sound.sampled.*;

public class LoopSound {

	public static void play(String filepath) throws Exception {
		File file = new File(filepath);
       
        Clip clip = AudioSystem.getClip();
		AudioInputStream ais = AudioSystem
				.getAudioInputStream(file);
	
        System.out.println(clip.isActive());
        clip.open(ais);
        clip.setMicrosecondPosition(0);

        clip.start();
        //clip.close();
    }

}//LoopSound

//Citations

//http://docs.oracle.com/javase/tutorial/sound/playing.html
//Helped a lot.

//http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
//I don't remember, but I looked

//http://stackoverflow.com/questions/9470148/how-do-you-play-a-long-audioclip
//IDK

//http://stackoverflow.com/questions/17056169/playing-a-music-file-in-a-java-program?lq=1
//Looked

//http://stackoverflow.com/questions/26305/how-can-i-play-sound-in-java
//Looked

//http://docs.oracle.com/javase/tutorial/sound/sampled-overview.html
//Looked

//http://stackoverflow.com/questions/3785652/java-audio-how-to-play-song-during-game-application-not-applet/3785724#3785724
//Based code.  

//http://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/Clip.html
//Looked

//http://stackoverflow.com/tags/javasound/info
//Looked

//http://stackoverflow.com/questions/17138614/clip-not-playing-any-sound
//Looked

//http://docs.oracle.com/javase/6/docs/api/javax/sound/sampled/Clip.html
//I am pretty sure it is irrelevant, but it was open so I'm citing it. 

//http://stackoverflow.com/questions/3785652/java-audio-how-to-play-song-during-game-application-not-applet/3785724#3785724
//Looked
