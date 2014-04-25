package edu.grinnell.cs.greenbruns;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.awt.event.WindowEvent;
import javax.swing.*;



public class JSONObjectMakerUI {

	/**
	 * ObjectMaker()
	 * This function is the starting point for the rest of the program,
	 * and allows the user to decide:
	 * - if they want sound
	 * - what the outer layer structure is (object or array)
	 * 
	 * Eventually returns the JAVA Object the user describes.
	 * */
	public static Object objectMaker() throws Exception {
		// Welcomes you into the parser
		Object temp;
		javax.swing.JFrame frame = new javax.swing.JFrame();
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.INFORMATION_DIALOG);
		
		messageBox("Welcome to the JSON Maker!\n" + "This tool allows you to intuitively make a JAVA Object\n"
				+ "and also generates equivalent JSON code \n", "Welcome!",frame);
		// Offers the user sound or not
		String[] yesOrNo = { "Yes", "No" };
		
		int k = optionBox(yesOrNo, yesOrNo.length, "Confused?",
				"Would you like to know a little more about Json?", frame);
		
		if (k == 0)
		{
			
			messageBox("JavaScript Object Notation,"
					+ "is an open standard format that uses\n"
					+ "human readable text to transmit"
					+ "data objects consisting of attribute value pairs", "Json Info", frame);
		}

		int j = optionBox(yesOrNo, yesOrNo.length,
				 "Sound?","Would you like sound today?", frame);

		// Asks the user if they're willing to JSON
		Object[] options = { "Yes!", "No", "Maybe?" };

		int n = optionBox(options, options.length, "Choose one",
				"Are you ready to make some JSON!?",frame);

