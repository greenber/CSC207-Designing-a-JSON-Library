package edu.grinnell.cs.greenbruns;


public class JSONParserExpt
{
  public static void main(String[] args) throws Exception
  {
    Object obj = JSONParser.parse("{\"employees\":[{\"firstName\":\"John\",\"lastName\":\"Doe\"},{\"firstName\":\"Anna\",\"lastName\":\"Smith\"},{\"firstName\":\"Peter\",\"lastName\":\"Jones\"}]}");
    System.out.println(obj.toString());
    System.out.println(JSONUnparser.unparse(obj));
    obj = JSONParser.parse(JSONUnparser.unparse(obj));
    System.out.println(obj.toString());
    System.out.println(JSONParser.parse(""));
  } // main(String[])


} // JSONParserExpt