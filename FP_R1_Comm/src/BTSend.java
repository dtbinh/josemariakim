import java.io.IOException;
import lejos.nxt.comm.*;
import lejos.robotics.Pose;
import java.io.*;
import javax.bluetooth.*;

import communication.DataLogger;
import communication.FetchCommand;
import communication.Utils;


public class BTSend {
	
	public static boolean sendPose(Pose pose, int id, float radius, DataLogger logger) 
	{	
//		String fileName = Utils.getFileName(filePrefix);
//        DataLogger logger = new DataLogger(fileName);       
        
		logBluetooth("Connecting...", logger);
        BTConnection btc;
		try {
			btc = connectBluetooth(logger);
		} catch (InterruptedException e) {
			return false;
		}
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		
		FetchCommand fetchCommand = new FetchCommand(id, pose.getX(), pose.getY(), pose.getHeading(), radius);
		String command = fetchCommand.Serialize();
		
		try {
			Utils.writeUpperLineToLCD("Sending command");
			Utils.send(dos, command);
			logger.logSent(command);
		} catch (IOException ioe) {
			Utils.writeUpperLineToLCD("Write Exception");
			logBluetooth("Write Exception", logger);
			
		}
		
		String response = "";
		try {
			response = Utils.receive(dis);
			logger.logReceived(response);
			Utils.writeUpperLineToLCD(response);
		} catch (IOException ioe) {
			Utils.writeUpperLineToLCD("Read Exception");
			logBluetooth("Read Exception", logger);
		}
		
		try {
			dis.close();
			dos.close();
			btc.close();
		} catch (IOException ioe) {
			Utils.writeUpperLineToLCD("Close Exception");
			logBluetooth("Close Exception", logger);
		}
		
		//return true if the response received is an ACK
		return response.indexOf("ACK") > -1;
			
	}

	

	private static BTConnection connectBluetooth(DataLogger logger) throws InterruptedException {
		
		String[] names = new String[4];
		names[0] = "Robot1";
		names[1] = "Robot2";
		names[2] = "NXT";
		names[3] = "maria";
		
		String name = "";
		
        Utils.writeUpperLineToLCD("Connecting...");
		
		RemoteDevice btrd = null;
		
		int i = 0;
		while(i < names.length && btrd == null)
		{
			btrd = Bluetooth.getKnownDevice(names[i]);
			i++;
		}
		
		if (btrd == null) {
			Utils.writeUpperLineToLCD("No such device");
			logBluetooth("Device not found", logger);
			Thread.sleep(2000);
			return null;
		}
		else
		{
			name = names[i-1];
			
			BTConnection btc = Bluetooth.connect(btrd);
			
			if (btc == null) {
				Utils.writeUpperLineToLCD("Connect failed");
				logBluetooth("Connection failed", logger);
				Thread.sleep(2000);
				return null;
			}
			
			String line = "Connected to " + name;
			Utils.writeUpperLineToLCD(line);
			
			if(logger != null)
				logBluetooth(line, logger);
			
			return btc;
		} 
	}
	
	private static void logBluetooth(String line, DataLogger logger)
	{
		String log = "[Bluetooth] " + line;
		logger.writeLine(log);
	}
}
