import lejos.nxt.*;
/**
 * RoboRace car - PID controlled line follower 
 * and state machine to control turns and stop at end of game
 * 
 * @author  Kim Bjerge
 * @version 01.10.2010
 */
public class Vehicle1 implements ButtonListener 
{
  private static int dT = 1000;   // seconds
  private boolean keepItRunning = true;
  private boolean stopped = false;
  private static SoundCtrMotor soundCtrMotor;

  public Vehicle1() {
  }

  public void buttonPressed(Button b){
	  
	  if (b == Button.ESCAPE)
		  keepItRunning = false;
	  
	  if (b == Button.ENTER)
	  {
		  stopped = !stopped;
		  //soundCtrMotor.stop(stopped);
	  }
	  if (b == Button.LEFT)
	  {
	  }
	  if (b == Button.RIGHT)
	  {
	  }
  }

  public void buttonReleased(Button b){}
  
  public void run() throws Exception
  {
 	 int time = 0; 
 	 Button.ESCAPE.addButtonListener(this);
 	 Button.ENTER.addButtonListener(this);
 	 Button.LEFT.addButtonListener(this);
 	 Button.RIGHT.addButtonListener(this);
     
 	 while (keepItRunning)
     {    	 
	     LCD.drawInt(soundCtrMotor.getValue(),4,10,2);
	     LCD.drawInt(time++,4,10,3);
	     LCD.refresh();     	     	     
	     Thread.sleep(dT);
     }
     
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     LCD.refresh();    
  }
  
  public static void main (String[] aArg)
  throws Exception
  {	 
	 
	 while (Button.ENTER.isPressed());
	 LCD.drawString("Press ENTER     ", 0, 0);
	 LCD.drawString("to start        ", 0, 1);
	 LCD.drawString("vehicle1        ", 0, 2);
	 while (!Button.ENTER.isPressed());

	 LCD.clear();
	 LCD.drawString("JoseMariaKim    ", 0, 0);
	 LCD.drawString("Vehicle1        ", 0, 1);
     LCD.drawString("Sound:          ", 0, 2); 
     LCD.drawString("Time [sec]:     ", 0, 3); 
  
	 soundCtrMotor = new SoundCtrMotor(SensorPort.S2, MotorPort.C); 
     soundCtrMotor.setDaemon(true);
     soundCtrMotor.start();
   	 
     Vehicle1 vehicle = new Vehicle1();
     vehicle.run();
     
   }
}
