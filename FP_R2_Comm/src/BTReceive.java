import lejos.nxt.comm.*;
import java.io.*;

import communication.ACKCommand;
import communication.Command;
import communication.DataLogger;
import communication.FetchCommand;
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

	public static ObjectLocation WaitAndReceiveLocation(DataLogger logger) 
	{
		String connected = "Connected";
        String waiting = "Waiting...";
        
        ObjectLocation location = null;
        
        Utils.writeUpperLineToLCD(waiting);
		logBluetooth(waiting, logger);

        BTConnection btc = Bluetooth.waitForConnection();
        
        Utils.writeUpperLineToLCD(connected);
        
        logBluetooth(connected, logger);
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		String commandString;
		try {
			commandString = Utils.receive(dis);
			
		} catch (IOException e) {
			logBluetooth("Error receiving command", logger);
			return null;
		}
				
		if(commandString.length() > 0)
		{
			logger.logReceived(commandString);
			
			Utils.writeUpperLineToLCD("Parsing the command");
			
			Command command = new Command();
			command.Deserialize(commandString);
				
			Utils.writeUpperLineToLCD("Command parsed");
			
			ACKCommand ack = new ACKCommand();
			
			try {
				Utils.send(dos, ack.Serialize());
			} catch (IOException e) {
				logBluetooth("Error sending ACK", logger);
				return null;
			}
			logger.logSent(ack.Serialize());
			
			if(command.getCommandID() == "FETCH")
			{
				FetchCommand fetch = new FetchCommand(command);
				location = new ObjectLocation(fetch.getX(), fetch.getY(), fetch.getHeading(), fetch.getRadius());				
			}
			
		}
		else
		{
			logger.logReceived("null");
		}
		
		
		try {
			dis.close();
			dos.close();
			btc.close();
			Thread.sleep(100); // wait for data to drain
			
		} catch (Exception e) {
			logBluetooth("Error closing", logger);
			return null;
		}
		
		return location;
		
	}
	private static void logBluetooth(String line, DataLogger logger)
	{
		String log = "[Bluetooth] " + line;
		logger.writeLine(log);
	}
}

