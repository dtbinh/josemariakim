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
	private UltrasonicSensor us;
	private ColorSensor cs;
  
    public SenseIdentifyObject( String name, int LCDrow, Behavior b, ColorSensor sColor, UltrasonicSensor sSonic)
    {
    	super(name, LCDrow, b);
    	cs = sColor;
    	us = sSonic;
    	landmarks = new Pose[maxMarks];
    }
    	
    public void addPose(Pose p)
    {
    	String msg;
    	// Add new landmark found
    	if (idx < maxMarks)
    	{
    		landmarks[idx++] = p;
	    	msg =   p.getX() + "," + p.getY() + "," + p.getHeading();
	    	drawString(msg);
    	}
    }
    
    public void run()
    {
        int distance;
    	
    	while (true)
    	{     		
      	   while (cs.getColorNumber() != color_val)
      	   {
     		   delay(10);
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
	            delay(10000);
	            release(); 
           }
    	}
    }   
}
