import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;
import lejos.robotics.navigation.*;

/**
 * Test the Robot 2 construction.
 * 
 * Sequential test of the construction:
 * 
 *   1. Moves into a position recorded by robot 1
 *   2. When position is reached the Ultrasonic sensor 
 *      is used to measure the distance to the object
 *   3. The gripper arm is move in position to fetch object
 *   4. Robot 2 returns with the object to the initial position
 * 
 * Press ENTER steps to the next point in the sequence of test  
 *
 * @author Kim Bjerge
 * @version 13.12.10
 * 
 */
public class Robot2Test
{
	// Wheel diameter, track width connected to motor B, C
	static TachoPilot pilot = new TachoPilot(56f,112f,Motor.B, Motor.C,false);
	static SimpleNavigator robot;

	public static void main(String[] args ) throws Exception
	{
		boolean exit = false;
		int distance = 0;
		int light = 0;
		// Gripper arm attached to Motor A
		Motor gripper = Motor.A;
		// Ultrasonic sensor attached to input port S1
		UltrasonicSensor us =  new UltrasonicSensor(SensorPort.S1);
		// Light sensors attached on the gripper arm
		RCXLightSensor leftLight;
		RCXLightSensor rightLight;
		
		leftLight = new RCXLightSensor(SensorPort.S2);
		leftLight.setFloodlight(true);
		rightLight = new RCXLightSensor(SensorPort.S3);
		rightLight.setFloodlight(true);
		robot = new SimpleNavigator(pilot);
		
		while (!exit)
		{
			// Lift gripper arm
			gripper.rotateTo(35);
			
			// Wait for user to press ENTER
			LCD.drawString("Robot 2 Test    ", 0, 0);
			Button.ENTER.waitForPressAndRelease();
			
			// Set travel speed
			robot.setMoveSpeed(250);
			robot.setTurnSpeed(100);
			robot.setPose(0, 0, 0);
			
			// Move to position with heading, Located by pose of Robot 1
			robot.goTo(278, -194, true);
			if (WaitMoving()) return;
			showCount(3);
			robot.rotateTo(-1, true);
			if (WaitMoving()) return;
			showCount(3);
			robot.stop();
						
			// Return to home position after object detected
			while (Button.ENTER.isPressed());
			LCD.drawString("Press ENTER to  ", 0, 0);
			LCD.drawString("grip object/ret.", 0, 1);
			LCD.drawString("Distance :      ", 0, 2); 
			LCD.drawString("Left     :      ", 0, 3); 
			LCD.drawString("Right    :      ", 0, 4); 
			while (!Button.ENTER.isPressed())	
			{
				// Displays distance to object
				distance = us.getDistance();
				LCD.drawInt(distance,4,12,2);
				light = leftLight.getLightValue();
				LCD.drawInt(light,4,12,3);
				light = rightLight.getLightValue();
				LCD.drawInt(light,4,12,4);
			}
			Button.ENTER.waitForPressAndRelease();
			
			// Lower gripper
			gripper.rotateTo(10);
			// Return to home position
			robot.goTo(0, 0, true);
			if (WaitMoving()) return;
			showCount(3);
			
			LCD.drawString("Press ENTER to  ", 0, 0);
			LCD.drawString("lift gripper    ", 0, 1);
			while (!Button.ENTER.isPressed());	
			
			// Lift gripper
			gripper.rotateTo(35);
			
			LCD.drawString("Press ENTER to  ", 0, 0);
			LCD.drawString("restart robot 2 ", 0, 1);
			while (!Button.ENTER.isPressed() &&
				   !Button.ESCAPE.isPressed());	
			
			gripper.rotateTo(0);
			if (Button.ESCAPE.isPressed())
			{
				// Lower gripper
				exit = true;
			}
		}
	}
	
	public static boolean WaitMoving()
	{
		while(robot.isMoving())
		{
			Thread.yield();
			if (Button.ENTER.isPressed()) 
				return true;
		}
		return false;
	}

	public static void showCount(int i)
	{
		robot.updatePose();
		LCD.drawInt((int)robot.getX(),0,i);
		LCD.drawInt((int)robot.getY(),7,i);
	}
}

