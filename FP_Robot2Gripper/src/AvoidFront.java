import lejos.nxt.*;
/**
 * A behavior that uses an ultrasonic sensor on port S1
 * to make a car avoid objects in front of the car. 
 *  
 * @author  Kim Bjerge
 * @version 15.12.10
 */
public class AvoidFront extends Behavior 
{    
    private UltrasonicSensor us ;
    private final int tooCloseThreshold = 8; // cm
           
    public AvoidFront( String name, int LCDrow, Behavior b)
    {
    	super(name, LCDrow, b);
        us = new UltrasonicSensor(SensorPort.S1);   	
    }
   
    public void run() 
    {
        while (true)
        {
	    	
            int distance = us.getDistance();
            while ( distance > tooCloseThreshold )
            {
                distance = us.getDistance();
                drawInt(distance);
            }

            suppress();		    
            stop();
            drawString("s");
            delay(5000); 
            release();		   

        }
    }
}


