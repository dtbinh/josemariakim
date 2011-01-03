import communication.DataLogger;

import lejos.nxt.*;
/**
 * Behavior: Avoid object
 *  
 * @author  Kim Bjerge
 * @version 7.12.10
 */
public class PlaySound extends Behavior 
{    
    private int vol = 10;
    private int dur = 10;
    private int freq = 10;
    private int puls = 0;

    private SyncUltrasonicSensor us ;
           
    public PlaySound( String name, int LCDrow, Behavior b, SyncUltrasonicSensor uSensor, DataLogger logger)
    {
    	super(name, LCDrow, b, logger);
        this.us = uSensor;
    }
   
    public void PlaySong()
    {
        drawString("s");    
        //  Composition: Ole Caprani
        int times = 5+(int)(20*Math.random());
        for ( int i = 0; i < times; i++ )
        {
             vol = (vol + 7) % 100 + 20;
             dur = (dur + 11) % 100;
             freq = (freq + 40) % 100;
             if ( puls == 0 )
                Sound.playTone(40, 10, 50);
             else
                Sound.playTone(freq, dur, vol);
             puls = (puls + 1) % 12;
             delay(100);   			  
 		}
        drawString(" ");  	
    }
    
    public void run() 
    { 
    	int distance;
    	
        while (true)
        {
            distance = us.getDistance();
            if ( distance < closeThreshold )
            {
                suppress();
            	PlaySong();
                release();	
            }
            delay(1000);   			  
       }
    }
}


