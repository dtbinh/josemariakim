import java.io.DataInputStream;
import java.io.DataOutputStream;

import javax.microedition.location.Location;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.robotics.Pose;

import communication.DataLogger;
import communication.Utils;


public class NXTComm {

	private static final String filePrefix = "pcComm_NXT";

	
	public static void main(String[] args) 
	{
		       
        //Create logger
 	    String fileName;
		try {
			fileName = Utils.getFileName(filePrefix );
		} catch (InterruptedException e) {
			fileName = filePrefix + "99"; //in case of an error
		}
		
        DataLogger logger = new DataLogger(fileName);
		
        BTConnection btc = BTReceive.waitForConnection(logger);
        DataInputStream dis = btc.openDataInputStream();
		DataOutputStream dos = btc.openDataOutputStream();
		
		  		
		for(int i = 0; i<10 ; i++)
    	{
			
    		ObjectLocation location = BTReceive.receiveLocation(logger, BTReceive.Location.HOME , dis, dos);
    		
    		String msg;
        	int x, y;
        	Pose pose = location.GetRobot1Pose();
        	
    		x = (int) pose.getX();
    		y = (int) pose.getY();
    		msg = x + "," + y ;
            LCD.drawString(msg, 0, 7);	
            
            String line = "Location received: x = ";
            line += x;
            line += ", y = ";
            line += y;
            
            logger.writeLine(line);
    		
    	}

		
		BTReceive.closeAllChannels(logger, btc, dis, dos);
		
		while (! Button.ESCAPE.isPressed()) {}
		
    	logger.close();
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

	}

}
