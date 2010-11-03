import lejos.nxt.*;

public class SoundCar 
{
    public static void main(String [] args)
    {    	
    	RandomDrive rd;
    	AvoidFront af;
    	PlaySounds ps;
    	
        LCD.drawString("Sound Car",0,0);
        LCD.refresh();
    	
    	rd = new RandomDrive("Drive",1, null);
    	af = new AvoidFront ("Avoid",2,rd);
    	ps = new PlaySounds ("Play ",3,af);

       	rd.start();
       	af.start();
    	ps.start();

    	while (! Button.ESCAPE.isPressed())
    	{
    		rd.reportState();
    		af.reportState();
    		ps.reportState();
    	}
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

    }    
}
