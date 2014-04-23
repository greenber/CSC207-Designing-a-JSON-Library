package edu.grinnell.cs.greenbruns;

import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.applet.*;
import java.awt.Frame;
import java.net.*;

import javax.swing.*;
import javax.sound.sampled.*;

public class JSONObjectMakerUI
{

  public static Object objectMaker()
    throws Exception
  {
	//Dumbie LoopSound =)/how we would do it for all of them.(Take this comment out)
	//sLoopSound.play("/Users/alexandragreenberg/Desktop/Stuff/Windows OS - Windows Sounds (mp3cut.net).wav");
	Frame frame = new Frame();
	//JOptionPane.showMessageDialog(frame, "Welcome to the JSON Object-Maker!");
	
	Object[] options = {"Yes",
	                    "No",
	                    "Maybe"};
	
	int n = JOptionPane.showOptionDialog(frame,
	    "Are you ready to parse some JSON?",
	    "JSON PARSE MAKER!!!",
	    JOptionPane.YES_NO_CANCEL_OPTION,
	    JOptionPane.QUESTION_MESSAGE,
	    null,
	    options,
	    options[2]);
	
	if(n == 1)
	{
		JOptionPane.showMessageDialog(frame, "Frankly my dear I don't give a damn!");

	}//If(n)
	else
	{
	
	UIContext context = new UIContext();
    context.depthCount = 0;
    context.output = new StringBuffer("");
    boolean validInput = false;
    // Make a pen to give output
    PrintWriter pen = new PrintWriter(System.out, true);

    // Make a reader to get input
    java.io.BufferedReader eyes;
    java.io.InputStreamReader istream;
    istream = new java.io.InputStreamReader(System.in);
    eyes = new java.io.BufferedReader(istream);

    pen.println("Welcome to the JSON Object-Maker!");
    pen.println("This tool allows you to intuitively make a JAVA Object, and also generates equivalent JSON code");
    pen.println("To begin with, are you making a JSON Array or a JSON Object [at the highest level]?");
    while (validInput == false)
      {
        pen.println("Enter \'a\' for an array, and \'o\' for an object!");
        String input = eyes.readLine().toLowerCase();
        switch (input)
          {
            case "a":
            	LoopSound.play("/Users/alexandragreenberg/Desktop/Stuff/Windows OS - Windows Sounds (mp3cut.net).wav");
              addArray(context);
              pen.println(context.output);
              return JSONParser.parse(context.output.toString());

            case "o":
              addObject(context);
              pen.println(context.output);
              return JSONParser.parse(context.output.toString());

            default:
              System.err.println("Invalid input, please try again.");
          }
      }
	}//Outer if (n == 1)(take if you don't want)
    return null;
  }

  public static void addArray(UIContext context)
    throws IOException
  {
    boolean validInput = false;
    // Make a pen to give output
    PrintWriter pen = new PrintWriter(System.out, true);

    // Make a reader to get input
    java.io.BufferedReader eyes;
    java.io.InputStreamReader istream;
    istream = new java.io.InputStreamReader(System.in);
    eyes = new java.io.BufferedReader(istream);

    context.depthCount++;
    context.output.append("[");
    while (validInput == false)
      {
        pen.println("Current object:" + context.output.toString());
        pen.println("What would you like to do to this array?");
        pen.println("Press a to add element, press c to close array");

        String input = eyes.readLine().toLowerCase();
        if (input.equals("a"))
          {
            addElement(context);
          }

        else if (input.equals("c"))
          {
            if (context.output.charAt(context.output.length() - 1) == ',')
              {
                context.output.setCharAt(context.output.length() - 1, ']');
              }
            else
              {
                context.output.append("]");
              }
            if (context.depthCount > 1)
              {
                context.output.append(",");
              }

            context.depthCount--;
            return;
          }

        else
          {
            System.err.println("Invalid input, please try again.");
          }
      }
  }

  public static void addObject(UIContext context)
    throws IOException
  {
    // Make a pen to give output
    PrintWriter pen = new PrintWriter(System.out, true);

    // Make a reader to get input
    java.io.BufferedReader eyes;
    java.io.InputStreamReader istream;
    istream = new java.io.InputStreamReader(System.in);
    eyes = new java.io.BufferedReader(istream);

    context.depthCount++;
    context.output.append("{");

    while (true)
      {
        pen.println("Current object:" + context.output.toString());
        pen.println("What would you like to do to this object?");
        pen.println("Press a to add element, press c to close object");
        String input = eyes.readLine();

        switch (input)
          {
            case "a":
              pen.println("Please enter your key");
              input = eyes.readLine();
              context.output.append("\"" + input + "\":");
              addElement(context);
              break;
            case "c":
              if (context.output.charAt(context.output.length() - 1) == ',')
                {
                  context.output.setCharAt(context.output.length() - 1, '}');
                }
              else
                {
                  context.output.append("}");
                }
              if (context.depthCount > 1)
                {
                  context.output.append(",");
                }
              context.depthCount--;
              return;

            default:
              System.err.println("Invalid input, please try again");
          }
      }

  }

  public static void addElement(UIContext context)
    throws IOException
  {
    // Make a pen to give output
    PrintWriter pen = new PrintWriter(System.out, true);

    // Make a reader to get input
    java.io.BufferedReader eyes;
    java.io.InputStreamReader istream;
    istream = new java.io.InputStreamReader(System.in);
    eyes = new java.io.BufferedReader(istream);

    pen.println("What type would you like this element to be?");
    pen.println("Enter n for a number, s for a string, a for an array, b for a boolean/null, and o for an object");
    String input = eyes.readLine().toLowerCase();
    switch (input)
      {
        case "n":
          pen.println("Please enter your desired value");
          input = eyes.readLine();
          try
            {
              BigDecimal value = new BigDecimal(input);
            }
          catch (Exception e)
            {
              System.err.println("This is not a valid input, please try again");
              addElement(context);
              return;
            }
          context.output.append(input + ",");
          return;

        case "s":
          pen.println("Please enter your desired string");
          input = eyes.readLine();
          context.output.append("\"" + input + "\",");
          return;

        case "a":
          addArray(context);
          return;

        case "o":
          addObject(context);
          return;

        case "b":
          pen.println("Enter t to add true, f to add false, and n to add null");
          input = eyes.readLine().toLowerCase();
          switch (input)
            {
              case "t":
                context.output.append("true,");
                return;
              case "f":
                context.output.append("false,");
                return;
              case "n":
                context.output.append("null,");
                return;
            }
        default:
          System.err.println("Invalid input, please try again.");
          addElement(context);
          return;
      }

  }

  /**
   * @param args
   * @throws Exception
   */
  public static void main(String[] args)
    throws Exception
  {
    // TODO Auto-generated method stub
    objectMaker();
  }

}

class UIContext
{
  int depthCount;
  StringBuffer output;
}



// SOURCES:

// http://www.cs.grinnell.edu/~rebelsky/Courses/CSC207/2014S/readings/io.html
// I love this reading so much <3, I read it every time I do i/o in Java.

// http://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java (and time)!
// How to play sounds in Java 

//http://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-%28Java%29
//How to import Jars

//http://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-overview.htm#JFXST784
//Explanation 

//http://www.cs.cmu.edu/~illah/CLASSDOCS/javasound.pdf
//Played clip 

//http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#dialogdemo
//Boxes

//Helen Doughery
//message