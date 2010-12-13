import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;
import lejos.robotics.navigation.*;

/**
 * Test the Robot 2 construction creating light emitted from object
 *
 * @author Kim Bjerge
 * @version 13.12.10
 * 
 */
public class LigthTest
{

	public static void main(String[] args ) throws Exception
	{
		boolean exit = false;
		Motor leftLight = Motor.A;

		LCD.drawString("Ligth test A ", 0, 0);

		while (!exit)
		{
			leftLight.setSpeed(200);
			leftLight.forward();

			while (!Button.ENTER.isPressed() &&
				   !Button.ESCAPE.isPressed());	

			if (Button.ESCAPE.isPressed())
			{
				// Lower gripper
				exit = true;
			}
		}

		leftLight.stop();
	}
	
}

