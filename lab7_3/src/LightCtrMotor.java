import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;

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

public class LightCtrMotor extends Thread
{
  private final int dT = 10;   // ms

  // Commands for the motors
  private final static int forward  = 1,
                           backward = 2,
                           stop     = 3;
 
  //private LightSensor light; // NXT light sensor
  private MotorPort motor;
  private RCXLightSensor light;
  
  public LightCtrMotor(SensorPort p, MotorPort m) {
	  
	  //light = new LightSensor(p); // NXT light sensor
	  //light.setFloodlight(true);

	  light = new RCXLightSensor(p); 
	  light.setFloodlight(true);
	  motor = m;
  }
  
  public int getValue(){
	  return light.readValue();
  }
  
  public void run ()
  {
	 int read; 

     while (true)
     {
       try  {
         
    	 read = light.readValue(); // Values in between 28 - 51 (light)
 	     motor.controlMotor(read+30, forward); // Add offset 
 	     
 	     Thread.sleep(dT);  	 
	     
       } catch (InterruptedException ie)  {	   
       }   
     }     
   }
}
