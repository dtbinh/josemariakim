package r2_pc;
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
    private final int tooCloseThreshold = 8; // cm
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
		gripper.rotateTo(-50);
    }

    private void releaseGripArm()
    {
		gripper.rotateTo(0);
    }

    private void lowerGripArm()
    {
		gripper.rotateTo(-15);
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
    		objLoc = BTReceive.WaitAndReceiveObjectLocation(logger);
    		if(objLoc == null)
    			logLine("There were errors receiving the object location");
    	}
           	    	
    	// Convert robot #1 position to location for robot #2
    	Pose robot2pose = objLoc.GetRobot2Pose();
    	x_loc = Math.round(robot2pose.getX());
    	y_loc = Math.round(robot2pose.getY());
    	head = Math.round(robot2pose.getHeading());   
    	
    	// Display robot #2 pose
    	String msg = x_loc + "," + y_loc + "," + head;
        LCD.drawString(msg, 0, 7);	    
        
        String line = "Get object at: x = ";
        line += x_loc;
        line += " , y = ";
        line += " , heading = ";
        line += head;
        
        logLine(line);
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
    	ObjectLocation homeLoc = null;
    	
    	while(homeLoc == null)
    	{
    		homeLoc = BTReceive.WaitAndReceiveHomeLocation(logger);
    		if(homeLoc == null)
    			logLine("There were errors receiving the home location");
    	}
           	    	
    	// Convert robot #1 position to location for robot #2
    	Pose pose = homeLoc.GetRobot1Pose();
    	x_loc = Math.round(pose.getX());
    	y_loc = Math.round(pose.getY());
    	
    	// Display robot #2 pose
    	String msg = x_loc + "," + y_loc;
        LCD.drawString(msg, 0, 7);
        
		goTo(x_loc, y_loc, true);
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
    	int distance;
    	int tooCloseCount;
    	
        while (true)
        {

        	suppress();	
        	// Wait location of object, then moves toward object
        	WaitForObjLocation();
        	MoveToOjbLocation();
            release();		   

            // Let find object behavior get close to object
        	tooCloseCount = 15;
            while ( tooCloseCount > 0 )
            {
                distance = us.getDistance();
                drawInt(distance);
                if (distance < tooCloseThreshold) tooCloseCount--;
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


