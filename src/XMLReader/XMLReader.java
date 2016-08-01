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
	
	public XMLCursor openXMLFileFromFile(File file) throws IOException{
		return new XMLCursor(new Scanner(file));
	}
	
	public XMLCursor openXMLFileFromScanner(Scanner fileScanner) throws IOException{
		return new XMLCursor(fileScanner);
	}

}
