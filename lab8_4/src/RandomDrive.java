/**
 * A behavior that make a car drive around randomly.
 *  
 * @author  Ole Caprani
 * @version 23.08.08
 */
public class RandomDrive extends Behavior 
{
    public RandomDrive( String name, int LCDrow, Behavior b)
    {
    	super(name, LCDrow, b);
    }
    	
    public void run()
    {    	
    	while (true)
    	{ 
    	   suppress();
    	   
    	   drawString("f");
    	   forward((int)(50+50*Math.random()),(int)(50+50*Math.random()));
    	   delay((int)(100+300*Math.random()));
    	   
    	   drawString("s");
    	   stop();
    	   delay((int)(1500+1000*Math.random()));
    	   
    	   release();
    	}
    }
}
