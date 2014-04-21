package edu.grinnell.cs.greenbruns;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;

public class JSONUnparser
{

  /*
   * unparse(Object) is a wrapper for unparser().
   * 
   * It takes in a java object, and creates a context object for unparser() to
   * be called on.
   */

  public static String unparse(Object input)
    throws Exception
  {
    AltContext context = new AltContext();
    context.input = input;
    context.output = new StringBuffer("");
    return unparser(context);
  }// unparse(Object input)

  /*
   * unparser(context) looks at the JAVA object stored in an AltContext object
   * and returns how it would be represented in a string of JSON. The object is
   * unchanged.
   */

  public static String unparser(AltContext context)
    throws Exception
  {
    if (context.input instanceof Hashtable)
      {
        Object[] keyArray = ((Hashtable) context.input).keySet().toArray();
        Object[] valArray = ((Hashtable) context.input).values().toArray();
        context.output.append("{");
        for (int i = 0; i < keyArray.length; i++)
          {
            if (i != keyArray.length - 1)
              {
                context.output.append("\"" + keyArray[i] + "\":"
                                      + unparse(valArray[i]) + ",");
              }// if
            else
              {
                context.output.append("\"" + keyArray[i] + "\":"
                                      + unparse(valArray[i]));
              }// else
          }// for

        context.output.append("}");
      }// if

    else if (context.input instanceof ArrayList)
      {
        context.output.append("[");
        ((ArrayList) context.input).trimToSize();
        Object[] temp = ((ArrayList) context.input).toArray();

        for (int i = 0; i < temp.length; i++)
          {
            if (i != temp.length - 1)
              {
                context.output.append(unparse(temp[i]) + ",");
              }// if
            else
              {
                context.output.append(unparse(temp[i]));
              }// else
          }// for
        context.output.append("]");
      }// else if

    else if (context.input instanceof String || context.input instanceof StringBuffer)
      {
        context.output.append("\"" + context.input + "\"");
      }// else if

    else if (context.input instanceof Boolean
             || context.input instanceof BigDecimal || context.input == null)
      {
        if (context.input == null)
          {
            context.output.append("null");
          }// if
        else
          {
            context.output.append(context.input.toString());
          }// else
      }// else if

    else
      {
        throw new Exception("Input not created from valid components.");
      }// else
    return context.output.toString();
  }// unparser(AltContext)
}// class JSONUnparser

class AltContext
{
  /**
   * The giant string we are building, to eventually return
   */

  StringBuffer output;

  /**
   * The input we get
   */

  Object input;

} // class Context
