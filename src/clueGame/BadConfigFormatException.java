package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Incorrect config format");
	}
	
	public BadConfigFormatException(String error) {
		super(error);
		try {
			PrintWriter writer = new PrintWriter(new File("logfile.txt"));
			writer.println(error);
			writer.close();
		}
		catch (FileNotFoundException e){
			System.out.println("File not found: logfile.txt");
		}
	}
}
