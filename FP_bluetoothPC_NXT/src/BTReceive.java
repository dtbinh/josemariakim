import lejos.nxt.comm.*;
import java.io.*;

import communication.ACKCommand;
import communication.Command;
import communication.DataLogger;
import communication.DeliverCommand;
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
	
	public enum Location {OBJECT, HOME};

	private static ObjectLocation WaitAndReceiveLocation(DataLogger logger, Location locationType) 
	{
		BTConnection btc = waitForConnection(logger);
		
        
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		ObjectLocation location = receiveLocation(logger, locationType, dis, dos);
		
		closeAllChannels(logger, btc, dis, dos);
		
		return location;
		
	}

	public static void closeAllChannels(DataLogger logger, BTConnection btc,
			DataInputStream dis, DataOutputStream dos) {
		try {
			dis.close();
			dos.close();
			btc.close();
			Thread.sleep(100); // wait for data to drain
			
		} catch (Exception e) {
			Utils.logBluetooth("Error closing", logger);
		}
	}

	public static ObjectLocation receiveLocation(DataLogger logger,
			Location locationType, DataInputStream dis, DataOutputStream dos) {
		ObjectLocation location = null;
		String commandString;
		try {
			commandString = Utils.receive(dis);
			
		} catch (IOException e) {
			Utils.logBluetooth("Error receiving command", logger);
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
				Utils.logBluetooth("Error sending ACK", logger);
				return null;
			}
			logger.logSent(ack.Serialize());
			
			if(locationType == Location.OBJECT && command.getCommandID().equals(FetchCommand.ID))
			{
				FetchCommand fetch = new FetchCommand(command);
				location = new ObjectLocation(fetch.getX(), fetch.getY(), fetch.getHeading(), fetch.getRadius());				
			}
			else if(locationType == Location.HOME && command.getCommandID().equals(DeliverCommand.ID))
			{
				DeliverCommand deliver = new DeliverCommand(command);
				location = new ObjectLocation(deliver.getX(), deliver.getY(),0, 0);
			}
			
		}
		else
		{
			logger.logReceived("null");
		}
		return location;
	}

	public static BTConnection waitForConnection(DataLogger logger) {
		String connected = "Connected";
        String waiting = "Waiting...";
        
        
        
        Utils.writeUpperLineToLCD(waiting);
		Utils.logBluetooth(waiting, logger);

        BTConnection btc = Bluetooth.waitForConnection();
        
        Utils.writeUpperLineToLCD(connected);
        
        Utils.logBluetooth(connected, logger);
		return btc;
	}

	public static ObjectLocation WaitAndReceiveObjectLocation(DataLogger logger)
	{
		return WaitAndReceiveLocation(logger, Location.OBJECT);
	}
	
	public static ObjectLocation WaitAndReceiveHomeLocation(DataLogger logger)
	{
		return WaitAndReceiveLocation(logger, Location.HOME);
	}
	
	
}