		if (n == 1) {
			// sad message for when the user decides to not JSON
			messageBox("Frankly my dear I don't give a SWAG!", "Nooo!", frame);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}// if(n==1)
		else {
			//makes our UIContext
			UIContext context = new UIContext();
			//Checks if the user want sound
			if (j == 0) {
				context.sound = true;
			}// if
			else {
				context.sound = false;
			}// else
			context.depthCount = 0;
			//creates a frame 
			context.frame = new javax.swing.JFrame();
			context.output = new StringBuffer("");
			context.exit = false;
			boolean validInput = false;
			
			//set frame default
			context.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			// Make a pen to give output
			PrintWriter pen = new PrintWriter(System.out, true);

			while (validInput == false) {
				//provides the choice of object or array
				playSound(0, context.sound);
				String[] ops = { "Array", "Object" };
				context.optionsHolder = optionBox(ops, ops.length,
						"Hightest Level",
						"Click Object for an object, and Array for an array!", context.frame);

				switch (context.optionsHolder) {
				// Arrays
				case 0:
					// if we add an array
					addArray(context);
					if (context.exit) {
						temp = JSONParser.parse("[]");
						context.frame.dispatchEvent(new WindowEvent(frame,
								WindowEvent.WINDOW_CLOSING));
					}// if(exit)
					else {
						//Displays the "object" in a window
						messageBox(context.output.toString(), "Your Json!",context.frame);
						temp = JSONParser.parse(context.output.toString());
						// closes the window
						context.frame.dispatchEvent(new WindowEvent(frame,
								WindowEvent.WINDOW_CLOSING));
						pen.println(context.output);
					}// Else
					return temp;
					// Objects
				case 1:
					//if we add an object
					addObject(context);
					if(context.exit)
					{
						temp = JSONParser.parse("{}");
						context.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}
					else {
					
					//displays the json object
					messageBox(context.output.toString(), "Your Json!",context.frame);
					pen.println(context.output);
					temp= JSONParser.parse(context.output.toString());
					//close the window
					context.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					pen.println(context.output);
					}
					return temp;
				default:
					// When some clicks the the red x
					temp = JSONParser.parse("[{}]");
					context.frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					return temp;
				}// while
			}// while
		}// Outer if
		return null;
	}// objectMaker()

	/**
	*addArray() allows the user to add an array to their JAVA object
	*returns nothing, but modifies context.output
	*/
	
	public static void addArray(UIContext context) throws Exception {
		boolean validInput = false;
			context.depthCount++;
			context.output.append("[");
			while (validInput == false) {
				// The options
				String[] ops = { "Add", "Close", "Cancel" };
			playSound(1, context.sound);
			// Pop up box
			context.optionsHolder = optionBox(
					ops,
					ops.length,
					"What would you like to do to this array?",
					"Current object:"
							+ context.output.toString()
							+ "\n Click Add to add an element, press Close to close array", context.frame);
			//if they want to add an element
			switch(context.optionsHolder)
			{
			case 0:
				addElement(context);
			case 1:
				if (context.output.charAt(context.output.length() - 1) == ',') {
					context.output.setCharAt(context.output.length() - 1, ']');
				}// if(comma)
				else {
					context.output.append("]");
				}// else
				if (context.depthCount > 1) {
					context.output.append(",");
				}// depthcount()

				context.depthCount--;
				return;
			case 2:
				if(context.depthCount <= 1)
				{
				context.exit = true;
				return;
				}//if 
				else 
				{
					addElement(context);
					return;
				}//else
			default:
				errorBox("Trying to exit!");
				return;
				}// Switch

			}// while(false)
	
	}// addArray(UIContext)

	
	/**
	 * addObject allows the user to add a nested object to their JAVA Object.
	 * Returns nothing, but modifies context.output
	 * 
	 * */
	public static void addObject(UIContext context) throws Exception {

		context.depthCount++;
		context.output.append("{");
		while (true) {
			String input;
			String[] ops = { "Add", "Close", "Cancel" };
			playSound(2, context.sound);
			context.optionsHolder = optionBox(
					ops,
					ops.length,
					"What would you like to do to this object?",
					"Current object:"
							+ context.output.toString()
							+ "\nClick add to add element, press close to close object", context.frame);
			switch (context.optionsHolder) {
			case 0:
				//if the user wants to add an element
				playSound(3, context.sound);
				input = submitBox("Please enter a string for your key",
						"Get key!");
				if (input.equals("")) {
					//if the user gives an invalid key (empty key)
					errorBox("Invalid Key!");
					// Deletes the {
					context.output.deleteCharAt(context.output.length() - 1);
					addObject(context);
					return;
				}// if
				else if (input.charAt(0) == '\"') {
					//if the user enters quotes immediately
					errorBox("Please Don't Enter Quotes!");
					// Deletes the {
					context.output.deleteCharAt(context.output.length() - 1);
					addObject(context);
					return;
				}// else if
				else {
					//if everything is good, we continue to add the paired value.
					context.output.append("\"" + input + "\":");
					addElement(context);
					break;
				}// else

			case 1:
				// if the user wants to close the object
				if (context.output.charAt(context.output.length() - 1) == ',') {
					context.output.setCharAt(context.output.length() - 1, '}');
				}// if(comma)
				else {
					context.output.append("}");
				}// else
				if (context.depthCount > 1) {
					context.output.append(",");
				}// if(depthcount)
				context.depthCount--;
				return;
			case 2:
				context.exit = true;
				return;
			default:
				errorBox("Trying to add a invalid object");
			}// switch(context.optionsHolder)
		}// While(true)

	}// addObject(UIContext)

	/**
	 * addElement lets the user choose which sort of thing they would like to add
	 * Options are:
	 * -array
	 * -object
	 * -boolean/null
	 * -number
	 * -string
	 * Returns nothing, but modifies context.output
	 * */
	public static void addElement(UIContext context) throws Exception {
		playSound(4, context.sound);
		//gives the user a menu to add elements with
		String[] ops = { "Number", "String", "Array", "Object", "Boolean" };

		context.optionsHolder = optionBox(ops, ops.length, "Type Options",
				"What type would you like this element to be?", context.frame);

		String input;
		switch (context.optionsHolder) {
		// Number
		case 0:
			input = submitBox("Please enter your desired value",
					"Number Submit!");
			//in case people click the cancel button
			if(input == null)
			{
				addElement(context);
					return;
			}
			try {
				//makes sure the input is a valid number
				BigDecimal value = new BigDecimal(input);
			}// Try
			catch (Exception e) {
				//if the input is invalid, we let the user try again.
				errorBox("Not a number");
				addElement(context);
				return;
			}// Catch
			context.output.append(input + ",");
			return;
			// String
		case 1:
			input = submitBox("Please enter your desired string(No Quotes)",
					"String enter");
			//In case people want to exit
			if (input == null) {
				addElement(context);
					return;
			}// if
			// In case people don't listen
			else if(input.equals(""))
			{
				errorBox("Empty String is not valid!");
				addElement(context);
				return;
			}
			else if (input.charAt(0) == '\"') {
				errorBox("No Quotes!");
				addElement(context);
				return;
			}// if
			else {
				context.output.append("\"" + input + "\",");
			}// else
			return;
			// Array
		case 2:
			addArray(context);
			return;
			// Object
		case 3:
			addObject(context);
			return;
			// Boolean
		case 4:
			String[] op = { "True", "False", "Null", "Cancel" };
			context.optionsHolder = optionBox(op, op.length, "Boolean Submit!",
					"Click True to add true, False to add false, and Null to add null", context.frame);
			switch (context.optionsHolder) {
			// if it is true
			case 0:
				context.output.append("true,");
				return;
				// False
			case 1:
				context.output.append("false,");
				return;
				// Null
			case 2:
				context.output.append("null,");
				return;
			case 3:
				addElement(context);
				return;
			}// switch(context.optionsHolder)
		default:
			//if the user somehow clicks a box that doesn't exist
			//really just a catch-all case
			errorBox("Tried to add an invalid element");
			addElement(context);
			return;
		}// Switch(context.optionsHolder)

	}// Add Element(UIContent)

	/**
	 * messageBox is a dialog box that only gives a message
	 * it provides the user no options, and returns void.
	 * 
	 * It takes two strings, the dialog of the box and the box title.
	 * @throws URISyntaxException 
	 * */
	public static void messageBox(String message, String title, javax.swing.JFrame frame) throws URISyntaxException {
		Icon icon = iconGet();
		JOptionPane.showMessageDialog(frame, message, title,
				JOptionPane.PLAIN_MESSAGE, icon);
	}// messageBox(String, String)

	/**
	 * optionBox is a dialog box that gives a message and options
	 * it returns the number representing the user-chosen option.
	 * 
	 * It takes an array of objects (the options), an integer representing 
	 * the number of options, and two strings, the dialog of the box and the box title.
	 * @throws URISyntaxException 
	 * */
	public static int optionBox(Object[] options, int numOfOptions,
			String topOfBox, String text,javax.swing.JFrame frame) throws URISyntaxException {
		
		Icon icon = iconGet();

		int n = JOptionPane.showOptionDialog(frame, text, topOfBox,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
				icon, options, "");

		return n;

	}// optionBox(Object[], int, String, String)

	/**
	 * submitBox is a dialog box that takes a string of input and gives a message.
	 * it returns the string input by the user.
	 * 
	 * It takes two strings, the dialog of the box and the box title.
	 * @throws URISyntaxException 
	 * */
	public static String submitBox(String text, String topOfBox) throws URISyntaxException {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		Icon icon = iconGet();
		String input = (String) JOptionPane.showInputDialog(frame, text,
				topOfBox, JOptionPane.DEFAULT_OPTION, icon, null, "");
		return input;
	}// sumbitBox(String, String)

	/**
	 * errorBox is a dialog box that gives an error message
	 * It also plays an audio clip, signifying the presence of an error.
	 * it provides the user no options, and returns void.
	 * 
	 * It takes one string, the reason the program has failed.
	 * @throws URISyntaxException 
	 * */
	public static void errorBox(String reason) throws IOException, URISyntaxException {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		Icon icon = iconGet();
		Runtime.getRuntime().exec("say I can't do that Dave!");
		JOptionPane.showMessageDialog(frame, reason, "Please try again.",
				JOptionPane.ERROR_MESSAGE, icon);

	}// errorBox(String)

	/**
	 * iconGet is a function used to beautify our software.
	 * iconGet takes no input, but grabs an image to display on the side of the screen.
	 * iconGet returns an Icon.
	 * @throws URISyntaxException 
	 * */
	public static Icon iconGet() throws URISyntaxException {
		
		
		String[] pathNames = { "1", "2", "3", "4", "5", "6", "7", "8", "9",
				"10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
				"20", "21", "22" };
		int index = (int) (Math.random() * pathNames.length);
		
		java.nio.file.Path imgPath = Paths.get("bin/Needed/icon"+pathNames[index] +".jpg");
		
		Icon icon = new ImageIcon(imgPath.toAbsolutePath().toString());
		return icon;

	}// IconGet

	/**
	 * playSound plays an audio clip to the user.
	 * as input, playSound takes an integer which references an audio clip in an array.
	 * playSound returns no output.
	 * */
	public static void playSound(int type, boolean play) throws Exception {
		String[] files = { "Voice0035.wav", "Voice0036.wav", "Voice0037.wav",
				"Voice0038.wav", "Voice0039.wav",
				"Windows OS - Windows Sounds (mp3cut.net).wav" };
		if (play)
			Audio.play("/Users/alexandragreenberg/Desktop/Stuff/"
					+ files[type]);

	}// playSound()

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try 
	    { 
	        UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel"); 
	    } 
	    catch(Exception e){ 
	    	JFrame errorMes= new JFrame();
	    	messageBox("You don't have that look and feel. But you can still make some Json!",
	    			"Sorry!", errorMes);
	    }
		System.out.println(objectMaker());
	}// main()

}// Class JSONObjectMaker

