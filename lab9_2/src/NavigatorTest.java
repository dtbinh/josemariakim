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
// Moves 500 mm ok displays x = 141, y = 10
// Moves 100 mm - displays x = 990, y = 80

public class NavigatorTest
{
	// Wheel diameter, track width 
	// Big wheels
	//static TachoPilot robot = new TachoPilot(82f,102f,Motor.B, Motor.C,true);
	// Small wheels
	static TachoPilot pilot = new TachoPilot(56f,112f,Motor.B, Motor.C,false);
	static SimpleNavigator robot;
 
	
	public static void main(String[] args ) throws Exception
	{
		robot = new SimpleNavigator(pilot);
		
        // Wait for user to press ENTER
	    LCD.drawString("Navigator Tester  ", 0, 0);
		Button.ENTER.waitForPressAndRelease();

		robot.setMoveSpeed(300);
		robot.setTurnSpeed(200);
		
		for (int i = 0; i < 4; i++)
		{
			robot.goTo(500, 0, true);
			if (WaitMoving()) return;
			showCount(1);
	
			robot.goTo(500, 500, true);
			if (WaitMoving()) return;
			showCount(2);
			
			robot.goTo(0, 500, true);
			if (WaitMoving()) return;
			showCount(3);
	
			robot.goTo(0, 0, true);  
			if (WaitMoving()) return;
			showCount(4);
		}
		robot.stop();
				
		// Exit after any button is pressed
		Button.waitForPress();
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

