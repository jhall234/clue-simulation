package clueGame;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Incorrect config format");
	}
	
	public BadConfigFormatException(String configFileName) {
		super(configFileName + " isn't formatted correctly");
	}
}
