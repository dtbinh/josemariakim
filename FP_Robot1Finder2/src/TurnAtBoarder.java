import lejos.nxt.*;
/**
 * Behavior: Turn at boarder
 * 
 * @author  Kim Bjerge
 * @version 04.12.2010
 */

public class TurnAtBoarder extends Behavior {

	private final int lightThreshold = 40; // Black ~34
	private int direction = 1;
	private LightSensor ls;
	
	public TurnAtBoarder(String name, int LCDrow, Behavior subsumedBehavior, LightSensor lightSensor) 
	{
		super(name, LCDrow, subsumedBehavior);
		this.ls = lightSensor;
	}

	public void run() {
		String message;
		
		while(true){
			
			while(ls.readValue() > lightThreshold){
				//only display the value if we don't detect the boarder of search area
				message = getStatusMessage();			
				drawString(message);
				delay(5);
			}
			
			// When robot detects boarder area marker
			// it turns to search for object in a new direction. 
			// Turn either +/- 170 degrees
			suppress();
			
			switch (direction) {
				case 0:
					rotate(150);
					break;
				case 1:
					rotate(-150);
					break;
				case 2: 
					rotate(90);
					break;
				case 4:
					rotate(-90);
					break;
			}
			if (++direction == 2) direction = 0;
				
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

