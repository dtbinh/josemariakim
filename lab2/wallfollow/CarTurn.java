import lejos.nxt.*;
/**
 * A locomotion module with methods to drive
 * a car with two independent motors. The left motor 
 * should be connected to port C and the right motor
 * to port B.
 *  
 * @author  Ole Caprani
 * @version 17.4.08
 */
public class CarTurn 
{
    // Commands for the motors
    private final static int forward  = 1,
                             backward = 2,
                             stop     = 3;
	                         
    private static MotorPort leftMotor = MotorPort.C;
    private static MotorPort rightMotor= MotorPort.B;
	
    private CarTurn()
    {	   
    } 
   
    public static void stop() 
    {
	    leftMotor.controlMotor(0,stop);
	    rightMotor.controlMotor(0,stop);
    }
   
    public static void forward(int power)
    {
	    leftMotor.controlMotor(power,forward);
	    rightMotor.controlMotor(power,forward);
    }
   
    public static void backward(int power)
    {
	    leftMotor.controlMotor(power,backward);
	    rightMotor.controlMotor(power,backward);
    }

    public static void left(int power, int offset)
    {
	    leftMotor.controlMotor(power-offset,forward);
	    rightMotor.controlMotor(power,forward);
    }
    
    public static void right(int power, int offset)
    {
	    leftMotor.controlMotor(power,forward);
	    rightMotor.controlMotor(power-offset,forward);
    }
}
