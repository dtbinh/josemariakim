import lejos.nxt.*;
import lejos.nxt.addon.ColorSensor;
/**
 * Simple program to test the sensors for Robot Car #1 
 *
 * Testing:
 * 	UltrasonicSensor - measures distance
 * 	LightSensor - measures black/white
 * 	ColorLightSensor - measures red, yellow and blue
 * 
 * @author  Kim Bjerge
 * @version 02.12.10
 */
public class SensorTest 
{

   public static void main(String [] args)  
   throws Exception 
   {
	   int dist_val, light_val, color_val;

	   String r = "R";
	   String g = "G";
	   String b = "B";
	   
       UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
	   LightSensor ls = new LightSensor(SensorPort.S2);
	   ColorSensor cs = new ColorSensor(SensorPort.S3);
       ls.setFloodlight(true);

       while (Button.ENTER.isPressed());
	   LCD.drawString("Press ENTER to", 0, 0);
	   LCD.drawString("Calibrate White balance", 0, 1);
	   while (!Button.ENTER.isPressed());
	   cs.initWhiteBalance();
	   
       while (Button.ENTER.isPressed());
	   LCD.drawString("Press ENTER to", 0, 0);
	   LCD.drawString("Calibrate Black balance", 0, 1);
	   while (!Button.ENTER.isPressed());
	   cs.initBlackLevel();

	   LCD.drawString("Robot 1 Sensor Test", 0, 0);  
	   LCD.drawString("Distance(cm): ", 0, 1); // Close 20
	   LCD.drawString("Light raw   : ", 0, 2); // White (50) Black (34)
	   LCD.drawString("Color       : ", 0, 3); // Read (9) Yelllow(5) Blue(0) 
     
       while (! Button.ESCAPE.isPressed())
       {
    	   dist_val = us.getDistance();
    	   light_val = ls.readValue();
    	   color_val = cs.getColorNumber();
    	   
           LCD.drawInt(dist_val,  3, 13, 1);
           LCD.drawInt(light_val, 3, 13, 2);
           LCD.drawInt(color_val, 3, 13, 3);
           
           LCD.drawString(r, 0, 5);
           LCD.drawInt((int)cs.getRedComponent(),1,5);
           LCD.drawString(g, 5, 5);
           LCD.drawInt((int)cs.getGreenComponent(),6,5);
           LCD.drawString(b, 10, 5);
           LCD.drawInt((int)cs.getBlueComponent(),11,5);
           LCD.refresh();
          
           Thread.sleep(300);
       }
       LCD.clear();
       LCD.drawString("Program stopped", 0, 0);
       Thread.sleep(2000);
   }
}
