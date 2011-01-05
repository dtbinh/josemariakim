
import lejos.robotics.Pose;

/**
 * The object location class handles 
 * converting pose of robot #1 to position 
 * in relation to object in where robot #2 can 
 * find the object whit a heading of 0
 *  
 * @author  Kim Bjerge
 * @version 29.12.10
 */
public class ObjectLocation {

	Pose robot1Pose;
	Pose robot2Pose;
	// X direction offset to let the PID control 
	// handle getting close to the object
    private final float x_offset = 200; // Offset to object [mm]
	float radius;
	
	ObjectLocation(float x, float y, float heading, float r)
	{
		robot1Pose = new Pose(x, y, heading);
		radius = r;
		robot2Pose = null;
	}
	
	Pose GetRobot1Pose()
	{
		return robot1Pose;
	}
	
	Pose GetRobot2Pose()
	{
		double a, ar, yr = 0.0, xr = 0.0;
		double head = robot1Pose.getHeading();
		double x = Math.abs(robot1Pose.getX());
		double y = Math.abs(robot1Pose.getY());
		double r = radius;
	
		if (robot2Pose == null)
		{
			// Robot 1 in position A of object
			if ((head >= -180) && (head < -90))
			{
			   a = 180 - Math.abs(head); 
			   ar = Math.abs(a) * Math.PI/180;
			   yr = y + r*Math.sin(ar);
			   xr = x - r*(1 + Math.cos(ar));
			}
		
			// Robot 1 in position B of object
			if ((head >= -90) && (head < 0))
			{
			   a = Math.abs(head);
			   ar = Math.abs(a) * Math.PI/180;   
			   yr = y + r*Math.sin(ar);
			   xr = x - r*(1 - Math.cos(ar));
			}
		
			// Robot 1 in position C of object
			if ((head > 90) && (head <= 180))
			{
			   a = 180 - Math.abs(head);
			   ar = Math.abs(a) * Math.PI/180;   
			   yr = y - r*Math.sin(ar);
			   xr = x - r*(1 + Math.cos(ar));
			}
		
			// Robot 1 in position D of object
			if ((head >= 0) && (head <= 90))
			{
			   a = Math.abs(head);
			   ar = Math.abs(a) * Math.PI/180;   
			   yr = y - r*Math.sin(ar); 
			   xr = x - r*(1 - Math.cos(ar));
			}
			
			// Subtract offset from x coordinate  
			if (xr >= x_offset) xr -= x_offset;
			else xr = 0;
			
			// Robot #2 default heading 0 degrees
			robot2Pose = new Pose((float)xr, (float)-yr, (float)0.0);
		}
		
		return robot2Pose;
	}
}
