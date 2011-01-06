package r2_pc;
import communication.DataLogger;
import communication.Utils;

import lejos.nxt.*;

public class Robot2PC 
{
	private static String filePrefix = "robot2PCLog";

    public static void main(String [] args)
    {    	
    	//GripObject go;
    	SeqStrategy ss;
    	FindObject fo;
		Motor gripper = Motor.A;
		UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
		
		//Create logger
 	    String fileName;
		try {
			fileName = Utils.getFileName(filePrefix );
		} catch (InterruptedException e) {
			fileName = filePrefix + "99"; //in case of an error
		}
 	    DataLogger logger = new DataLogger(fileName);
 	    
		Car.InitCar();
    	
        LCD.drawString("Robot 2 PCCom",0,0);
        LCD.refresh();
    	
        fo = new FindObject("Find", 3, null, logger);
    	//go = new GripObject ("Avoid", 4, fo, us, gripper);
    	ss = new SeqStrategy ("Sequence", 4, fo, us, gripper, logger);
    	//ss.WaitForObjLocation(); // Testing ObjectLocation class

		while (Button.ENTER.isPressed());	
    	LCD.drawString("Press ENTER to  ", 0, 0);
		LCD.drawString("calibrate       ", 0, 1);
		while (!Button.ENTER.isPressed())
			fo.Calibrate(); 
		
    	ss.liftGripArm();

        while (Button.ENTER.isPressed());	
    	LCD.drawString("Press ENTER to  ", 0, 0);
		LCD.drawString("start       ", 0, 1);
		while (!Button.ENTER.isPressed()); 

		LCD.clearDisplay();
        LCD.drawString("Robot 2 NavGrip",0,0);
    	
        ss.start();
		fo.start();

    	while (! Button.ESCAPE.isPressed())
    	{
    		ss.reportState();
    		fo.reportState();
    	}

    	logger.close();
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

    }    
}
