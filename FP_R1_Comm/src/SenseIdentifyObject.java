import communication.DataLogger;

import lejos.nxt.*;
import lejos.nxt.addon.ColorSensor;
import lejos.robotics.Pose;

/**
 * Behavior: Sense and identify object
 *  
 * @author  Kim Bjerge
 * @version 7.12.10
 */
public class SenseIdentifyObject extends Behavior 
{	    
	private int color_val = 9; // Red
    private final int maxMarks = 10;
    private final float dist_to_obj = 210; // Distance to object (radius)
    private int idx = 0;
    private Pose[] landmarks;
	private SyncUltrasonicSensor us;
	private ColorSensor cs;
	private TurnAtBoarder turnAtBoarder;
	private boolean stopped = false;
  
    public SenseIdentifyObject( String name, int LCDrow, Behavior b, ColorSensor sColor, SyncUltrasonicSensor sSonic, TurnAtBoarder tab, DataLogger logger)
    {
    	super(name, LCDrow, b, logger);
    	this.cs = sColor;
    	this.us = sSonic;
    	this.landmarks = new Pose[maxMarks];
    	this.turnAtBoarder = tab;
    }
    
    public void setColor()
    {
    	String msg;
    	color_val = cs.getColorNumber(); 
    	msg = "Color = " + color_val + "   ";
        LCD.drawString(msg, 0, 7);
    }
    
    public void Pause(boolean stop)
    {
    	stopped = stop;
    }
    
    public void ResetPose()
    {
    	Car.setPose(0,0,0);
    }
    
    private void dispPose(Pose p)
    {
    	String msg;
    	int x, y, head;
		x = (int)p.getX();
		y = (int)p.getY();
		head = (int)p.getHeading();
    	msg = x + "," + y + "," + head;
        LCD.drawString(msg, 0, 7);	
        
        String line = "Object found: x = ";
        line += p.getX();
        line += ", y = ";
        line += p.getY();
        line += ", heading = ";
        line += p.getHeading();
        
        logLine(line);
    }
    
    
    private void addPose(Pose p)
    {
    	// Add new landmark found
    	if (idx < maxMarks)
    	{
    		landmarks[idx++] = p;
    		dispPose(p);
    	}
    }
  
    private void searchObject()
    {
        int distance = us.getDistance();
        if (distance < foundThreshold)
        {
        	// Stop robot until released by user
  			stop();
        	stopped = true;
        	
			// Save location 
			addPose(Car.getPose());
			//send command to Robot2 to come and get the object
			boolean success = BTSend.sendPose(Car.getPose(), color_val, dist_to_obj, logger); // 
			
			if(success)
			{
				Sound.playTone(800, 2000, 50); // High tone

				// Backup giving space for robot #2 to pick up object
				backward();
				delay(500);
			}
			else 
				Sound.playTone(100, 2000, 50); // Low tone
					
			// Stop and await for object to be removed
			stop();
			delay(15000);
			
			while (stopped)
			{
				delay(1000);
			}
        } 
    }
    
    private void waitMoving()
    {
        while (isMoving())
        {
        	searchObject();
        	delay(5); 
        }    	
    }   
    
    public void run()
    {
    
    	delay(500); 	
    	while (true)
    	{     		
  		   delay(5);
  		   
  		   if (cs.getColorNumber() == color_val)
      	   {
  	      	   // When color area is detected robot
  	      	   // looks for object within distance threshold.
  	      	   // If object is detected the location is recorded.
       		   suppress();
               stop();
       		   searchObject();
       		   
       		   Car.setSpeed(75);
               rotate(30, true);
               drawString("l");
               waitMoving();

               rotate(-60, true);
               drawString("r");
               waitMoving();

               rotate(30, true);
               drawString("l");
               waitMoving();
       		   Car.setDefaultSpeed();
       		   
    		   release(); 
    		    
               drawString(" ");

               for (int i = 0; i < 100; i++)
               {
                   delay(5);
           		   searchObject();       	   
               }
      	   }	   
    	}
    }   
}
