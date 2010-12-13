import java.io.IOException;

import lejos.nxt.*;
import lejos.nxt.comm.*;
import java.io.*;
import javax.bluetooth.*;

/**
 * 
 * Test of NXT to NXT Bluetooth comms.
 * 
 * Connects to another NXT, sends 100 ints, and receives the 
 * replies. Then closes the connection and shuts down.
 * 
 * Works with the BTReceive sample running on the slave NXT.
 * 
 * Change the name string to the name of your slave NXT, and make sure
 * it is in the known devices list of the master NXT. To do this, turn
 * on the slave NXT and make sure Bluetooth is on and the device
 * is visible. Use the Bluetooth menu on the slave for this. Then,
 * on the master, select the Bluetooth menu and then select Search.
 * The name of the slave NXT should appear. Select Add to add
 * it to the known devices of the master. You can check this has
 * been done by selecting Devices from the Bluetooth menu on the
 * master.
 * 
 * @author Lawrie Griffiths
 *
 */
public class BTConnectTest {
	public static void main(String[] args) throws Exception {
		String name1 = "Robot1";
		String name2 = "Robot2";
		String name3 = "NXT";
		String name = "";
		LCD.drawString("Connecting...", 0, 0);
		LCD.refresh();
		
		RemoteDevice btrd = Bluetooth.getKnownDevice(name1);
		if (btrd == null) {
			btrd = Bluetooth.getKnownDevice(name2);
		}
		if (btrd == null) {
			btrd = Bluetooth.getKnownDevice(name3);
		}
		
		if (btrd == null) {
			
			LCD.clear();
			LCD.drawString("No such device", 0, 0);
			LCD.refresh();
			Thread.sleep(2000);
			System.exit(1);
		}
		
		BTConnection btc = Bluetooth.connect(btrd);
		
		if (btc == null) {
			LCD.clear();
			LCD.drawString("Connect fail", 0, 0);
			LCD.refresh();
			Thread.sleep(2000);
			System.exit(1);
		}
		
		LCD.clear();
		LCD.drawString("Connected", 0, 0);
		LCD.refresh();
		
		DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		FetchCommand fetchCommand = new FetchCommand(3, (float)25.46, (float)2.6, (float)6.75, 3); 
		String command = fetchCommand.Serialize();//"FETCH ID=3 X=25.46 Y=2.6 H=6.75 P=3\n";
		
			try {
				LCD.drawString("Sending command", 0, 0);
				LCD.refresh();
				dos.writeChars(command);
				dos.flush();			
			} catch (IOException ioe) {
				LCD.drawString("Write Exception", 0, 0);
				LCD.refresh();
			}
			
			try {
				String response = "";
				char c = dis.readChar();
				while(c!='\n'){
					response += c;
					c = dis.readChar();
				}
				LCD.drawString(response, 0, 0);
				LCD.refresh();
			} catch (IOException ioe) {
				LCD.drawString("Read Exception ", 0, 0);
				LCD.refresh();
			}
		
		
		try {
			LCD.drawString("Closing...    ", 0, 0);
			LCD.refresh();
			dis.close();
			dos.close();
			btc.close();
		} catch (IOException ioe) {
			LCD.drawString("Close Exception", 0, 0);
			LCD.refresh();
		}
		
		LCD.clear();
		LCD.drawString("Finished",3, 4);
		LCD.refresh();
		Thread.sleep(2000);
	}
}
