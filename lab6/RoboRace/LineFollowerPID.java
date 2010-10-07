import lejos.nxt.*;
/**
 * A PID controlled line follower for the LEGO 9797 car with
 * a light sensor. Before the car is started on a line
 * a BlackWhiteSensor is calibrated to adapt to different
 * light conditions and colors.
 * 
 * The light sensor should be connected to port 3. The
 * left motor should be connected to port C and the right 
 * motor to port B.
 * 
 * @author  Kim Bjerge
 * @version 01.10.2010
 */

public class LineFollowerPID extends Thread
{

  public BlackWhiteSensor sensor;
  
  // Configuration values
  private final int max_power = 100;
  private final int min_power = 40;
  private final int default_Tp = 75;
  private int Tp = 75;  // default forward power
  
  //private int Tp = 66;  // default forward power, OK
  private final int dT = 4;   // ms
  private int Pc = 800; // ms, estimate depends on Tp and Kp
  
  // Computed constants, OK
  private int offset = 45;
  private float Kp =  3.00F;
  private float Ki =  0.03F;
  private float Kd = 75.00F;
  //private float Ki =  0.05F;
  //private float Kd = 45.00F;

  // Variables
  private float integral = 0;
  private int lastError;
  private int powerA;
  private int powerC;
  private int lightValue;
  private boolean pause;
  private boolean stop;
  private boolean rightTurn = true;

  public LineFollowerPID() {
     sensor = new BlackWhiteSensor(SensorPort.S3);
  }
  
  public void setLeftSideOfLine() {
	  rightTurn = true;  
  }
  
  public void setRightSideOfLine() {
	  rightTurn = false;	  
  }
  
  public int getLight() {
	  return lightValue;  
  }
  
  public void pause(boolean p) {
	  pause = p;
  }
  
  public void stop(boolean s) {
	  stop = s;
  }
  
  public void setSpeed(int speed)
  {
	  Tp = speed;
  }
  
  public void setDefaultSpeed()
  {
	  Tp = default_Tp;
  }
  
  private void computePIDConstants() {
	  int maxPowerDiff = Tp - min_power;
	  float maxError = sensor.getWhiteToBlackSpan()/2;
	  float Kc = maxPowerDiff/maxError;
	  Kp = 0.6F*Kc;
	  Ki = 2*Kp*((float)dT/(float)Pc);
	  Kd = Kp*((float)Pc/(8*dT));  
  }
  
  private void displayConstants() {	  
     LCD.drawString("Kp [*10] ", 0, 4);
     LCD.drawInt((int)(Kp*10),4,10,4);
	 LCD.drawString("Ki [*1000]", 0, 5);
     LCD.drawInt((int)(Ki*1000),4,10,5);
	 LCD.drawString("Kd ", 0, 6);
     LCD.drawInt((int)(Kd),4,10,6);
  }
  
  public void calibrate() {
	 sensor.calibrate();
	 offset = sensor.getThreshold();
	 //computePIDConstants();
     integral = 0;
     displayConstants();
  }
  
  public void incSensitiv() {
	 //if (Pc < 2000) Pc += 100;  
	 if (Tp < 100) Tp += 2;  
	 //computePIDConstants();
	 displayConstants();
  }
  
  public void decSensitiv() {  
	 //if (Pc > 200) Pc -= 100;  
	 if (Tp > 50) Tp -= 2;  
	 //computePIDConstants();
	 displayConstants();
  }
  
  private int limitPower(int p)
  {
	  if (p < min_power) return min_power;
	  if (p > max_power) return max_power;
	  return p;
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
 		powerA = limitPower(Tp); 
 		powerC = limitPower(Tp - 2*turn);
 	 }
 	 else
 	 {
  		powerA = limitPower(Tp + 2*turn); 
 		powerC = limitPower(Tp); 		 
 	 }
 	 
 	 
 	 //powerA = limitPower(Tp + turn); 
 	 //powerC = limitPower(Tp - turn);
 	 
 	 lastError = error;
 }
  
  public void run ()
  {

     while (true)
     {
       try  {
  
    	 lightValue = sensor.light();
	     pidCalculate(lightValue);
	     
	     if (!pause)
	     {
		     if (stop)
		    	 Car.stop();
	    	 else
	    		 if (rightTurn)
	    			 Car.forward(powerA, powerC);
	    		 else 
	    			 Car.forward(powerC, powerA);
	     }
    	 
	     Thread.sleep(dT);  	 
	     
       } catch (InterruptedException ie)  {	   
       }   
     }     
   }
}
