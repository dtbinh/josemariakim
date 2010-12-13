import lejos.nxt.*;
/*
* Monitor version of Ultrasonic Sensor
*  
* @author  Kim Bjerge
* @version 7.12.10
*/
public class SyncUltrasonicSensor 
{
    private UltrasonicSensor delegateUS ;

    public SyncUltrasonicSensor (I2CPort port)
    {
    	this.delegateUS = new UltrasonicSensor(port);
    }
    
    public synchronized int getDistance()
    {
    	return delegateUS.getDistance();
    }
}