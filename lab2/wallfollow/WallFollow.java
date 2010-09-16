import lejos.nxt.*;
/**
 * A LEGO 9797 car with a sonar sensor. The sonar is used
 * to maintain the car at a constant distance 
 * to objects in front of the car.
 * 
 * The sonar sensor should be connected to port 1. The
 * left motor should be connected to port C and the right 
 * motor to port B.
 * 
 * @author  Ole Caprani
 * @version 24.08.08
 */
public class WallFollow
{
	
  public static void main (String[] aArg)
  throws Exception
  {
     UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
     float error, gain = 0.5f;
     final int  noObject = 255;
     int distance,
     	 last_distance,
     	 power;

     // Parameters to be adjusted
     int desiredDistance = 0, // cm to wall
         minPower = 60,  
         slowPower = 20,  // + minPower
         maxPower = 90,
         rightPower = 6,  // Power difference to turn
         leftPower = 3,
         sampleTime = 50; // Time between reading sensor
     
     // Distance areas approaching wall cm
     int CloseWall1 = 30, 	// Turn Left
         CloseWall2 = 20, 	// Forward
         CloseWall3 = 15;  	// Turn Right
	  
     LCD.drawString("Distance: ", 0, 1);
     LCD.drawString("Power:    ", 0, 2);
	 
     last_distance = us.getDistance();
     
     while (! Button.ESCAPE.isPressed())
     {		   
         distance = us.getDistance();
         distance = distance/2 + last_distance/2; // 1. order moving average filter 
         last_distance = distance;
		 
         if ( distance != noObject ) 
         {
             error = distance - desiredDistance;
        	 power = slowPower; // Use fixed speed close to wall
        	 power = Math.min(minPower + power, maxPower);
             
             if (error < CloseWall3)
             {	 
            	 // Very close, turn in place
            	 // Turn motor right 
                 CarTurn.right(power, rightPower); // Hard turn
                 LCD.drawString("Right(3)  ", 0, 3);
             } 
             else if (error < CloseWall2)
             {
            	 // Car in right position 
            	 // Motor forward
                 CarTurn.forward(power);
                 LCD.drawString("Forward(2)", 0, 3);
             } 
             else if (error < CloseWall1)
             {
            	 // Wall seen turn in direction at slow speed
                 CarTurn.left(power, leftPower); // Soft turn
                 LCD.drawString("Left(1)   ", 0, 3);            	 
             }
             else
             {
            	 // Turn motor forward using proportional gain
                 power = (int)(gain * error);
                 power = Math.min(minPower + power, maxPower);
                 CarTurn.forward(power);
                 LCD.drawString("Forward(0)", 0, 3);            	 
             }
             
             LCD.drawInt(distance,4,10,1);
             LCD.drawInt(power, 4,10,2);
		 }
         else
        	 CarTurn.forward(maxPower); // Forward with full speed when nothing seen
		 
         Thread.sleep(sampleTime); // Samples each xx ms
     }
	 
     CarTurn.stop();
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     Thread.sleep(2000);
   }
}
