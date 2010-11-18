import lejos.robotics.subsumption.*;
import lejos.nxt.*;

/**
 * Demonstration of the Behavior subsumption classes.
 * 
 * Requires a wheeled vehicle with two independently controlled
 * motors connected to motor ports A and C, and 
 * a touchmsensor connected to sensor  port 1 and
 * an ultrasonic sensor connected to port 3;
 * 
 * @author Brian Bagnall and Lawrie Griffiths, modified by Roger Glassey
 *
 */
public class BumperCar
{

  public static void main(String[] args)
  {
    Motor.A.setSpeed(400);
    Motor.C.setSpeed(400);
    Behavior b1 = new DriveForward();
    Behavior b2 = new DetectWall();
    Behavior b3 = new ExitBehavior();
    Behavior[] behaviorList =
    {
      b1, b2, b3
    };
    Arbitrator arbitrator = new Arbitrator(behaviorList);
    LCD.drawString("Bumper Car",0,1);
    Button.waitForPress();
    arbitrator.start();
  }
}


class DriveForward implements Behavior
{

  private boolean _suppressed = false;

  public boolean takeControl()
  {
    return true;  // this behavior always wants control.
  }

  public void suppress()
  {
    _suppressed = true;// standard practice for suppress methods
  }

  public void action()
  {
    _suppressed = false;
    Motor.A.forward();
    Motor.C.forward();
    while (!_suppressed)
    {
      Thread.yield(); //don't exit till suppressed
    }
    Motor.A.stop(); // not strictly necessary, but good probramming practice
    Motor.C.stop();
  }
}


class DetectWall implements Behavior
{
  private TouchSensor touch;
  private UltrasonicSensor sonar;
  private boolean _ultrasonicDetected = false;
  private Detect detect;

  public DetectWall()
  {
    touch = new TouchSensor(SensorPort.S4);
    sonar = new UltrasonicSensor(SensorPort.S1);
    detect = new Detect();
    detect.setDaemon(true);
    detect.start();
  }
    
  public boolean takeControl()
  {
    return touch.isPressed() || _ultrasonicDetected;
  }

  public void suppress()
  {
  }

  public void action()
  { 
    synchronized (detect)
    {
   	  detect.startAction();
    }
	while (!detect.isCompleted())
	{
      Thread.yield(); //don't exit before completing stateMachine
	}
  }
  
  
  private class Detect extends Thread
  {
	  private int _counter;
	  private int _state = 0;

	  private void startAction()
	  {
		  _state = 1;
	  }
	  private boolean isCompleted()
	  {
		  return (_state == 0);
	  }
	  
	  private void pingWaitDetect()
	  {
	    sonar.ping();
	    Sound.pause(20);
	    LCD.drawInt(sonar.getDistance(),10,2);
	    _ultrasonicDetected = (sonar.getDistance() < 25);	  
	  }

	  public void stateMachine()
	  {
        synchronized (this)
        {
		  if (touch.isPressed())
		  {
			  Motor.A.stop(); // Move backward 1 sec. before turning
			  Motor.C.stop();
			  _state = 0;
		  }

		  switch (_state)
		  {
		  case 0: // Idle
			  break;
		  case 1: // Start moving backward
			    Motor.A.backward(); // Move backward 1 sec. before turning
			    Motor.C.backward();
			    _counter = 0;
			    _state = 2;
			  break;
		  case 2: // Moving backward 1 sec
			  if (_counter++ == 25) // 1 sec.
			     _state = 3;
			  break;
		  case 3: // Rotating
				Motor.A.rotate(-180, true);// start Motor.A rotating backward
			    Motor.C.rotate(-360);  // rotate C farther to make the turn
			    _state = 0;
			  break;  
		  }
        }
	  }

	  public void run()
	  {
		  while(true)
		  {
			  pingWaitDetect();
			  // Called every 20 ms
			  stateMachine();
		  }
	  }
  }
}


class ExitBehavior implements Behavior
{

  public ExitBehavior()
  {
  }

  public boolean takeControl()
  {
	  return Button.ENTER.isPressed();
  }

  public void suppress()
  {
    //Since  this is highest priority behavior, suppress will never be called.
  }

  public void action()
  { 
	  System.exit(0);
  }
}

