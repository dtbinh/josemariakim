package r2_pc;
import communication.DataLogger;

import lejos.nxt.*;
/**
 * A behavior that uses an ultrasonic sensor on port S1
 * to make a car avoid objects in front of the car. 
 *  
 * @author  Kim Bjerge
 * @version 15.12.10
 */
public class GripObject extends Behavior 
{    
    private UltrasonicSensor us ;
    private final int tooCloseThreshold = 10; // cm
    Motor gripper;
           
    public GripObject( String name, int LCDrow, Behavior b, UltrasonicSensor sensor, Motor grip, DataLogger logger)
    {
    	super(name, LCDrow, b, logger);
        us = sensor; 
        gripper = grip;
    }
   
    public void liftGripArm()
    {
		gripper.rotateTo(30);
    }

    private void releaseGripArm()
    {
		gripper.rotateTo(0);
    }

    private void lowerGripArm()
    {
		gripper.rotateTo(0);
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
            
            // Moves a bit forward
            forward();
            delay(300);
            
            // Stop and grip object
            stop();
            lowerGripArm();
            drawString("s");
            delay(1000); 
            
            // Turn left
            rotate(90);
            delay(500); 
            
            // Move object forward
            forward();
            delay(2000); 
            
            // Stop and lift grip arm
            stop();
            liftGripArm();
            delay(500); 

            // Move backward
            backward();
            delay(1000);
            
            // Turn right
            rotate(-180);
            delay(1000); 
            
            release();		   

        }
    }
}


