package edu.grinnell.cs.greenbruns;

import java.math.BigDecimal;
import java.util.ArrayList;

public class OldParser {
	
	public static Object parse(String input, int index)
	  {
	    while (index < input.length())
	      {
	        // gets the next value
	        char current = input.charAt(index);
	        // if we have an object:
	        if (current == '{')
	          {
	            //STUB
	          }
	        // if we have a string, instead.
	        else if (current == '\"')
	          {
	            index++;
	            current = input.charAt(index);
	            // moves through name declaration to actual meat of the thing
	            while (current != '\"')
	              {
	                index++;
	                current = input.charAt(index);
	                // if we see a \ we skip the next element, so we don't get
	                // fooled by fake "'s
	                if (current == '\\')
	                  {
	                    index++;
	                  }// if
	              }// while

	            // while we are not at the end of the expression
	            while (current != ',')
	              {
	                index++;
	                current = input.charAt(index);

	                // if the next thing is an array:
	                if (current == '[')
	                  {
	                    ArrayList<Object> myArray = new ArrayList<Object>();
	                    while (current != ']')
	                      {
	                        //hangs here on run w/o explanation
	                        Object element = parse(input, index++);
	                        myArray.add(element);
	                        current = input.charAt(index);
	                      }//while
	                    return myArray;

	                  }// if

	                // if the next thing is a string:
	                else if (current == '\"')
	                  {
	                    String outString = "";
	                    index++;
	                    current = input.charAt(index);
	                    //while we haven't hit the end yet:
	                    while (current != '\"')
	                      {
	                        outString = outString + current;
	                        if (current == '\\')
	                          {
	                            index++;
	                            char help = input.charAt(index);
	                            outString.concat(Character.toString(help));
	                          }// if
	                        index++;
	                        current = input.charAt(index);
	                      }// while
	                    current = input.charAt(index++);
	                    return outString;
	                  }// else if

	                // if the next thing is a boolean:
	                else if (current == 'f' || current == 't' || current == 'n')
	                  {
	                    char curVal = current;
	                    //while we aren't at the end of the boolean, keep moving
	                    while (current != ',' && current != '}' && current != ']')
	                      {
	                        index++;
	                        current = input.charAt(index);
	                      }// while
	                    if (curVal == 'f')
	                      {
	                        return false;
	                      }// if
	                    else if (curVal == 't')
	                      {
	                        return true;
	                      }// else if
	                    else
	                      {
	                        return null;
	                      }// else
	                  }// else if

	                // if the next thing is an integer/double:
	                else if (Character.isDigit(current))
	                  {
	                    String preE = "";
	                    boolean hasE = false;
	                    String outString = "";

	                    //while we aren't at the end of the expression:
	                    while (current != ',' && current != '}' && current != ']')
	                      {
	                        // checks for an exponential expression
	                        if (current == 'e')
	                          {
	                            hasE = true;
	                            preE = outString;
	                            outString = "";
	                          }// if
	                        // if there's no exponent yet, just add it to the
	                        // string.
	                        else
	                          {
	                            outString = outString + current;
	                          }// else
	                        index++;
	                        current = input.charAt(index);
	                      }// while

	                    // if we are outputting an exponential expression
	                    if (hasE)
	                      {
	                        BigDecimal output = new BigDecimal(preE);
	                        BigDecimal exponent =
	                            new BigDecimal(
	                                           Math.pow(10,
	                                                    Double.parseDouble(outString)));
	                        output = output.multiply(exponent);
	                        return output;
	                      }// if
	                    // if our expression has no exponents
	                    else
	                      {
	                        return new BigDecimal(outString);
	                      }// else

	                  }// else if

	              }// while

	          }// else if

	      }// while
	    return index;

	  }// parse(String, int)
	
	

}
