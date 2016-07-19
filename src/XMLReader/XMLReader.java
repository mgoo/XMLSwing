package XMLReader;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class XMLReader {

	public XMLReader(){

	}

	public XMLCursor openXMLFile(String fileName) throws IOException{
		return new XMLCursor(new Scanner(new File(fileName)));
	}

}
