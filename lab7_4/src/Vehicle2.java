import lejos.nxt.*;
/**
 * RoboRace car - PID controlled line follower 
 * and state machine to control turns and stop at end of game
 * 
 * @author  Kim Bjerge
 * @version 01.10.2010
 */
public class Vehicle2 implements ButtonListener 
{
  private static int dT = 1000;   // seconds
  private boolean keepItRunning = true;
  private boolean stopped = false;
  private static LightCtrMotor leftLightCtrMotor;
  private static LightCtrMotor rightLightCtrMotor;

  public Vehicle2() {
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
	     LCD.drawInt(leftLightCtrMotor.getValue(),4,10,2);
	     LCD.drawInt(rightLightCtrMotor.getValue(),4,10,3);
	     LCD.drawInt(time++,4,10,4);
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
	 LCD.drawString("vehicle2+3      ", 0, 2);
	 while (!Button.ENTER.isPressed());

	 LCD.clear();
	 LCD.drawString("JoseMariaKim    ", 0, 0);
	 LCD.drawString("Vehicle2+3      ", 0, 1);
     LCD.drawString("LigthLeft:      ", 0, 2); 
     LCD.drawString("LigthRight:     ", 0, 3); 
     LCD.drawString("Time [sec]:     ", 0, 4); 
  
     leftLightCtrMotor = new LightCtrMotor(SensorPort.S1, MotorPort.C); 
     leftLightCtrMotor.setDaemon(true);
     leftLightCtrMotor.start();
     
     rightLightCtrMotor = new LightCtrMotor(SensorPort.S4, MotorPort.B); 
     rightLightCtrMotor.setDaemon(true);
     rightLightCtrMotor.start();
   	 
     Vehicle2 vehicle = new Vehicle2();
     vehicle.run();
     
   }
}
