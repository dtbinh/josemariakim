import lejos.nxt.*;
/**
 * A simple line follower for the LEGO 9797 car with
 * a light sensor. Before the car is started on a line
 * a BlackWhiteSensor is calibrated to adapt to different
 * light conditions and colors.
 * 
 * The light sensor should be connected to port 3. The
 * left motor should be connected to port C and the right 
 * motor to port B.
 * 
 * @author  Ole Caprani
 * @version 23.08.07
 */
public class LineFollowerCal
{

  private static BlackWhiteSensor sensor;
  
  // Porportional range
  private static int max_power = 100;
  private static int min_power = 40;
  
  private static int Tp = 70;
  private static int offset = 45;
  private static float Kp = 5.0F;
  private static float Ki = 0.0F;
  private static float Kd = 0.0F;

  private int lastError;
  private float integral = 0;
  private static int powerA;
  private static int powerC;

  public LineFollowerCal() {
     sensor = new BlackWhiteSensor(SensorPort.S3);
  }
  
  public void calibrate() {
	 sensor.calibrate();
	 offset = sensor.getThreshold();
  }
  
  public int limitPower(int p)
  {
	  if (p < min_power) return min_power;
	  if (p > max_power) return max_power;
	  return p;
  }
  
  public void pidCalculate(int lvalue) {
	 int turn;
	 int error;
	 float derivative = 0;
	 
 	 error = lvalue - offset;
 	 integral = (integral*2)/3 + error;
 	 derivative = error - lastError;
 	 turn = (int)(Kp*error + Ki*integral + Kd*derivative);	 
 	 
 	 powerA = limitPower(Tp + turn); 
 	 powerC = limitPower(Tp - turn);
 	 
 	 lastError = error;
 }
  
  public static void main (String[] aArg)
  throws Exception
  {
	 final int power = 80;
	 int LightValue;
	 
	 LineFollowerCal lineFollower = new LineFollowerCal(); 
	 
	 lineFollower.calibrate();
	 
     LCD.clear();
     LCD.drawString("Light: ", 0, 2); 
	 
     while (! Button.ESCAPE.isPressed())
     {
    	 LightValue = sensor.light();
	     LCD.drawInt(LightValue,4,10,2);
	     LCD.refresh();
     
	     /**/
	     lineFollower.pidCalculate(LightValue);
	     Car.forward(powerA, powerC);
	     
	     /*
	     if ( sensor.black() )
	         Car.forward(power, 0);
	     else
	         Car.forward(0, power);
	     */
	     
	     Thread.sleep(10);
     }
     
     Car.stop();
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     LCD.refresh();
   }
}
