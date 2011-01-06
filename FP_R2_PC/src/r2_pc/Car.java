package r2_pc;
import lejos.nxt.*;
import lejos.robotics.Pose;
import lejos.robotics.navigation.SimpleNavigator;
import lejos.robotics.navigation.TachoPilot;
/**
 * A locomotion module with methods to drive
 * a car with two independent motors. The left motor 
 * should be connected to port C and the right motor
 * to port B.
 *  
 * @author  Ole Caprani revised by Kim Bjerge using SimpleNavigator
 * @version 23.12.10
 */
public class Car 
{	                         
    // Wheel diameter, track width for small wheels
    private static TachoPilot pilot = new TachoPilot(56f, 112f, Motor.B, Motor.C, false);
	private static SimpleNavigator robot = new SimpleNavigator(pilot);
	
    private Car()
    {	   
    } 
    
    public static synchronized void InitCar()
    {
	   	robot.setMoveSpeed(150);
    	robot.setTurnSpeed(80);
    }
    
    public static synchronized void setDefaultSpeed()
    {
    	InitCar();
    }

    public static synchronized void setSpeed(int speed)
    {
    	robot.setMoveSpeed(speed);
    	robot.setTurnSpeed(speed);
    }
    
    public static synchronized Pose getPose()
    {
    	robot.updatePose();
    	return robot.getPose();
    }     
  
    public static synchronized void forward(int leftPower, int rightPower)
    {
    	// Steer values between -200 to 200 thats why multiply by two
    	robot.steer((leftPower - rightPower)*2);
    	//robot.updatePose();
    	// Steer method replaces
	    // leftMotor.controlMotor(leftPower,forward);
	    // rightMotor.controlMotor(rightPower,forward);
    }
       
    public static synchronized void stop() 
    {
    	robot.stop();
    }
   
    public static synchronized void forward()
    {
   		robot.forward();
     }
   
    public static synchronized void backward()
    {
   		robot.backward();
    }

    public static synchronized void goTo(int x, int y, boolean imediateReturn)
    {
		robot.goTo(x, y, imediateReturn);
    }

    public static synchronized void rotate(int angle, boolean imediateReturn)
    {
		robot.rotate(angle, imediateReturn);
    }

    public static synchronized void rotateTo(int angle, boolean imediateReturn)
    {
		robot.rotateTo(angle, imediateReturn);
    }
    
    public static boolean isMoving()
    {
    	return robot.isMoving();
    }
}
