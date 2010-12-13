import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;

/**
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
  
  private int min = 35;
  private int max = 40;
  private int sample_2;
  private int sample_1;
  private final int min_power = 60;
  private Filter maxFilter;
  private Filter minFilter;
  
  public LightCtrMotor(SensorPort p, MotorPort m) {
	  
	  //light = new LightSensor(p); // NXT light sensor
	  //light.setFloodlight(true);

	  light = new RCXLightSensor(p); 
	  light.setFloodlight(true);
	  motor = m;
	  maxFilter = new Filter(max, 0.5);
	  minFilter = new Filter(min, 0.5);
  }
  
  public void updateMinMax(int sample)
  {
	  if (sample > max) max = sample;
	  if (sample < min) min = sample;
  }
  
  public void updateFilteredMinMax(int sample)
  {
	  // Filter max and min values based on last sample readings
	  if (sample_1 >= sample_2 && sample_1 > sample) 
		  max = maxFilter.averageFilter(sample_1); // Local maximum
	  if (sample_1 <= sample_2 && sample_1 < sample)  
		  min = minFilter.averageFilter(sample_1); // Local minimum
	  
	  sample_2 = sample_1;
	  sample_1 = sample;
  }
  
  public int normalize(int sample)
  {
	  int scale = max - min;
	  int tmp = sample - min;
	  return tmp * (60/scale); // Chosen based on max power (100) and min power (60)
  }
  
  public int getValue(){
	  return light.readValue();
  }
  
  public void run ()
  {
	 int read; 
     sample_1 = getValue();
     sample_2 = getValue();
   
     while (true)
     {
       try  {
         
    	 read = light.readValue(); // Values in between 28 - 51 (light)
	     updateMinMax(read);
	     //updateFilteredMinMax(read);
    	 read = normalize(read);
	     motor.controlMotor(read+min_power, forward); // Add offset 
 	     
 	     Thread.sleep(dT);  	 
	     
       } catch (InterruptedException ie)  {	   
       }   
     }     
   }
}
