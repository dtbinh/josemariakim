package communication;


public class FetchCommand extends Command {

	private String ID = "FETCH";
	public FetchCommand(DataLogger logger)
	{
		super(logger);
		commandID = ID;
	}
	public FetchCommand()
	{
		super();
		commandID = ID;
	}
	public FetchCommand(DataLogger logger, int id, float x, float y, float heading, float radius) 
	{
		super(logger);
		commandID = ID;
		
		parameters.put("ID", Integer.toString(id));
		parameters.put("X", Float.toString(x));
		parameters.put("Y", Float.toString(y));
		parameters.put("H", Float.toString(heading));
		parameters.put("R", Float.toString(radius));
	}
	
	public FetchCommand(int id, float x, float y, float heading, float radius) 
	{
		this(null, id, x, y, heading, radius);		
	}
	
	public FetchCommand(Command command)
	{
		//if(command.getCommandID() == ID )
		{
			this.commandID = ID;
			this.dataLogger = command.dataLogger;
			this.parameters = command.parameters;
		}
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
	
	public float getRadius()
	{
		String r = (String) parameters.get("R");
		return Float.parseFloat(r);
	}
	
	

}
