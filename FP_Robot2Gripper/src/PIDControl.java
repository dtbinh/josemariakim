import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;
/**
 * A PID controlled light follower for Robot 2
 * 
 * @author  Kim Bjerge
 * @version 15.12.10
 */

public class PIDControl
{
  
  // Configuration values
  private final int max_power = 100;
  private final int min_power = 40;
  private final int default_Tp = 65;
  private int Tp = 65;  // default forward power
    
  // Computed constants, OK
  private int offset = 0;
  private float Kp =  4.00F;
  private float Ki =  0.03F;
  private float Kd = 75.00F;

  // Variables
  private float integral = 0;
  private int lastError;
  private int powerLeft;
  private int powerRight;
  private int lightValue;
  private RCXLightSensor leftLight;
  private RCXLightSensor rightLight;


  public PIDControl(RCXLightSensor left, RCXLightSensor right) {
     leftLight = left;
     rightLight = right;
  }
  
  public int getLight() {
	  return lightValue;  
  }
  
  public void setSpeed(int speed)
  {
	  Tp = speed;
  }
  
  public void setDefaultSpeed()
  {
	  Tp = default_Tp;
  }
  
  private void displayConstants() {	  
     LCD.drawString("Kp [*10] ", 0, 3);
     LCD.drawInt((int)(Kp*10),4,10,3);
	 LCD.drawString("Ki [*1000]", 0, 4);
     LCD.drawInt((int)(Ki*1000),4,10,4);
	 LCD.drawString("Kd ", 0, 5);
     LCD.drawInt((int)(Kd),4,10,5);
	 LCD.drawString("Offset ", 0, 6);
     LCD.drawInt(offset,4,10,6);
  }
  
  public void calibrate() {
	 offset = leftLight.getLightValue();
	 offset -= rightLight.getLightValue();
     integral = 0;
     displayConstants();
  }
  
  public void incSensitiv() {
	 if (Tp < 100) Tp += 2;  
	 displayConstants();
  }
  
  public void decSensitiv() {  
	 if (Tp > 50) Tp -= 2;  
	 displayConstants();
  }
  
  private int limitPower(int p)
  {
	  if (p < min_power) return min_power;
	  if (p > max_power) return max_power;
	  return p;
  }
  
  public int getLeftPower()
  {
	  return powerLeft;
  }
  
  public int getRightPower()
  {
	  return powerRight;
  }

  private void pidCalculate(int lvalue) {
	 int turn;
	 int error;
	 float derivative = 0;
	 
 	 error = lvalue - offset;
 	 integral = (integral*2)/3 + error;
 	 derivative = error - lastError;
 	 turn = (int)(Kp*error + Ki*integral + Kd*derivative);	 
 	 
 	 // Try to reduce power in turns 
 	 if (turn > 0)
 	 {
 		powerLeft = limitPower(Tp); 
 		powerRight = limitPower(Tp - 2*turn);
 	 }
 	 else
 	 {
  		powerLeft = limitPower(Tp + 2*turn); 
  		powerRight = limitPower(Tp); 		 
 	 }
 	 
 	 lastError = error;
 }
  
  public void regulateStep ()
  {
  
	 lightValue = leftLight.getLightValue();
	 lightValue -= rightLight.getLightValue();
     pidCalculate(lightValue);
  }
  
}