/**
 * UIContext is a context object for our user interface
 * we use this object to pass useful information between procedures.
 * */
class UIContext {
	//counts how many structures deep we are, so we know when to stop.
	int depthCount;
	//keeps track of our output
	StringBuffer output;
	//holds the integers we get from optionsBox
	int optionsHolder;
	//true if the user wants sound, false otherwise.
	boolean sound;
	boolean exit;
	javax.swing.JFrame frame;
}// UIContext class

// SOURCES:

// http://www.cs.grinnell.edu/~rebelsky/Courses/CSC207/2014S/readings/io.html
// I love this reading so much <3, I read it every time I do i/o in Java.

// http://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java (and
// time)!
// How to play sounds in Java

// http://www.wikihow.com/Add-JARs-to-Project-Build-Paths-in-Eclipse-%28Java%29
// How to import Jars

// http://docs.oracle.com/javase/8/javafx/get-started-tutorial/jfx-overview.htm#JFXST784
// Explanation

// http://www.cs.cmu.edu/~illah/CLASSDOCS/javasound.pdf
// Played clip

// http://docs.oracle.com/javase/tutorial/uiswing/components/dialog.html#dialogdemo
// Boxes

// Helen Doughery
// Message when saying no and error message

// Same Rebelsky
// life advice/Code

