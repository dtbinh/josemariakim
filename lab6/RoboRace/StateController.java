import lejos.nxt.*;

public class StateController extends Thread
{
	LineFollowerPID lineFollower;
	UltrasonicSensor us;
    private final int dT = 10;   // ms
    int dist;
    //private int state = 21;
    private int state = 1;
    private int time = 0;
	   
	public StateController(LineFollowerPID lf) {
		lineFollower = lf;
	    us = new UltrasonicSensor(SensorPort.S1);
	}
	
	// Returns true if above threshold detecting 
	// Tilting of car at plateau
	private boolean lookForThreshold(int dist, int threshold)
	{
	   boolean atThreshold = false;
	   
	   if (dist > threshold)
		   atThreshold = true;

	   return atThreshold;
	}

	//Car.stop();
	//Thread.sleep(1000);
	
	private void handelStates() throws InterruptedException
    {
 	    dist = Car.updateTachoCounter();
 		
 	    LCD.drawString("Tacho ", 0, 7);
 	    LCD.drawInt((int)(dist),4,10,7);
	    
    	switch (state)
    	{
    		case 1: // Moving up first time track 1
    			if (lookForThreshold(dist, 2100)) // Track 1
    			{
    			    lineFollower.pause(true);
    			    time = 0;
    				state = 2;
    			}
   			break;
    		case 2: // At plateau no. 1
    			if (time > 150)
    			{
    			    state = 3;
    			}
    			else
    				Car.forward(60, 35); // Turn on plateau no. 1
    			break;
    		case 3:
    			if (lineFollower.sensor.black())
    			{
    				//Car.stop();
    				//sleep(500);
    				Car.resetTachoCounter();
    			    lineFollower.pause(false);
    				time = 0;
    			    state = 10;    				
    			}
    			else 
    				Car.forward(55, 30); // Turn on plateau no. 1
    			break;
    		case 10: // Moving up second time track 2
    			if (lookForThreshold(dist, 2100)) // Track 2
    			{
    			    lineFollower.pause(true);
    			    time = 0;
    			    state = 11;
    			}    			
    			break;
       		case 11: // At plateau no. 2
    			if (time > 150)
    			{
    			    state = 12;
    			}
    			else
    				Car.forward(35, 60); // Turn on plateau no. 2
    			break;
    		case 12:
    			if (lineFollower.sensor.black())
    			{
    				//Car.stop();
    				//sleep(500);
    				Car.resetTachoCounter();
    				lineFollower.setRightSideOfLine();
    			    lineFollower.pause(false);
    				time = 0;
    			    state = 20;    				
    			}
    			else 
    				Car.forward(30, 40); // Turn on plateau no. 1
    			break;
     		case 20: // Moving up third time track 3
    			if (lookForThreshold(dist, 2400)) // Track 3
    			{
    			    lineFollower.pause(true);
    				Car.stop();
    				sleep(500);
    			    time = 0;
    			    state = 21;
    			}    			
    			break;
    		case 21: // At plateau no. 3
    			if (time > 75)
    			{
    				Car.stop();
    				sleep(1000);
    				Car.resetTachoCounter();
    				lineFollower.setLeftSideOfLine();
    				lineFollower.setSpeed(60);
    			    lineFollower.pause(false);
    				state = 30;
    			}
    			else
    				Car.turn(50, 50);
    			break;
            case 30: // Moving down track 3
    			if (lookForThreshold(dist, 2300)) // Track 3
    			{
    			    lineFollower.pause(true);
    				Car.stop();
    				sleep(500);
    			    time = 0;
    			    state = 31;
    			}    			
            	break;
            case 31:
            	break;
    	}
    }

	public void run ()
	{
		Car.resetTachoCounter();
	    //lineFollower.pause(true);
		
	    while (true)
	     {
	       try  {
	    	   handelStates();
	    	   Thread.sleep(dT); 
	    	   time++;
	       } catch (InterruptedException ie)  {	   
	       }   
	     }     
		
	}
}
