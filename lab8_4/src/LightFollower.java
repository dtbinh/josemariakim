import lejos.nxt.*;
/**
 * Light follower behavior
 * 
 * @author  Maria Soler
 * @version 04.10.2010
 */

public class LightFollower extends Behavior {

	private LightControlMotor leftLightCtrMotor;
	private LightControlMotor rightLightCtrMotor;
	private int lightThreshold = 35;
	
	public LightFollower(String name, int LCDrow, Behavior subsumedBehavior) 
	{
		super(name, LCDrow, subsumedBehavior);
		leftLightCtrMotor = new LightControlMotor(SensorPort.S2, MotorPort.C);
		rightLightCtrMotor = new LightControlMotor(SensorPort.S3, MotorPort.B);
		
	}

	public void run() {
		while(leftLightCtrMotor.getValue() < lightThreshold
				&& rightLightCtrMotor.getValue() < lightThreshold){
			//only display the value if we don't detect a light source
			String message = getStatusMessage();			
			drawString(message);
			
		}
		suppress();
		leftLightCtrMotor.reactOnLight();
		rightLightCtrMotor.reactOnLight();
		drawString("Driving to the light...");
		delay(1000);
		release();
	}

	private String getStatusMessage() {
		return "l = " + leftLightCtrMotor.getValue() 
		+ "- r = " + rightLightCtrMotor.getValue();
	}

	
	
}

