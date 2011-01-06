package pcProgram;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

import communication.DeliverCommand;
import communication.Utils;

import lejos.pc.comm.*;


public class PCProgram {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		while(true)
		{
			Scanner scanner = new Scanner(System.in);
			System.out.print("\nEnter coordinate X: ");
			float x = scanner.nextFloat();
			System.out.print("\nEnter coordinate Y: ");
			float y = scanner.nextFloat();
			
			System.out.println("\nSending Robot to position (" + x + ", " + y + ")" );
			sendPosition(x, y);	
		}
	}

	public static void sendPosition(float x, float y) {
		
		String[] names = new String[4];
		names[0] = "Robot2";
		names[1] = "Robot1";
		names[2] = "maria";
		names[3] = "NXT";
		
		NXTInfo nxtInfo = null;
		
		for(int i = 0; i < names.length; i++)
		{
			try {
				NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
				NXTInfo[] nxtInfos = nxtComm.search(names[i],NXTCommFactory.BLUETOOTH);
				
				if(nxtInfos.length > 0)
				{
					nxtInfo = nxtInfos[0];	
					break;
				}
				
			} catch (NXTCommException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
		if(nxtInfo != null)
		{
			NXTConnector conn = new NXTConnector();
			// Connect to any NXT over Bluetooth
			boolean connected = conn.connectTo(nxtInfo, NXTComm.PACKET);
			
			if (!connected) {
				System.err.println("Failed to connect to " + nxtInfo.name);
				System.exit(1);
			}
			
			DataOutputStream dos = conn.getDataOut();
			DataInputStream dis = conn.getDataIn();
		
		
			DeliverCommand deliverCommand = new DeliverCommand(x, y);
			String command = deliverCommand.Serialize();
			
			try {
				Utils.send(dos, command);
				System.out.println(">>" + command);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				String response = Utils.receive(dis);
				System.out.println("<<" + response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

			try {
				dis.close();
				dos.close();
				conn.close();
			} catch (IOException ioe) {
				System.out.println("IOException closing connection:");
				System.out.println(ioe.getMessage());
			}

		}
		
		else
		{
			System.out.println("Couldn't find any NXT");
		}
	}

}
