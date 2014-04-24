package edu.grinnell.cs.greenbruns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;

public class JSONParser
{

  /*
   * Object parse(String)
   * 
   * This is a wrapper class used for newParse. parse takes in a string and
   * creates a context object, which it calls newParse() on. This eventually
   * returns a JAVA Object representing the JSON object represented in input.
   * 
   * Does not fail fast, user should make sure their input is accurate JSON.
   */

  public static Object parse(String input)
    throws Exception
  {
    // return parse(input, 0);
    if (input.length() <= 0)
      {
        throw new Exception("Empty string is invalid input");
      }// if
    Context context = new Context();
    context.input = input;
    context.index = 0;
    Object result = newParse(context);
    return result;
  }// parse(String)

  /*
   * Object newParse(Context context)
   * 
   * This function takes an object of the context class as input, and returns an
   * Object. The returned object represents the object written as JSON in the
   * input string of the context object. Does not fail fast, accurate input
   * should be provided by user.
   */

  public static Object newParse(Context context)
  {
    char current = context.input.charAt(context.index);

    switch (current)
      {
        case '[':
          context.index++;
          ArrayList<Object> myArray = new ArrayList<Object>();
          current = context.input.charAt(context.index);
          while (current != ']')
            {
              Object element = newParse(context);
              myArray.add(element);
              current = context.input.charAt(context.index);
              context.index++;
            }// while
          return myArray;

        case '{':
          context.index++;
          Hashtable<Object, Object> myTable = new Hashtable<Object, Object>();
          current = context.input.charAt(context.index);
          while (current != '}')
            {
              Object key = newParse(context);
              context.index++;
              current = context.input.charAt(context.index);
              Object value = newParse(context);
              if(value != null)
              {
              myTable.put(key, value);
              } 
              else
              {
            	  myTable.put(key,"null");
              }
              current = context.input.charAt(context.index);
              context.index++;
            }// while
          return myTable;

          // Handle the object
        case '"':
          // Handle the string
          StringBuffer outString = new StringBuffer("");
          context.index++;
          current = context.input.charAt(context.index);
          // while we haven't hit the end yet:
          while (current != '\"')
            {
              outString.append(current);
              if (current == '\\')
                {
                  context.index++;
                  char help = context.input.charAt(context.index);
                  outString.append(Character.toString(help));
                }// if
              context.index++;
              current = context.input.charAt(context.index);
            }// while
          current = context.input.charAt(context.index++);

          return outString;

        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
        case '-':
          String preE = "";
          boolean hasE = false;
          StringBuffer outString2 = new StringBuffer("");

          // while we aren't at the end of the expression:
          while (current != ',' && current != '}' && current != ']')
            {
              outString2.append(current);
              context.index++;
              current = context.input.charAt(context.index);
            }// while

          // if we are outputting an exponential expression
          if (hasE)
            {
              BigDecimal output = new BigDecimal(preE);
              BigDecimal exponent =
                  new BigDecimal(Math.pow(10, Double.parseDouble(outString2.toString())));
              output = output.multiply(exponent);
              return output;
            }// if
             // if our expression has no exponents
          else
            {
              return new BigDecimal(outString2.toString());
            }// else

        default: // In this case, we handle a constant (null/true/false)
          char curVal = current;
          // while we aren't at the end of the boolean, keep moving
          while (current != ',' && current != '}' && current != ']')
            {
              context.index++;
              current = context.input.charAt(context.index);
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
        	  //????
        	  
              return null;
            }// else

      } // switch
  } // newParse()
} // JSONParser

// This class is used to keep track of globally important data while we recurse.
class Context
{
  /**
   * The string we're parsing.
   */
  String input;

  /**
   * The position in the string.
   */
  int index;

} // class Context

// ||SOURCES||

// http://stackoverflow.com/questions/18462826/split-string-only-on-first-instance-java
// for information about splitting just the first time something is encountered.

// http://json-schema.org/example1.html
// for understanding what different types look like in JSON.

// http://docs.oracle.com/javase/tutorial/java/nutsandbolts/switch.html
// for how to format a switch statement

// http://www.daniweb.com/software-development/java/threads/142323/what-is-the-code-for-exponent-in-java
// for a quick refresher on exponentiation

// http://www.w3schools.com/json/
// experiment code taken from

// http://stackoverflow.com/questions/3600686/java-boolean-instanceof-boolean
// figured out that you need "instanceof Boolean" instead of
// "instanceof boolean" for unparser

// Sam Rebelsky taught us how to dougie. He also gave us help figuring out the context
// class and suggested the use of stringbuffers.