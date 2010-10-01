import lejos.nxt.*;
/**
 * RoboRace car - PID controlled line follower 
 * and state machine to control turns and stop at end of game
 * 
 * @author  Kim Bjerge
 * @version 01.10.2010
 */
public class RoboRace implements ButtonListener 
{
  private static int dT = 1000;   // seconds
  private boolean keepItRunning = true;
  private boolean pause = false;
  private static LineFollowerPID lineFollower;

  public RoboRace() {
  }

  public void buttonPressed(Button b){
	  
	  if (b == Button.ESCAPE)
		  keepItRunning = false;
	  
	  if (b == Button.ENTER)
	  {
		  pause = !pause;
	      lineFollower.pause(pause);
	  }
  }

  public void buttonReleased(Button b){}
  
  public void run() throws Exception
  {
 	 int time = 0; 
 	 Button.ESCAPE.addButtonListener(this);
 	 Button.ENTER.addButtonListener(this);
     
 	 while (keepItRunning)
     {    	 
	     LCD.drawInt(lineFollower.getLight(),4,10,2);
	     LCD.drawInt(time++,4,10,3);
	     LCD.refresh();     	     	     
	     Thread.sleep(dT);
     }
     
     Car.stop();
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     LCD.refresh();    
  }
  
  public static void main (String[] aArg)
  throws Exception
  {	 
	 lineFollower = new LineFollowerPID(); 
	 lineFollower.calibrate();
	 
	 while (Button.ENTER.isPressed());
	 LCD.drawString("Press ENTER     ", 0, 0);
	 LCD.drawString("to start        ", 0, 1);
	 LCD.drawString("Robot Race      ", 0, 2);
	 while (!Button.ENTER.isPressed());
	 while (Button.ENTER.isPressed());
	 
     LCD.clear();
	 LCD.drawString("JoseMariaKim    ", 0, 0);
	 LCD.drawString("Robot Race      ", 0, 1);
     LCD.drawString("Light: ", 0, 2); 
     LCD.drawString("Time [sec]: ", 0, 3); 
  
     lineFollower.setDaemon(true);
     lineFollower.start();
   	 
     RoboRace roboRace = new RoboRace();
     roboRace.run();
     
   }
}
