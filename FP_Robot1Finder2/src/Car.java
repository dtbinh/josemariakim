import lejos.nxt.*;
import lejos.robotics.navigation.SimpleNavigator;
import lejos.robotics.navigation.TachoPilot;
import lejos.robotics.Pose;
/**
 * Car
 *  
 * @author  Kim Bjerge
 * @version 7.12.10
 */

public class Car 
{	
	// Wheel diameter, track width for small wheels
    private static TachoPilot pilot = new TachoPilot(56f, 112f, Motor.B, Motor.C, false);
	private static SimpleNavigator robot = new SimpleNavigator(pilot);
	
    private Car()
    {	   
    } 
    
    public static void InitCar()
    {
		robot.setMoveSpeed(250);
		robot.setTurnSpeed(100);
    }
   
    public static synchronized Pose getPose()
    {
    	robot.updatePose();
    	return robot.getPose();
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

    public static synchronized void rotate(int angle)
    {
		robot.rotate(angle);
    }
}
