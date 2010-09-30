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
  private static int max_power = 90;
  private static int min_power = 60;
  
  private static int Tp = 75;
  private static int offset = 45;
  /* Test0 
  private static float Kp = 2.0F;
  private static float Ki = 0.0F;
  private static float Kd = 0.0F;
  */
  /* Test1 
  private static float Kp =  1.20F;
  private static float Ki =  0.03F;
  private static float Kd = 12.00F;
  */
  /* Test2
  private static float Kp =  2.40F;
  private static float Ki =  0.08F;
  private static float Kd = 18.00F;
  */
  /* Test3 */ 
  private static float Kp =  1.80F;
  private static float Ki =  0.045F;
  private static float Kd = 18.00F;
 

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
	 int time = 0;
	 
	 LineFollowerCal lineFollower = new LineFollowerCal(); 
	 
	 lineFollower.calibrate();
	 
     LCD.clear();
     LCD.drawString("Light: ", 0, 2); 
     LCD.drawString("Time: ", 0, 3); 
	 
     while (! Button.ESCAPE.isPressed())
     {
    	 LightValue = sensor.light();
	     LCD.drawInt(LightValue,4,10,2);
	     LCD.drawInt(time,4,10,3);
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
	     time++;
     }
     
     Car.stop();
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     LCD.refresh();
   }
}
