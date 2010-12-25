import lejos.nxt.*;
/**
 * A behavior that uses a sequential strategy moving to the position
 * of the object location found by robot #1
 *  
 * @author  Kim Bjerge
 * @version 25.12.10
 */
public class SeqStrategy extends Behavior 
{    
    private UltrasonicSensor us ;
    private final int tooCloseThreshold = 10; // cm
    private int x_loc, y_loc, head;
    Motor gripper;
           
    public SeqStrategy( String name, int LCDrow, Behavior b, UltrasonicSensor sensor, Motor grip)
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
		gripper.rotateTo(0);
    }
    
	private boolean WaitMoving()
	{
		while(isMoving())
		{
			Thread.yield();
		}
		return false;
	}
   
    private void WaitForObjLocation()
    {
    	x_loc = 602;
    	y_loc = -260;
    	head = -15;	
    }

    private void MoveToOjbLocation()
    {
		// Move to position with heading, Located by pose of Robot 1
        drawString("l");
		goTo(x_loc, y_loc, true);   
		WaitMoving();
        drawString("h");
		rotateTo(head, true);
		WaitMoving();
		stop();
   }

    private void GripObject()
    {
        stop();
        
        // Moves a bit forward
        drawString("f");
        forward();
        delay(300);
        
        // Stop and grip object
        stop();
        lowerGripArm();
        drawString("s");
        delay(500); 
        
    }
    
    private void BringObjectHome()
    {
		// Return to home position
		goTo(0, 0, true);
		WaitMoving();
        liftGripArm();
        delay(2500); // Object must be manual removed 
        rotateTo(0, true);
		WaitMoving();   	
    }
 
	private void MoveObjectAway()
	{
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
		stop();	
	}

    
    public void run() 
    {
  	
        while (true)
        {

        	suppress();	
        	// Wait location of object, then moves toward object
        	WaitForObjLocation();
        	MoveToOjbLocation();
            release();		   

            // Let find object behavior get close to object
        	int distance = us.getDistance();
            while ( distance > tooCloseThreshold )
            {
                distance = us.getDistance();
                drawInt(distance);
            }
            
            suppress();	
            // Object is now very close to robot, then grip object             
        	GripObject();
        	// Bring object home and release it
        	BringObjectHome();
        	//MoveObjectAway();
            release();		   
        }
    }
}


