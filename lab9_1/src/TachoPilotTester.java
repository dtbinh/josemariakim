import lejos.nxt.*;
import lejos.robotics.navigation.*;

/**
 * Test the lejos pilot moving the blighbot on track
 * 
 * Press ENTER to start and any button to return to the menu
 * when the program has finished.
 *
 * @author Kim Bjerge
 *
 */


public class TachoPilotTester
{
	// Wheel diameter, track width 
	// Big wheels
	//static TachoPilot robot = new TachoPilot(82f,102f,Motor.B, Motor.C,true);
	// Small wheels
	static TachoPilot robot = new TachoPilot(56f,112f,Motor.B, Motor.C,true);
 
	
	public static void main(String[] args ) throws Exception
	{
        // Wait for user to press ENTER
	    LCD.drawString("Test Pilot   ", 0, 0);
		Button.ENTER.waitForPressAndRelease();

		robot.setSpeed(300);
		
		// Blighbot moving towards way points

		// Moving forward in the track
		robot.travel(-1000, true);
		if (WaitMoving(1)) return;
		robot.rotate(-135, true); // Way point 1
		if (WaitMoving(5)) return;
		robot.travel(-710, true);
		if (WaitMoving(2)) return;
		robot.rotate(-135, true); // Way point 2
		if (WaitMoving(5)) return;
		robot.travel(-750, true);
		if (WaitMoving(3)) return;
		//robot.stop();			  // Way point 3

		// Moving backward the track
		robot.rotate(-180, true); // Way point 3
		if (WaitMoving(4)) return;
		robot.travel(-750, true);
		if (WaitMoving(4)) return;
		robot.rotate(135, true); // Way point 2
		if (WaitMoving(4)) return;
		robot.travel(-710, true);
		if (WaitMoving(4)) return;
		robot.rotate(135, true); // Way point 1
		if (WaitMoving(4)) return;
		robot.travel(-1000, true);
		if (WaitMoving(4)) return;
		robot.stop();			// Home
		
		// Exit after any button is pressed
		Button.waitForPress();
	}
		
	public static boolean WaitMoving(int dispPos)
	{
		while(robot.isMoving())
		{
			Thread.yield();
			showCount(dispPos);
			if (Button.ENTER.isPressed()) 
				return true;
		}
		return false;
	}

	public static void showCount(int i)
	{
		LCD.drawInt(robot.getLeftCount(),0,i);
		LCD.drawInt(robot.getRightCount(),7,i);
	}
}

