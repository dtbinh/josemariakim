import lejos.nxt.*;
/**
 * A behavior that uses an ultrasonic sensor on port S1
 * to make a car avoid objects in front of the car. 
 *  
 * @author  Ole Caprani
 * @version 23.08.08
 */
public class AvoidFront extends Behavior 
{    
    private UltrasonicSensor us ;
    private final int tooCloseThreshold = 20; // cm
           
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
		    
            backward(70,70);
            drawString("b");
            delay(1000);
		    
            forward(0,80);
            drawString("f");
            delay(800); 
		    
            stop();
            drawString("s");
            delay(500);
		    
            drawString(" ");
		    
            release();		   
       }
    }
}


