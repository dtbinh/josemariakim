/**
 * Behavior: Avoid object
 *  
 * @author  Kim Bjerge
 * @version 7.12.10
 */
public class AvoidObject extends Behavior 
{    
    private SyncUltrasonicSensor us ;
           
    public AvoidObject( String name, int LCDrow, Behavior b, SyncUltrasonicSensor uSensor)
    {
    	super(name, LCDrow, b);
        this.us = uSensor;
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
       		    delay(5);
                distance = us.getDistance();
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
            delay(500);
		    
            rotate(45);
            drawString("r");
            delay(100); 
		    
            stop();
            drawString("s");
            delay(500);
		    
            drawString(" ");
		    
            release();		   
       }
    }
}