// Greg Garcia
// JOptionPane and Error Messages

// http://docs.oracle.com/javase/tutorial/uiswing/components/icon.html
// Icon Help

// http://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
// Custum Icons

// http://www.javafaq.nu/java-bookpage-25-3.html
// Looked at

// http://code.google.com/p/json-simple/wiki/EncodingExamples
// Json ref

// images:
/*
 * Taken from the instruction Data I/O manual pic I took for work.
 */

//http://tips4java.wordpress.com/2009/05/01/closing-an-application/
//Closes the window for you.

//http://docs.oracle.com/javase/tutorial/uiswing
///examples/components/FrameDemoProject/src/components/FrameDemo.java
//BackGround 

//http://stackoverflow.com/questions/9541045/how-to-set-jframe-look-and-feel
//Look and feel help

//Dave Mathews band
//For be a good band to code by 

//http://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
//Look and feel

//http://tips4java.wordpress.com/2009/05/01/closing-an-application/
//Closing it when it is done

//http://stackoverflow.com/questions/18386613/java-getting-file-as-resource-when-its-in-the-project-folder
//Looked at

//http://stackoverflow.com/questions/19473005/why-doesnt-getimage-return-an-error-or-display-an-image?lq=1
//Helped with relative paths	

//Grinnell College
//For having working Internet, so that I could make this amazing citation list

//http://en.wikipedia.org/wiki/JSON
//For Json Info for message