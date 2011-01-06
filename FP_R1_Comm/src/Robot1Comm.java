import communication.DataLogger;
import communication.Utils;

import lejos.nxt.*;
import lejos.nxt.addon.ColorSensor;
/*
* Behavior: Robot 1 finder for final project 
*  
* @author  Kim Bjerge
* @version 7.12.10
*/
public class Robot1Comm implements ButtonListener  
{
	private boolean keepItRunning = true;
	
	// Name of file to log information
	private static String filePrefix = "robot1Log";
	private static MoveForward mf;
	private static TurnAtBoarder tb;
	private static AvoidObject ao;
	private static SenseIdentifyObject sio;
	private static PlaySound ps;
   
	public void buttonPressed(Button b)
	{
	  // Exit program
	  if (b == Button.ESCAPE)
		  keepItRunning = false;
	  
	  if (b == Button.ENTER)
	  {
	  }
	  if (b == Button.LEFT)
	  {
		  tb.ResetTurns();
		  sio.ResetPose();
		  sio.Pause(false);
	  }
	  if (b == Button.RIGHT)
	  {
		  tb.ResetTurns();
		  sio.ResetPose();
		  sio.Pause(false);
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
  		mf.reportState();
		tb.reportState();
		ao.reportState();
		sio.reportState();
		ps.reportState();		 
      }
    }

	public static void main(String [] args)
	throws Exception
    {    	
		// Sensors used by Robot #1 - Light, Color and Ultrasonic
    	SyncUltrasonicSensor us =  new SyncUltrasonicSensor(SensorPort.S1);
 	    LightSensor ls = new LightSensor(SensorPort.S2);
 	    ColorSensor cs = new ColorSensor(SensorPort.S3);
        
 	    // Create file logger 
 	    String fileName;
		try {
			fileName = Utils.getFileName(filePrefix );
		} catch (InterruptedException e) {
			fileName = filePrefix + "99"; //in case of an error
		}
 	    DataLogger logger = new DataLogger(fileName);
 	    
 	    // Calibrate color sensor - white
	    while (Button.ENTER.isPressed());
	    LCD.drawString("Press ENTER to           ", 0, 0);
	    LCD.drawString("Calibrate White balance  ", 0, 1);
	    while (!Button.ENTER.isPressed());
	    cs.initWhiteBalance();
	   
	    // Calibrate color sensor - black
        while (Button.ENTER.isPressed());
	    LCD.drawString("Press ENTER to           ", 0, 0);
	    LCD.drawString("Calibrate Black balance  ", 0, 1);
	    while (!Button.ENTER.isPressed());
	    cs.initBlackLevel();
	    
        LCD.drawString("Robot 1 finder 3         ",0,0);
        LCD.refresh();

        ls.setFloodlight(true);	
        // Initialize turn and driving speeds 
	    Car.InitCar();	
	    
        // Subsumption architecture with levels of behavioral competences
        mf = new MoveForward  ("Forward ", 1, null, logger);
    	tb = new TurnAtBoarder("Turn    ", 2, mf, ls, logger);
    	ao = new AvoidObject  ("Avoid   ", 3, tb, us, logger);
    	sio = new SenseIdentifyObject
    	                      ("Identify", 4, ao, cs, us, tb, logger);
        ps = new PlaySound    ("Play    ", 5, null, us, logger);

        // Select the color to identify object
        while (Button.ENTER.isPressed());
	    LCD.drawString("Press ENTER to           ", 0, 1);
	    LCD.drawString("Select color for object  ", 0, 2);
	    while (!Button.ENTER.isPressed()) sio.setColor();

	    // Start robot
        while (Button.ENTER.isPressed());
	    LCD.drawString("Press ENTER to           ", 0, 1);
	    LCD.drawString("start robot 1 searching  ", 0, 2);
	    while (!Button.ENTER.isPressed());
	    
	    LCD.drawString("                         ", 0, 1);
	    LCD.drawString("                         ", 0, 2);
        LCD.refresh();
    	
        // Start behavior threads
    	mf.start();
    	tb.start();
       	ao.start();
       	sio.start();
       	ps.start();

       	// Start user interface thread
    	Robot1Comm robot1 = new Robot1Comm();
    	robot1.run();
    	
    	// Close log file
    	logger.close();
    	
    	LCD.clear();
        LCD.drawString("Program stopped",0,0);
        LCD.refresh();

    }    
}
