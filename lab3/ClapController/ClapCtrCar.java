import lejos.nxt.*;
/**
 * Clap car controller 
 */

public class ClapCtrCar 
{
    private static int samplePeriode = 5;
    private static SoundSensor sound = new SoundSensor(SensorPort.S1);
    private static int last_read = 0;
    
    private static boolean rising = false;
	
    // This function reads sound value and looks for maximum.
    // Returns maximum based on the last 3 readings.
    private static int lookForMax() throws Exception
    {
        int read, max = 0;
        
        read = sound.readValue();
        
        if (read > last_read)
        	rising = true;
        if (read < last_read)
        {
        	if (rising) max = last_read;
        	rising = false;
        }
    	return max;
    }
    
    // This function will block while looking for a maximum peak.
    // It looks for maximum peak between min_max and max_max limits 
    // between timestart and timeend looking for the maximum.
    // If timestart = 0 the function returns when first maximum is found
    private static boolean lookForMaxInInterval(int min_max, int max_max, 
    		                                    int timestart, int timeend) throws Exception
    {
    	int count = 0; 
    	int max;
    	int cntstart = timestart/samplePeriode;
    	int cntend = timeend/samplePeriode;
    	
    	// Looking for maximum within limits
    	while (true)
    	{
    		// Wait sample period
    		count++;
    		Thread.sleep(samplePeriode);
    		
    		// Look for maximum peak
    		max = lookForMax();
            LCD.drawInt(max,4,10,0); 

    		if (min_max <= max && max <= max_max)
    		{
    			// Max found
    			if (cntstart == 0) return true;
    			if (count < cntstart) return false; // Maximum to early
    			return true; // Maximum within limits
    		}
    		if (cntstart > 0 && count > cntend)
    			return false; // Timeout looking for maximum
    	}
    	
    }
    
    // Wait for clap sound peak pattern
    // Peak 1 
    // Peak 2
    // Peak 3
    private static void waitForClapSoundPattern() throws Exception
    {
        int state = 1;
        boolean waiting = true;

        while (waiting)
        {
        	switch (state)
        	{
        		case 1:
        			if (lookForMaxInInterval(5,10,0,0)) // Peak 1
        				state = 2;
        			break;
        		case 2:
        			if (lookForMaxInInterval(5,10,0,0)) // Peak 2
        				state = 2;
        			else	
        				state = 1; // Start again looking for clap
        			break;
        		case 3:
        			if (lookForMaxInInterval(5,10,0,0)) // Peak 3
        				waiting = false;
        			else	
        				state = 1; // Start again looking for clap
        			break;
        	}
        }
    }

    public static void main(String [] args) throws Exception
    {
        LCD.drawString("dB level: ",0,0);
        LCD.refresh();
	   	   
        while (! Button.ESCAPE.isPressed())
        {
        	waitForClapSoundPattern();		    			   
            LCD.drawString("Forward ",0,1);
            Car.forward(100, 100);
		    
            waitForClapSoundPattern();		    			   
            LCD.drawString("Right   ",0,1);
            Car.forward(100, 0);
		    
            waitForClapSoundPattern();		    			   
            LCD.drawString("Left    ",0,1);
            Car.forward(0, 100);
		    
            waitForClapSoundPattern();		    			   
            LCD.drawString("Stop    ",0,1); 
            Car.stop();
       }
       Car.stop();
       LCD.clear();
       LCD.drawString("Program stopped", 0, 0);
       Thread.sleep(2000); 
   }
}
