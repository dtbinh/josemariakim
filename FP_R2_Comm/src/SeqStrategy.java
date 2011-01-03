import communication.DataLogger;

import lejos.nxt.*;
import lejos.robotics.Pose;
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
           
    public SeqStrategy( String name, int LCDrow, Behavior b, UltrasonicSensor sensor, Motor grip, DataLogger logger)
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
    
	private boolean WaitMoving()
	{
		while(isMoving())
		{
			Thread.yield();
		}
		return false;
	}
   
    public void WaitForObjLocation()
    {
    	ObjectLocation objLoc = null;
    	
    	while(objLoc == null)
    	{
    		objLoc = BTReceive.WaitAndReceiveLocation(logger);
    		if(objLoc == null)
    			logLine("There were errors receiving the object location");
    	}
           	
    	// Test of object location class
    	//ObjectLocation objLoc = new ObjectLocation(702, -156, 45, 20);
    	//ObjectLocation objLoc = new ObjectLocation(702, -156, 135, 20);
    	//ObjectLocation objLoc = new ObjectLocation(702, -156, -45, 20);
    	//ObjectLocation objLoc = new ObjectLocation(702, -156, -135, 20);
    	
    	// Convert robot #1 position to location for robot #2
    	Pose robot2pose = objLoc.GetRobot2Pose();
    	x_loc = Math.round(robot2pose.getX());
    	y_loc = Math.round(robot2pose.getY());
    	head = Math.round(robot2pose.getHeading());   
    	
    	// Display robot #2 pose
    	String msg = x_loc + "," + y_loc + "," + head;
        LCD.drawString(msg, 0, 7);	    	    	
    	/*
    	x_loc = 602;
    	y_loc = -260;
    	head = -15;
    	*/
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
        delay(400);
        
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


