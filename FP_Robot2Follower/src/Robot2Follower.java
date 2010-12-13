import lejos.nxt.*;

public class Robot2Follower 
{
    public static void main(String [] args)
    {    	
    	AvoidFront af;
    	LightFollower lf;
    	
        LCD.drawString("Sound Car",0,0);
        LCD.refresh();
    	
    	lf = new LightFollower("Light", 3, null);
    	af = new AvoidFront ("Avoid", 5,lf);
    	
       	af.start();
       	lf.start();

    	while (! Button.ESCAPE.isPressed())
    	{
    		af.reportState();
    		lf.reportState();
    	}
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

    }    
}
