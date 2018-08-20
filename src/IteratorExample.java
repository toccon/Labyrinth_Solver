import java.io.*;
import java.util.*;

public class IteratorExample {

    public static void main(String[] args) {

	BufferedReader in;
	String line;
        String fileName = args[0];

	Vector<String> v = new Vector<String>();
	Iterator<String> iter;

	try {
            // Open the input file
	    in = new BufferedReader(new FileReader(fileName));

            // Read one line of text from the file
	    line = in.readLine();

	    while (line != null) {  // line != null as long as the end of the
                                    // file has not been reached
		v.add(line);        // Store the lines of text from the file
                                    // in the vector v
		line = in.readLine();
	    }
	    in.close(); //Close the file

	    iter = v.iterator();

	    // read the lines of text and print them out by using the iterator.
	    while (iter.hasNext())
		System.out.println(iter.next());

	    System.out.println("");
	}

	catch (IOException e) {
	    System.out.println("Error reading input file: "+fileName);
	}
    }
}
