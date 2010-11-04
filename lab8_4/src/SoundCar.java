import lejos.nxt.*;

public class SoundCar 
{
    public static void main(String [] args)
    {    	
    	RandomDrive rd;
    	AvoidFront af;
    	PlaySounds ps;
    	LightFollower lf;
    	
        LCD.drawString("Sound Car",0,0);
        LCD.refresh();
    	
    	rd = new RandomDrive("Drive",1, null);
    	ps = new PlaySounds ("Play ",2,rd);
    	lf = new LightFollower("Light", 3, ps);
    	af = new AvoidFront ("Avoid",4,lf);
    	
    	rd.start();
       	af.start();
       	lf.start();
    	ps.start();

    	while (! Button.ESCAPE.isPressed())
    	{
    		rd.reportState();
    		af.reportState();
    		lf.reportState();
    		ps.reportState();
    	}
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

    }    
}
