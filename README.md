CSC207-Designing-a-JSON-Library
===============================

This JSON Library is intended to allow users to convert JAVA Objects(Specifically, Hashtables and Arrays) into JSON code, and also allows the conversion of properly formatted JSON code into JAVA Objects.

The user should note that this code is licensed under the GPL V2 License, and so they should be careful to abide by its standards.

--------
|Parser|
--------
Firstly, users should note that the parser only takes accurate JSON as input, and has very little error correction. So, users should try to avoid inaccurate input- even added spaces may cause the code to not run.

As input, the parser takes a String of JSON. The parser function will then process the string, and return the JAVA Object equivalent of the JSON string.

JSON Objects are converted to JAVA Hashtables, JSON Arrays are converted to JAVA Arrays, JSON Strings become JAVA Strings, JSON Numbers become JAVA BigDecimals, and the values of JSON's true, false and null are preserved.

----------
|Unparser|
----------
The unparser will take JAVA Objects, and return their equivalent in JSON. The code produced is entirely functional JSON code, with correct formatting. The only JAVA Objects the unparser takes are Arrays and HashTables, which become into JSON Arrays and Objects, respectively.

-------
|Notes|
-------

Because we implement JSON Objects as JAVA HashTables, the order of objects within the JSON Object is NOT preserved. If the user wishes to maintain an order, they should consider using a JSON Array.

