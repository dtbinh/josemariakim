import java.awt.geom.Point2D.Float;

import lejos.nxt.*;
/**
 * Clap car controller 
 */

public class Sejway 
{
	// PID constants
	static final float KP = 28;
	static final float KI = 4;
	static final float KD = 33;
	static final float SCALE = 10;
	
	// Global vars:
	float offset;
	float prev_error;
	float int_error;
	
	LightSensor ls;
	
	public Sejway() {
		ls = new LightSensor(SensorPort.S2, true);
		Motor.B.regulateSpeed(false);
		Motor.C.regulateSpeed(false);
	}
	
	public void getBalancePos() {
		// Wait for user to balance and press orange button
		while (!Button.ENTER.isPressed()) {
			// NXTway must be balanced.
			offset = ls.readNormalizedValue();
			LCD.clear();
			LCD.drawInt(java.lang.Float.floatToIntBits(offset), 2, 4);
	
			LCD.refresh();
		}
	}
	
	public void pidControl() {
		while (!Button.ESCAPE.isPressed()) {
			int normVal = ls.readNormalizedValue();
			
			// Proportional Error:
			float error = normVal - offset;
			// Adjust far and near ligth readings:
			if (error < 0) error = (float)(error * 1.8F);
			
			// Integral Error:
			int_error = ((int_error + error) *2)/3;
			
			// Derivative Error:
			float deriv_error = error - prev_error;
			prev_error = error;
			
			float pid_val = (float)(KP * error + KI * int_error + KD * deriv_error) / SCALE;
			
			if (pid_val > 100)
				pid_val = 100;
			if (pid_val < -100)
				pid_val = -100;
			
			// Power derived from PID value:
			int power = (int) Math.abs(pid_val);
			power = 35 + (power * 45) / 100; // NORMALIZE POWER
			
			Motor.B.setPower(power);
			Motor.C.setPower(power);
			
			if (pid_val > 0) {
				Motor.B.forward();
				Motor.C.forward();
			} else {
				Motor.B.backward();
				Motor.C.backward();
			}
		}
	}
	
	public void shutDown() {
		// Shut down light sensor, motors
		Motor.B.flt();
		Motor.C.flt();
		ls.setFloodlight(false);
	}

    public static void main(String [] args) throws Exception
    {
    	Sejway sej = new Sejway();
        LCD.drawString("Sejway running", 0, 0);
        LCD.refresh();
    	
    	sej.getBalancePos();
    	sej.pidControl();
    	sej.shutDown();
    	   
   
        LCD.clear();
        LCD.drawString("Program stopped", 0, 0);
        LCD.refresh();
        Thread.sleep(2000); 
   }
}
