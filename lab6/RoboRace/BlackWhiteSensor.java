import lejos.nxt.*;
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
 * @version 01.10.2010, modified by Kim Bjerge
 */
public class BlackWhiteSensor {

   private LightSensor ls; 
   private int blackLightValue;
   private int whiteLightValue;
   private int blackWhiteThreshold;

   public BlackWhiteSensor(SensorPort p)
   {
	   ls = new LightSensor(p); 
	   // Use the light sensor as a reflection sensor
	   ls.setFloodlight(true);
   }

   public int getThreshold() {
	   return blackWhiteThreshold;
   }

   public int getWhiteToBlackSpan() {
	   return Math.abs(blackLightValue - whiteLightValue);
   }
   
   public int getBlack() {
	   return blackLightValue;
   }
   
   public int getWhite() {
	   return whiteLightValue;
   }

   private int read(String color){
	   
	   int lightValue=0;
	   
	   while (Button.ENTER.isPressed());
	   
	   LCD.clear();
	   LCD.drawString("Press ENTER", 0, 0);
	   LCD.drawString("to callibrate", 0, 1);
	   LCD.drawString(color, 0, 2);
	   while( !Button.ENTER.isPressed() ){
	      lightValue = ls.readValue();
	      LCD.drawInt(lightValue, 4, 10, 2);
	      LCD.refresh();
	   }
	   return lightValue;
   }
   
   public void calibrate()
   {
	   blackLightValue = read("black");
	   whiteLightValue = read("white");
	   // The threshold is calculated as the median between 
	   // the two readings over the two types of surfaces
	   blackWhiteThreshold = (blackLightValue+whiteLightValue)/2;
   }
   
   public boolean black() {
           return (ls.readValue()< blackWhiteThreshold);
   }
   
   public boolean white() {
	   return (ls.readValue()> blackWhiteThreshold);
   }
   
   public int light() {
 	   return ls.readValue();
   }
   
}
