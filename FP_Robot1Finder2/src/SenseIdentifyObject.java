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
	private final int color_val = 9; // Red
    private final int maxMarks = 10;
    private int idx = 0;
    private Pose[] landmarks;
	private SyncUltrasonicSensor us;
	private ColorSensor cs;
  
    public SenseIdentifyObject( String name, int LCDrow, Behavior b, ColorSensor sColor, SyncUltrasonicSensor sSonic)
    {
    	super(name, LCDrow, b);
    	this.cs = sColor;
    	this.us = sSonic;
    	this.landmarks = new Pose[maxMarks];
    }
    	
    public void dispPose(Pose p)
    {
    	String msg;
    	int x, y, head;
		x = (int)p.getX();
		y = (int)p.getY();
		head = (int)p.getHeading();
    	msg = x + "," + y + "," + head;
        LCD.drawString(msg, 0, 6);	    	    	
    }
    
    public void addPose(Pose p)
    {
    	// Add new landmark found
    	if (idx < maxMarks)
    	{
    		landmarks[idx++] = p;
    		dispPose(p);
    	}
    }
    
    public void run()
    {
        int distance;
    	
    	while (true)
    	{     		
      	   while (cs.getColorNumber() != color_val)
      	   {
     		   delay(100);
     		   //dispPose(Car.getPose());
      	   }
      	   
      	   // When color area is detected robot
      	   // looks for object within distance threshold.
      	   // If object is detected the location is recorded.
           distance = us.getDistance();
           if (distance < foundThreshold)
           {
	      	    suppress();
	    	    stop();
	            // Save location 
	            addPose(Car.getPose());
                Sound.playTone(800, 2000, 50);
               	delay(60000);
                release(); 
           }
           delay(100);
 		   //dispPose(Car.getPose());
    	}
    }   
}
