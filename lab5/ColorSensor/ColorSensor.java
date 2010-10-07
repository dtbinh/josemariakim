import lejos.nxt.*;
import lejos.nxt.addon.RCXLightSensor;
/**
 * A sensor that is able to distinguish a black/dark surface
 * from a white/bright surface.
 * 
 * Light percent values from an active light sensor and a
 * threshold value calculated based on a reading over a 
 * black/dark surface and a reading over a light/bright 
 * surface is used to make the distinction between the two 
 * types of surfaces.
 *  
 * @author  Ole Caprani
 * @version 23.08.07
 */
public class ColorSensor {

   private RCXLightSensor rcsLs;
   private int blackLightValue;
   private int whiteLightValue;
   private int greenLightValue;
   private int whiteGreenThreshold;
   private int greenBlackThreshold;

   public ColorSensor(SensorPort p)
   {
	   rcsLs = new RCXLightSensor(p);
	   // Use the light sensor as a reflection sensor
	   rcsLs.setFloodlight(true);
   }

   private int read(String color){
	   
	   int lightValue=0;
	   
	   while (Button.ENTER.isPressed());
	   
	   LCD.clear();
	   LCD.drawString("Press ENTER", 0, 0);
	   LCD.drawString("to callibrate", 0, 1);
	   LCD.drawString(color, 0, 2);
	   while( !Button.ENTER.isPressed() ){
	      lightValue = rcsLs.readValue();
	      LCD.drawInt(lightValue, 4, 10, 2);
	      LCD.refresh();
	   }
	   return lightValue;
   }
   
   public void calibrate()
   {
	   blackLightValue = read("black");
	   whiteLightValue = read("white");
	   greenLightValue = read("green");
	   
	   // The threshold is calculated as the median between 
	   // the two readings over the two types of surfaces	   
	   greenBlackThreshold = (blackLightValue+greenLightValue)/2;
	   whiteGreenThreshold = (greenLightValue+whiteLightValue)/2;
   }
   
   
   public boolean black() {
           return (rcsLs.readValue()< greenBlackThreshold);
   }
   
   public boolean white() {
	   return (rcsLs.readValue()> whiteGreenThreshold);
   }
   
   public boolean green() {
	   return(rcsLs.readValue() < whiteGreenThreshold && rcsLs.readValue() > greenBlackThreshold);
   }
   
   public int light() {
 	   return rcsLs.readValue();
   }
   
}
