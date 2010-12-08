import lejos.nxt.*;
/**
 * Behavior: Turn at boarder
 * 
 * @author  Kim Bjerge
 * @version 04.12.2010
 */

public class TurnAtBoarder extends Behavior {

	private final int lightThreshold = 40; // Black ~34
	private boolean left = true;
	private LightSensor ls;
	
	public TurnAtBoarder(String name, int LCDrow, Behavior subsumedBehavior, LightSensor lightSensor) 
	{
		super(name, LCDrow, subsumedBehavior);
		ls = lightSensor;
	}

	public void run() {
		String message;
		
		while(true){
			
			while(ls.readValue() > lightThreshold){
				//only display the value if we don't detect the boarder of search area
				message = getStatusMessage();			
				drawString(message);
			}
			
			// When robot detects boarder area marker
			// it turns to search for object in a new direction. 
			// Turn either +/- 170 degrees
			suppress();
			if (left) {
				rotateTo(170);				
			} else {
				rotateTo(-170);
			}
			left = !left;
				
			message = getStatusMessage();			
			drawString(message);
			delay(1500);
			stop();
			release();
		}
	}

	private String getStatusMessage() {
		return " " + ls.readValue();
	}

}

