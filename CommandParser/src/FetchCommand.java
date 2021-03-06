
public class FetchCommand extends Command_parser {

	public FetchCommand(DataLogger_parser logger)
	{
		super(logger);
	}
	public FetchCommand(DataLogger_parser logger, int id, float x, float y, float heading, int probability) 
	{
		super(logger);
		commandID = "FETCH";
		
		parameters.put("ID", Integer.toString(id));
		parameters.put("X", Float.toString(x));
		parameters.put("Y", Float.toString(y));
		parameters.put("H", Float.toString(heading));
		parameters.put("P", Integer.toString(probability));
	}
	
	public int getID()
	{
		String id = (String) parameters.get("ID");
		return Integer.parseInt(id);
	}
	
	public float getX()
	{
		String x = (String) parameters.get("X");
		return Float.parseFloat(x);
	}
	
	public float getY()
	{
		String y = (String) parameters.get("Y");
		return Float.parseFloat(y);
	}
	
	public float getHeading()
	{
		String h = (String) parameters.get("H");
		return Float.parseFloat(h);
	}
	
	public int getProbability()
	{
		String p = (String) parameters.get("P");
		return Integer.parseInt(p);
	}
	
	

}
