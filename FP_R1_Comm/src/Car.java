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
    
    public static synchronized void setPose(int x, int y, int heading)
    {
    	robot.setPose(0, 0, 0);
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

    public static synchronized void rotate(int angle, boolean imediateReturn)
    {
		robot.rotate(angle, imediateReturn);
    }
    
    public static boolean isMoving()
    {
    	return robot.isMoving();
    }
}
