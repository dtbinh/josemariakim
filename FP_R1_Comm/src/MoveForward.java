/**
 * A behavior that make a car drive around randomly.
 *  
 * @author  Kim Bjerge
 * @version 23.08.08
 */
public class MoveForward extends Behavior 
{
    public MoveForward( String name, int LCDrow, Behavior b)
    {
    	super(name, LCDrow, b);
    }
    	
    public void run()
    {    	
    	while (true)
    	{ 
    	   // This behavior moves the robot forward all time
    	   suppress();
    	   drawString("f");
    	   forward();
     	   release();

    	   delay(500); // Wait 0.5 sec
    	   
    	}
    }
}
