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
public class BlackWhiteMain
{
  public static void main (String[] aArg)
  throws Exception
  {
     final int power = 80;
	  
     BlackWhiteSensor sensor = new BlackWhiteSensor(SensorPort.S3);
	 
     sensor.calibrate();
	 
     LCD.clear();
     LCD.drawString("Light: ", 0, 2); 
	 
     while (! Button.ESCAPE.isPressed())
     {

	     LCD.drawInt(sensor.light(),4,10,2);
	     LCD.refresh();
	       
	     Thread.sleep(10);
     }
     
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     LCD.refresh();
   }
}
