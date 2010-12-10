import lejos.nxt.*;
import lejos.nxt.addon.ColorSensor;
/*
* Behavior: Robot 1 finder for final project 
*  
* @author  Kim Bjerge
* @version 7.12.10
*/
public class RobotFinder2 
{

	public static void main(String [] args)
    {    	
    	MoveForward mf;
    	TurnAtBoarder tb;
    	AvoidObject ao;
    	SenseIdentifyObject sio;
    	PlaySound ps;
    	
    	SyncUltrasonicSensor us =  new SyncUltrasonicSensor(SensorPort.S1);
 	    LightSensor ls = new LightSensor(SensorPort.S2);
 	    ColorSensor cs = new ColorSensor(SensorPort.S3);
	    ls.setFloodlight(true);	
	    Car.InitCar();
	
        LCD.drawString("Robot 1 finder",0,0);
        LCD.refresh();
    	
        // Subsumption architecture with levels of behavioral competences
        mf = new MoveForward  ("Forward ", 1, null);
    	tb = new TurnAtBoarder("Turn    ", 2, mf, ls);
    	ao = new AvoidObject  ("Avoid   ", 3, tb, us);
    	sio = new SenseIdentifyObject
    	                      ("Identify", 4, ao, cs, us);
        ps = new PlaySound    ("Play    ", 5, null, us);
    	
    	mf.start();
    	tb.start();
       	ao.start();
       	sio.start();
       	ps.start();

    	while (! Button.ESCAPE.isPressed())
    	{
    		mf.reportState();
    		tb.reportState();
    		ao.reportState();
    		sio.reportState();
    		ps.reportState();
    	}
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

    }    
}
