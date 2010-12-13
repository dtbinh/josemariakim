import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import java.util.*;

/**
 * Receive data from another NXT, a PC, a phone, 
 * or another bluetooth device.
 * 
 * 
 * @author Lawrie Griffiths
 *
 */
public class BTReceive {

	private static String fileName = "testlog1.txt";
	public static void main(String [] args)  throws Exception 
	{
		String connected = "Connected";
        String waiting = "Waiting...";
        String closing = "Closing...";
        
        
        DataLogger logger = new DataLogger(fileName);
		
		LCD.drawString(waiting,0,0);
		LCD.refresh();
		logger.writeLine(waiting);

        BTConnection btc = Bluetooth.waitForConnection();
        
		LCD.clear();
		LCD.drawString(connected,0,0);
		LCD.refresh();	
		logger.writeLine(connected);
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		String commandString = "";
		char c = dis.readChar();
		while(c!='\n'){
			commandString += c;
			c = dis.readChar();
		}
		
		if(commandString.length() > 0)
		{
			String line = "Command received: " + commandString;
			logger.writeLine(line);
		}
		
		LCD.drawString("Parsing the command", 0, 0);
		Command command = new Command(null);
		command.Deserialize(commandString);
		
		logger.writeLine(command.toString());
		
		LCD.drawString("Command parsed", 0, 0);
		LCD.refresh();
		dos.writeChars("ACK\n");
		dos.flush();
		
		dis.close();
		dos.close();
		Thread.sleep(100); // wait for data to drain
		
		logger.close();
		while (! Button.ESCAPE.isPressed()){}
		
		LCD.clear();
		LCD.drawString(closing,0,0);
		LCD.refresh();
		btc.close();
		LCD.clear();
		
	}
}

