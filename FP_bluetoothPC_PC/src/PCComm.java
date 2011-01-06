import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import communication.DeliverCommand;
import communication.Utils;

import lejos.pc.comm.*;


public class PCComm {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		NXTInfo nxtInfo = null;
		try {
			NXTComm nxtComm = NXTCommFactory.createNXTComm(NXTCommFactory.BLUETOOTH);
			NXTInfo[] nxtInfos = nxtComm.search("maria",NXTCommFactory.BLUETOOTH);
			
			if(nxtInfos.length > 0)
			{
				nxtInfo = nxtInfos[0];				
			}
			
		} catch (NXTCommException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			
			for(int i = 0; i< 10; i++)
			{
				DeliverCommand deliverCommand = new DeliverCommand(i, i*2);
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
