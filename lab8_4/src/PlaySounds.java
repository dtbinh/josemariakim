import lejos.nxt.*;
/**
 * A behavior that uses the build in speaker to play 
 * a simple melody every 10. second. 
 *  
 * @author  Ole Caprani
 * @version 23.08.08
 */
public class PlaySounds extends Behavior 
{	    
    private int vol = 10;
    private int dur = 10;
    private int freq = 10;
    private int puls = 0;
 	
    public PlaySounds( String name, int LCDrow, Behavior b)
    {
    	super(name, LCDrow, b);
    }
    	
    public void run(){
    	
    	while (true)
    	{     		
    	    delay(10000);
    	    
    	    suppress();
    	    
            stop();
            drawString("s");
            
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
	        
            release(); 		    
    	}
    }   
}
