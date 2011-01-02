import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;

import communication.Command;
import communication.DataLogger;
import communication.Utils;


/**
 * Receive data from another NXT, a PC, a phone, 
 * or another bluetooth device.
 * 
 * 
 * @author Lawrie Griffiths
 *
 */
public class BTReceive {

	private static String filePrefix = "receiverLog";
	public static void main(String [] args)  throws Exception 
	{
		String connected = "Connected";
        String waiting = "Waiting...";
        String closing = "Closing...";
        
        String fileName = Utils.getFileName(filePrefix);
        DataLogger logger = new DataLogger(fileName);
		
        Utils.writeUpperLineToLCD(waiting);
		logger.writeLine(waiting);

        BTConnection btc = Bluetooth.waitForConnection();
        
        Utils.writeUpperLineToLCD(connected);
        
		logger.writeLine(connected);
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		for(int i = 0; i<10; i++)
		{
			String commandString = Utils.receive(dis);
					
			if(commandString.length() > 0)
			{
				logger.logReceived(commandString);
				
				Utils.writeUpperLineToLCD("Parsing the command");
				
				Command command = new Command();
				command.Deserialize(commandString);
					
				Utils.writeUpperLineToLCD("Command parsed");
				
				String ack = "ACK\n";
				Utils.send(dos, ack);
				logger.logSent(ack);
			}
			else
			{
				logger.logReceived("null");
			}
		}
		
		dis.close();
		dos.close();
		Thread.sleep(100); // wait for data to drain
		
		logger.close();
		while (! Button.ESCAPE.isPressed()){}
		
		Utils.writeUpperLineToLCD(closing);
		
		btc.close();
		LCD.clear();
		
	}
	
}

