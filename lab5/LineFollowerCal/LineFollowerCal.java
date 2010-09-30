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
  private static int max_range = 50;
  private static int min_range = 40;
  
  private static int Tp = 50;
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
     sensor.calibrate();
  }
  
  public void calibrate() {
	  
  }
  
  public void pidCalculate(int lvalue) {
	 int turn;
	 int error;
	 float derivative = 0;
	 
 	 error = lvalue - offset;
 	 if (error > max_range) error = max_range;
 	 if (error < min_range) error = min_range;
 	 
 	 integral = (integral*2)/3 + error;
 	 derivative = error - lastError;
 	 turn = (int)(Kp*error + Ki*integral + Kd*derivative);
 	 powerA = Tp + turn; 
 	 powerC = Tp - turn;
 	 lastError = error;
 }
  
  public static void main (String[] aArg)
  throws Exception
  {
	 final int power = 80;
	 int LightValue;
	 
	 LineFollowerCal lineFollower = new LineFollowerCal(); 	 
	 
     LCD.clear();
     LCD.drawString("Light: ", 0, 2); 
	 
     while (! Button.ESCAPE.isPressed())
     {
    	 LightValue = sensor.light();
	     LCD.drawInt(LightValue,4,10,2);
	     LCD.refresh();
     
	     /*
	     lineFollower.pidCalculate(LightValue);
	     Car.forward(powerA, powerC);
	     */
	     
	     if ( sensor.black() )
	         Car.forward(power, 0);
	     else
	         Car.forward(0, power);
	     
	     Thread.sleep(10);
     }
     
     Car.stop();
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     LCD.refresh();
   }
}
