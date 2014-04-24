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
	//Welcomes you into the parser
		Frame frame = new Frame();
		
		messageBox("Welcome to the JSON Maker!", "Welcome!");

		//Plays the opening
		//LoopSound.play("/Users/alexandragreenberg/Desktop/Stuff/Windows OS - Windows Sounds (mp3cut.net).wav");
		playSound(5);
		
		Object[] options = {"Yes!",
				"No",
				"Maybe?"};

	int n = optionBox(options, options.length, "Choose one", "Are you ready to make some JSON!?");
		
	if (n == 1)
	{
		messageBox("Frankly my dear I don't give a SWAG!", "Nooo!");

	}//if(n==1)
	else 
	{
	UIContext context = new UIContext();
    context.depthCount = 0;
    context.output = new StringBuffer("");
    boolean validInput = false;
    // Make a pen to give output
    PrintWriter pen = new PrintWriter(System.out, true);

    messageBox("This tool allows you to intuitively make a JAVA Object\n"
    		+ "and also generates equivalent JSON code \n","Welcome to the JSON Object-Maker!");
    
    while (validInput == false)
      {
    	 String[] ops = {"Array","Object"};
        context.optionsHolder = optionBox(ops, ops.length, "Hightest Level",
        		"Click Object for an object, and Array for an array!");
        playSound(0);
       
        switch (context.optionsHolder)
          {
        	//Arrays 
            case 0:
					LoopSound.play("/Users/alexandragreenberg/Desktop/Stuff/Windows OS - Windows Sounds (mp3cut.net).wav");
					addArray(context);
					pen.println(context.output);
					return JSONParser.parse(context.output.toString());
			//Objects
            case 1:
              addObject(context);
              pen.println(context.output);
              return JSONParser.parse(context.output.toString());
              //In case of the end of the world (but I don't know how one would get to here)
            default:
            	errorBox("Tried to Exit!");
          }//while
	}//while
	}//Outer if
    return null;
  }//objectMaker()

  public static void addArray(UIContext context)
    throws Exception
  {
    boolean validInput = false;
   
    context.depthCount++;
    context.output.append("[");
    while (validInput == false)
      {
    	//The options
    	String[] ops = {"Add", "Close"};
    	playSound(1);
    	//Pop up box
    	context.optionsHolder = optionBox(ops,ops.length,
    			"What would you like to do to this array?",
    			"Current object:" + context.output.toString() 
    			+ "\n Click Add to add an element, press Close to close array");
        if (context.optionsHolder == 0)
          {
            addElement(context);
          }//if(add)

        else if (context.optionsHolder == 1)
          {
            if (context.output.charAt(context.output.length() - 1) == ',')
              {
                context.output.setCharAt(context.output.length() - 1, ']');
              }//if(comma)
            else
              {
                context.output.append("]");
              }//else
            if (context.depthCount > 1)
              {
                context.output.append(",");
              }//depthcount()

            context.depthCount--;
            return;
          }//if (close)

        else
          {
        	errorBox("Tryed to Exit!");
            
          }//else (error)
      }//while(false)
  }//addArray(UIContext)

  public static void addObject(UIContext context)
    throws Exception
  {

    context.depthCount++;
    context.output.append("{");
    while (true)
      {    
        String input;
    	String[] ops = {"add", "close"};
    	playSound(2);
    	context.optionsHolder = optionBox(ops,ops.length,
    			"What would you like to do to this object?",
    			"Current object:" + context.output.toString() 
    			+ "\nClick add to add element, press close to close object");
        switch (context.optionsHolder)
          {
            case 0:
            	playSound(3);
              input = submitBox("Please enter a string for your key", "Get key!");
              if(input.equals(""))
              {
              
            	  errorBox("Invalid Key!");
            	  //Deletes the {
            	  context.output.deleteCharAt(context.output.length()-1);
            	  addObject(context);
            	  return;
              }//if 
              else if(input.charAt(0) == '\"')
              {
            	  errorBox("No Quotes!");
            	  //Deletes the {
            	  context.output.deleteCharAt(context.output.length()-1);
            	  addObject(context);
            	  return;
              }//else if 
              else 
              {
              context.output.append("\"" + input + "\":");
              addElement(context);
              break;
              }//else
              
            case 1:
              if (context.output.charAt(context.output.length() - 1) == ',')
                {
                  context.output.setCharAt(context.output.length() - 1, '}');
                }//if(comma)
              else
                {
                  context.output.append("}");
                }//else
              if (context.depthCount > 1)
                {
                  context.output.append(",");
                }//if(depthcount)
              context.depthCount--;
              return;

            default:
            	errorBox("Trying to add a invalid object");
          }//switch(context.optionsHolder)
      }//While(true)

  }//addObject(UIContext)

  public static void addElement(UIContext context)
    throws Exception
  {
	playSound(4);
    String[] ops = {"Number","String","Array","Object","Boolean"};
    
    context.optionsHolder = optionBox(ops, ops.length,"Type Options","What type would you like this element to be?");
    
    String input;
    switch (context.optionsHolder)
      {
    	//Number 
        case 0:
          input = submitBox("Please enter your desired value", "Number Submit!");
          try
            {
              BigDecimal value = new BigDecimal(input);
            }//Try
          catch (Exception e)
            {
        	  errorBox("Not a number");
              addElement(context);
              return;
            }//Catch
          context.output.append(input + ",");
          return;
          //String
        case 1:
          input = submitBox("Please enter your desired string(No Quotes)", "String enter");
          //In case people don't listen
          if(input == null || input.equals(""))
          {
        	  errorBox("Empty String is not valid! Why would you want that?");
        	  addElement(context);
              return;
          }
          else if(input.charAt(0) == '\"')
          {
        	  errorBox("No Quotes!");
        	  addElement(context);
              return;
          }
          else
          {
          context.output.append("\"" + input + "\",");
          }//else 
          return;
          //Array
        case 2:
          addArray(context);
          return;
          //Object
        case 3:
          addObject(context);
          return;
          //Boolean
        case 4:
          String[] op = {"True", "False","Null"};
          context.optionsHolder = optionBox(op, op.length,"Boolean Submit!"
        		  ,"Click True to add true, False to add false, and Null to add null" );
          switch (context.optionsHolder)
            {
          //if it is true
              case 0:
                context.output.append("true,");
                return;
                //False
              case 1:
                context.output.append("false,");
                return;
                //Null
              case 2:
                context.output.append("null,");
                return;
            }//switch(context.optionsHolder)
        default:
        	errorBox("Tried to add an invalid element");
          addElement(context);
          return;
      }//Switch(context.optionsHolder)

  }//Add Element(UIContent)
  
  public static void messageBox(String message, String title)
  {
	  Frame frame = new Frame();
	  Icon icon = iconGet();
		JOptionPane.showMessageDialog(frame,
				message, title, 
				JOptionPane.PLAIN_MESSAGE, icon);
  }//messageBox(String, String)
  
	public static int optionBox(Object[] options, int numOfOptions, String topOfBox, String text) {
		Frame frame = new Frame();
		Icon icon = iconGet();
		
		int n = JOptionPane.showOptionDialog(frame, text, topOfBox,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, 
				icon, options, "");
		    
		return n;

  }//optionBox(Object[], int, String, String)
	
	public static String submitBox(String text, String topOfBox)
	{
		Frame frame = new Frame();
		Icon icon = iconGet();
		String input = (String) JOptionPane.showInputDialog(frame, text, topOfBox,
				JOptionPane.YES_NO_OPTION,icon, null, "");
		return input; 
	}//sumbitBox(String, String)
	
	public static void errorBox(String reason) throws IOException
	{
		Frame frame = new Frame(); 
		Icon icon = iconGet();
		Runtime.getRuntime().exec("say I can't do that Dave!");
		JOptionPane.showMessageDialog(frame,
    		    reason,
    		    "Please try again.",
    		    JOptionPane.ERROR_MESSAGE, icon);
		
	}//errorBox(String) 
	
	public static Icon iconGet(){
		String[] pathNames = {"1", "2", 
				"3", "4", "5", "6", "7", "8", "9", "10", "11",
				"12", "13", "14", "15", "16", "17", "18", "19","20", "21","22"};
		int index = (int) (Math.random() * pathNames.length);
		Icon icon = new ImageIcon("/Users/alexandragreenberg/Desktop/Stuff/icon" + pathNames[index] +".jpg");
		return icon;
		
	}//IconGet
	
	public static void playSound(int type) throws Exception
	{
		String[] files = {"Voice0035.wav", "Voice0036.wav", "Voice0037.wav","Voice0038.wav","Voice0039.wav", "Windows OS - Windows Sounds (mp3cut.net).wav"};
		
		LoopSound.play("/Users/alexandragreenberg/Desktop/Stuff/" + files[type]);
	}//playSound()

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

}//Class JSONObjectMaker

class UIContext
{
  int depthCount;
  StringBuffer output;
  int optionsHolder;
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
//Message when saying no and error message

//Same Rebelsky
//life advice/Code

//Greg Garcia
//JOptionPane and Error Messages

//http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
//Icon Help

//http://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
//Custum Icons

//http://www.javafaq.nu/java-bookpage-25-3.html
//Looked at

//http://code.google.com/p/json-simple/wiki/EncodingExamples
//Json ref

//images: 
/*
 * Taken from the instruction Data I/O manual pic I took for work.  
 * 
 */