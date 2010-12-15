import lejos.nxt.*;

public class Robot2Gripper 
{
    public static void main(String [] args)
    {    	
    	AvoidFront af;
    	FindObject fo;
    	
        LCD.drawString("Robot 2 Gripper",0,0);
        LCD.refresh();
    	
        fo = new FindObject("Find", 3, null);
    	af = new AvoidFront ("Avoid", 5,fo);

		while (Button.ENTER.isPressed());	
    	LCD.drawString("Press ENTER to  ", 0, 0);
		LCD.drawString("calibrate       ", 0, 1);
		while (!Button.ENTER.isPressed())
			fo.Calibrate(); 
		
    	LCD.clearDisplay();
        LCD.drawString("Robot 2 Gripper",0,0);
    	
       	af.start();
		fo.start();

    	while (! Button.ESCAPE.isPressed())
    	{
    		af.reportState();
    		fo.reportState();
    	}
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

    }    
}
