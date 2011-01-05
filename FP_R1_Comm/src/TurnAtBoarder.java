import communication.DataLogger;

import lejos.nxt.*;
/**
 * Behavior: Turn at boarder
 * 
 * @author  Kim Bjerge
 * @version 04.12.2010
 */

public class TurnAtBoarder extends Behavior {

	private final int lightThreshold = 40; // Black ~34
	//private final int angleDefault = 170; // Turn angle in search for object
	private final int angleDefault = 140; // Turn angle in search for object
	private boolean rotateLeft = false;
	private int rotateAngle = angleDefault;
	private LightSensor ls;
	private int numberOfTurns;
	
	// Return number of turn at boarder since started 
	public int GetNumberOfTurns()
	{
		return numberOfTurns;
	}
	
	public void ResetTurns()
	{
		rotateLeft = false;
		numberOfTurns = 0;
	}
	
	public TurnAtBoarder(String name, int LCDrow, Behavior subsumedBehavior, LightSensor lightSensor, DataLogger logger) 
	{
		super(name, LCDrow, subsumedBehavior, logger);
		this.ls = lightSensor;
	}

	public void run() {
		String message;
		int countBoarderSeen = 0;  // Counts how many times the boarder seen in a row
		int timeSinceLastTurn = 0; // Counts the time between turns
		numberOfTurns = 0;
		
		while(true){
			
			while(ls.readValue() > lightThreshold){
				//only display the value if we don't detect the boarder of search area
				message = getStatusMessage();			
				drawString(message);
				delay(5);
				if (timeSinceLastTurn++ > 200)
				{
					countBoarderSeen = 0; // Clear boarder seen counter (~0.5 sec.)
				}
			}
			timeSinceLastTurn = 0;
			
			// When robot detects boarder area marker
			// it turns to search for object in a new direction. 
			// Turn either +/- 160 degrees
			suppress();
			
			logLine("Turning at border");
			
			if (++countBoarderSeen < 2)
			{
				// Default turn behavior
				rotateLeft = !rotateLeft;
				rotateAngle = angleDefault;
			}
			else 
			{
				// If robot got stuck in a corner only turn 90 degrees
				rotateAngle = 90;
			}

			if (rotateLeft) 
				rotate(-rotateAngle);
			else
				rotate(rotateAngle);

			numberOfTurns++;
			message = getStatusMessage();			
			drawString(message);
			delay(100);
			stop();
			release();
		}
	}

	private String getStatusMessage() {
		return " " + ls.readValue();
	}

}

