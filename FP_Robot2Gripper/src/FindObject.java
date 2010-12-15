import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;
/**
 * Follow inferred light using PID control
 * 
 * @author  Kim Bjerge
 * @version 15.12.10
 */

public class FindObject extends Behavior {

	private RCXLightSensor leftLight;
	private RCXLightSensor rightLight;
	private PIDControl pidControl;
	//private int lightThreshold = 21;
	
	public FindObject(String name, int LCDrow, Behavior subsumedBehavior) 
	{
		super(name, LCDrow, subsumedBehavior);
		leftLight = new RCXLightSensor(SensorPort.S2);
		leftLight.setFloodlight(true);
		rightLight = new RCXLightSensor(SensorPort.S3);
		rightLight.setFloodlight(true);
		pidControl = new PIDControl (leftLight, rightLight);
		
	}

	public void Calibrate()
	{
		pidControl.calibrate();
	}
	
	public void run() {
		while(true){
			/*
			while(leftLight.getLightValue() < lightThreshold
					&& rightLight.getLightValue() < lightThreshold){
				//only display the value if we don't detect a light source
				String message = getStatusMessage();			
				drawString(message);
				
			}
			*/
			
			//int leftPower = leftLight.getLightValue() + 50;
			//int rightPower = rightLight.getLightValue() + 55;
			
			String message = getStatusMessage();			
			drawString(message);
			pidControl.regulateStep();

			suppress();
			forward(pidControl.getLeftPower(), pidControl.getRightPower());
			release();

			delay(5);
		}
	}

	private String getStatusMessage() {
		return leftLight.getLightValue()+ "-" + rightLight.getLightValue();
	}

	
	
}

