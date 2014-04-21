package edu.grinnell.cs.greenbruns;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class JSONParseUnparseTest
{

  @Test
  public void emptyTest() //Test based on the empty string
  {
    boolean thrown = false;
    try
      {
        JSONParser.parse("");
      }
    catch (Exception e)
      {
        thrown = true;
      }
    assertTrue(thrown);
  }

  @Test
  public void emptyStructureTest() //Tests giving legal empty structures
    throws Exception
  {
    Assert.assertEquals("Empty Object Test", "{}",
                        JSONUnparser.unparse(JSONParser.parse("{}")));
    Assert.assertEquals("Empty Array Test", "[]",
                        JSONUnparser.unparse(JSONParser.parse("[]")));
  }

//  @Test
//  public void bigTest() //Tests with a mixture of arrays and objects
//    throws Exception
//  {
//    Assert.assertEquals("BigTest",
//                        "{\"employees\":[{\"lastName\":\"Doe\",\"firstName\":\"John\"},{\"lastName\":\"Smith\",\"firstName\":\"Anna\"},{\"lastName\":\"Jones\",\"firstName\":\"Peter\"}]}",
//                        JSONUnparser.unparse(JSONParser.parse("{\"employees\":[{\"firstName\":\"John\",\"lastName\":\"Doe\"},{\"firstName\":\"Anna\",\"lastName\":\"Smith\"},{\"firstName\":\"Peter\",\"lastName\":\"Jones\"}]}")));
//    Assert.assertEquals("BigTest",
//                        "{\"triplething\":{\"thingthing\":{\"thing\":[\"name of thing\",\"quick, hack the gui!\",\"Oh no it's triple encrypted!\"]}}}",
//                        JSONUnparser.unparse(JSONParser.parse("{\"triplething\":{\"thingthing\":{\"thing\":[\"name of thing\",\"quick, hack the gui!\",\"Oh no it's triple encrypted!\"]}}}")));
//  }

  @Test
  public void arrayTest() //Tests with simple and more complex arrays
    throws Exception
  {
    Assert.assertEquals("ArrayTest 1",
                        "[\"bubba\",\"susan\",\"bobby\"]",
                        JSONUnparser.unparse(JSONParser.parse("[\"bubba\",\"susan\",\"bobby\"]")));
    Assert.assertEquals("ArrayTest 2",
                        "[\"bubba\",[\"susan\",\"bobby\"],\"raoul\"]",
                        JSONUnparser.unparse(JSONParser.parse("[\"bubba\",[\"susan\",\"bobby\"],\"raoul\"]")));
    Assert.assertEquals("ArrayTest 3",
                        "[\"bubba\",[[\"susan\",[\"bobby\"]],\"raoul\"]]",
                        JSONUnparser.unparse(JSONParser.parse("[\"bubba\",[[\"susan\",[\"bobby\"]],\"raoul\"]]")));
  }

  @Test
  public void objectTest() //Tests with simple and more complex objects
    throws Exception
  {
    Assert.assertEquals("ObjectTest 1",
                        "{\"thing\":\"name of thing\"}",
                        JSONUnparser.unparse(JSONParser.parse("{\"thing\":\"name of thing\"}")));
    Assert.assertEquals("ObjectTest 2",
                        "{\"thingthing\":{\"thing\":\"name of thing\"}}",
                        JSONUnparser.unparse(JSONParser.parse("{\"thingthing\":{\"thing\":\"name of thing\"}}")));
    Assert.assertEquals("ObjectTest 3",
                        "{\"triplething\":{\"thingthing\":{\"thing\":\"name of thing\"}}}",
                        JSONUnparser.unparse(JSONParser.parse("{\"triplething\":{\"thingthing\":{\"thing\":\"name of thing\"}}}")));
  }

  @Test
  public void booleanTest() //Test on boolean values.
    throws Exception
  {
    Assert.assertEquals("BooleanTest",
                        "[true,false,true,true,null,false]",
                        JSONUnparser.unparse(JSONParser.parse("[true,false,true,true,null,false]")));
  }

  @Test
  public void numbersTest() //Tests on a variety of numeric inputs.
    throws Exception
  {
    Assert.assertEquals("NumbersTest 1",
                        "[1.2,1.3,1.4,1.5,1.6,1.7]",
                        JSONUnparser.unparse(JSONParser.parse("[1.2,1.3,1.4,1.5,1.6,1.7]")));
    Assert.assertEquals("NumbersTest 2",
                        "[10,20,1E+1,1E+2,1E+4]",
                        JSONUnparser.unparse(JSONParser.parse("[10,20,1e1,1e2,1e4]")));
    Assert.assertEquals("NumbersTest 3",
                        "[-10,-20,-1E+1,-1E+2,-1E+4]",
                        JSONUnparser.unparse(JSONParser.parse("[-10,-20,-1e1,-1e2,-1e4]")));
  }

}


// http://stackoverflow.com/questions/156503/how-do-you-assert-that-a-certain-exception-is-thrown-in-junit-4-tests
// for asserting exceptions code