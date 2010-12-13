import java.io.*;

import lejos.nxt.Button;
import lejos.nxt.LCD;


public class CommandParser {

	private static String commandString = "FETCH ID=3 X=25.46 Y=2.6 H=6.75 P=3\n";
	private static String fileName = "parsingTest.txt";
	
	public static void main(String[] args) 
	{
		
		DataLogger_parser logger = new DataLogger_parser(fileName);
		
		logger.writeLine("Starting test");
		logger.writeLine(commandString);
		
		LCD.clear();
		LCD.drawString("Start parsing",0,0);
		LCD.refresh();
		
		FetchCommand command = new FetchCommand(logger);
		command.Deserialize(commandString);

		LCD.clear();
		LCD.drawString("Command parsed",0,0);
		LCD.refresh();
		
		LCD.clear();
		LCD.drawString("Command to file",0,0);
		LCD.drawString("ID = " + command.getID(), 0, 1);
		LCD.drawString("X = " + command.getX(), 0, 2);
		LCD.refresh();
		
		
		logger.writeLine(command.toString());
		logger.writeLine(command.Serialize());
//		LCD.clear();
//		LCD.drawString("Done",0,0);
//		LCD.refresh();
		
		logger.close();
		while (! Button.ESCAPE.isPressed()){}
		
		
		LCD.clear();
		LCD.drawString("Closing...",0,0);
		LCD.refresh();
	}

}
