import lejos.nxt.*;
/**
 * RoboRace car - PID controlled line follower 
 * and state machine to control turns and stop at end of game
 * 
 * @author  Kim Bjerge
 * @version 01.10.2010
 */
public class Vehicle3 implements ButtonListener 
{
  private static int dT = 1000;   // seconds
  private boolean keepItRunning = true;
  private boolean stopped = false;
  private static SoundCtrMotor leftSoundCtrMotor;
  private static SoundCtrMotor rightSoundCtrMotor;
  private static LightCtrMotor leftLightCtrMotor;
  private static LightCtrMotor rightLightCtrMotor;

  public Vehicle3() {
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
 	 Button.ESCAPE.addButtonListener(this);
 	 Button.ENTER.addButtonListener(this);
 	 Button.LEFT.addButtonListener(this);
 	 Button.RIGHT.addButtonListener(this);
     
 	 while (keepItRunning)
     {    	 
	     LCD.drawInt(leftSoundCtrMotor.getValue(),4,10,2);
	     LCD.drawInt(rightSoundCtrMotor.getValue(),4,10,3);
	     LCD.drawInt(leftLightCtrMotor.getValue(),4,10,4);
	     LCD.drawInt(rightLightCtrMotor.getValue(),4,10,5);
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
	 LCD.drawString("vehicle3        ", 0, 2);
	 while (!Button.ENTER.isPressed());

	 LCD.clear();
	 LCD.drawString("JoseMariaKim    ", 0, 0);
	 LCD.drawString("Vehicle3        ", 0, 1);
     LCD.drawString("leftSound:      ", 0, 2); 
     LCD.drawString("rightSound:     ", 0, 3); 
     LCD.drawString("leftLight:      ", 0, 4); 
     LCD.drawString("rightLight:     ", 0, 5); 
  
     leftSoundCtrMotor = new SoundCtrMotor(SensorPort.S2, MotorPort.B); 
     leftSoundCtrMotor.setDaemon(true);
     leftSoundCtrMotor.start();
     
     rightSoundCtrMotor = new SoundCtrMotor(SensorPort.S3, MotorPort.C); 
     rightSoundCtrMotor.setDaemon(true);
     rightSoundCtrMotor.start();
     
     leftLightCtrMotor = new LightCtrMotor(SensorPort.S1, MotorPort.B); 
     leftLightCtrMotor.setDaemon(true);
     leftLightCtrMotor.start();
     
     rightLightCtrMotor = new LightCtrMotor(SensorPort.S4, MotorPort.C); 
     rightLightCtrMotor.setDaemon(true);
     rightLightCtrMotor.start();
 	 
     Vehicle3 vehicle = new Vehicle3();
     vehicle.run();
     
   }
}