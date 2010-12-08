import lejos.nxt.*;
/**
 * Behavior: Avoid object
 *  
 * @author  Kim Bjerge
 * @version 7.12.10
 */
public class AvoidObject extends Behavior 
{    
    private UltrasonicSensor us ;
           
    public AvoidObject( String name, int LCDrow, Behavior b, UltrasonicSensor uSensor)
    {
    	super(name, LCDrow, b);
        us = uSensor;
    }
    
    public void run() 
    {
        int distance;
        
        while (true)
        {
	    	
            distance = us.getDistance();
            while ( distance > tooCloseThreshold )
            {
                drawInt(distance);
       		    delay(10);
            }

            // When robot is too close an object
            // it backs off turn and moves forward again 
            // to avoid crashing the object.
            suppress();
		    
            stop();
            drawString("s");
            delay(1000);

            backward();
            drawString("b");
            delay(1000);
		    
            rotateTo(45);
            drawString("r");
            delay(500); 
		    
            stop();
            drawString("s");
            delay(500);
		    
            drawString(" ");
		    
            release();		   
       }
    }
}


