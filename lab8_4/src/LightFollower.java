import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;
/**
 * Light follower behavior
 * 
 * @author  Maria Soler
 * @version 04.10.2010
 */

public class LightFollower extends Behavior {

	private RCXLightSensor leftLight;
	private RCXLightSensor rightLight;
	private int lightThreshold = 21;
	
	public LightFollower(String name, int LCDrow, Behavior subsumedBehavior) 
	{
		super(name, LCDrow, subsumedBehavior);
		leftLight = new RCXLightSensor(SensorPort.S2);
		leftLight.setFloodlight(true);
		rightLight = new RCXLightSensor(SensorPort.S3);
		rightLight.setFloodlight(true);
		
	}

	public void run() {
		while(true){
		while(leftLight.readValue() < lightThreshold
				&& rightLight.readValue() < lightThreshold){
			//only display the value if we don't detect a light source
			String message = getStatusMessage();			
			drawString(message);
			
		}
		suppress();
		int leftPower = leftLight.readValue() + 50;
		int rightPower = rightLight.readValue() + 50;
		forward(leftPower, rightPower);
		String message = getStatusMessage();			
		drawString(message);
		delay(1500);
		stop();
		release();
		}
	}

	private String getStatusMessage() {
		return leftLight.readValue()+ "-" + rightLight.readValue();
	}

	
	
}

