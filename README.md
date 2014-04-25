README - Jsong - GUI JSON Parser with Audio
===========================================

Created by Alex Greenburg and Evan Bruns

--------------
|Introduction|
--------------
This JSON Library is intended to allow users to convert JAVA Objects(Specifically, Hashtables and Arrays) into JSON code, and also allows the conversion of properly formatted JSON code into JAVA Objects. This library also provides an Object Maker, which allows the user to create JAVA Objects and JSON code from an intuitive GUI.

The user should note that this code is licensed under the GPL V2 License, and so they should be careful to abide by its standards.

---------------
|Compatability|
---------------
The Parser and Unparser functionality of this JSON Library should work with any version of JAVA, but the Object Maker's advanced features, like sound and the GUI, make it only compatable with JAVA 7. However, even users with JAVA 7 may have some troubles getting the JAVAfx library to load. Fixing this simply involves adding the JAVAfx JAR to the user's IDE.

One explanation of this we found was: http://www.guigarage.com/2012/10/adding-javafx-to-your-eclipse-project/

--------
|Parser|
--------
Firstly, users should note that the parser only takes accurate JSON as input, and has very little error correction. So, users should try to avoid inaccurate input- even added spaces may cause the code to not run.

As input, the parser takes a String of JSON. The parser function will then process the string, and return the JAVA Object equivalent of the JSON string.

JSON Objects are converted to JAVA Hashtables, JSON Arrays are converted to JAVA Arrays, JSON Strings become JAVA Strings, JSON Numbers become JAVA BigDecimals, and the values of JSON's true, false and null are preserved.

----------
|Unparser|
----------
The unparser will take JAVA Objects, and return their equivalent in JSON. The code produced is entirely functional JSON code, with correct formatting. The only JAVA Objects the unparser takes are Arrays and HashTables, which become into JSON Arrays and Objects, respectively. These Arrays and HashTables can have a variety of JAVA Objects inside of them, however, including Arrays, HashTables, Strings, Booleans/null, and BigDecimals.

--------------
|Object Maker|
--------------
The object maker functionality is what sets our JSON Library apart from many others. We provide a GUI with optional audio cues which allows the user to create a JSON object of their very own. This part of our program was written for users with little to no knowledge of JSON.

The object maker's GUI walks the user through all the things they need to do to create their object. How the user gets the object back is in two forms: firstly, the JSON representation of the object is returned as a string in the JAVA console. Also, the parsed version (Object representation) of the JSON String is the return type of the objectMaker, allowing the user to create an object using our GUI, and then to immediately start using the created Object in JAVA.

-------
|Notes|
-------

Because we implement JSON Objects as JAVA HashTables, the order of objects within the JSON Object is NOT preserved. If the user wishes to maintain an order, they should consider using a JSON Array.
(It should be noted that the Object Maker preserves the user-entered order of objects in the JSON code it produces, but that the JAVA Object it returns may not have this same order.)

The GUI method of creating Objects and JSON code is the "safe" way- there is careful error checking and it is rather user friendly. Conversely, our parser and unparser were built to maximize speed, and so have little to no checking for user error. The user should be careful to input only valid JAVA Objects and JSON code to these functions.
