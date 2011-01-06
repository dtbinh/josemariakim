import java.io.IOException;
import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import javax.bluetooth.*;

import communication.DataLogger;
import communication.FetchCommand;
import communication.Utils;

/**
 * 
 * Test of NXT to NXT Bluetooth comms.
 * 
 *
 */
public class BTConnectTest {
	
	private static String filePrefix = "senderLog";
	public static void main(String[] args) throws Exception {
		
		String fileName = Utils.getFileName(filePrefix);
        DataLogger logger = new DataLogger(fileName);       
        
        logger.writeLine("Connecting...");
        BTConnection btc = connectBluetooth(logger);
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		for(int i = 0; i<10;i++)
		{
			FetchCommand fetchCommand = new FetchCommand(3*i, (float)(25.46 * (float)i/10.0), (float)2.6, (float)6.75, i*2); 
			String command = fetchCommand.Serialize();//"FETCH ID=3 X=25.46 Y=2.6 H=6.75 P=3\n";
			
			try {
				Utils.writeUpperLineToLCD("Sending command");
				Utils.send(dos, command);
				logger.logSent(command);
			} catch (IOException ioe) {
				Utils.writeUpperLineToLCD("Write Exception");
				
			}
			
			try {
				String response = Utils.receive(dis);
				logger.logReceived(response);
				Utils.writeUpperLineToLCD(response);
			} catch (IOException ioe) {
				Utils.writeUpperLineToLCD("Read Exception");
			}
			

		}
				
		
		try {
			Utils.writeUpperLineToLCD("Closing...");
			dis.close();
			dos.close();
			btc.close();
		} catch (IOException ioe) {
			Utils.writeUpperLineToLCD("Close Exception");
		}
		
		logger.close();
		
		LCD.clear();
		LCD.drawString("Finished",3, 4);
		LCD.refresh();
		
		Thread.sleep(2000);
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
			
			Thread.sleep(2000);
			System.exit(1);
		}
		else
		{
			name = names[i-1];
			
			BTConnection btc = Bluetooth.connect(btrd);
			
			if (btc == null) {
				Utils.writeUpperLineToLCD("Connect failed");
				Thread.sleep(2000);
				System.exit(1);
			}
			
			String line = "Connected to " + name;
			Utils.writeUpperLineToLCD(line);
			
			if(logger != null)
				logger.writeLine(line);
			
			return btc;
		}
		
		//that will never happen, as it will have either returned the connection, or exit the system (but the compiler does not see that!
		return null; 
	}
}
