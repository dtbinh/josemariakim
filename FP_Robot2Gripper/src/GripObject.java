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
           
    public GripObject( String name, int LCDrow, Behavior b, UltrasonicSensor sensor, Motor grip)
    {
    	super(name, LCDrow, b);
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
		gripper.rotateTo(5);
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
            forward(60,60);
            delay(300);
            
            // Stop and grip object
            stop();
            lowerGripArm();
            drawString("s");
            delay(1000); 
            
            // Turn left
            forward(60,0);
            delay(1000); 
            
            // Move object forward
            forward(60,60);
            delay(2000); 
            
            // Stop and lift grip arm
            stop();
            liftGripArm();
            delay(500); 

            // Move backward
            backward(60,60);
            delay(1000);
            
            // Turn right
            forward(0,60);
            delay(2000); 
            
            release();		   

        }
    }
}


