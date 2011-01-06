package communication;


public class DeliverCommand extends Command {

	public static String ID = "DELIVER";
	public DeliverCommand(DataLogger logger)
	{
		super(logger);
		commandID = ID;
	}
	public DeliverCommand()
	{
		super();
		commandID = ID;
	}
	public DeliverCommand(DataLogger logger, float x, float y) 
	{
		super(logger);
		commandID = ID;
		
		parameters.put("Y", Float.toString(y));
		parameters.put("X", Float.toString(x));
	}
	
	public DeliverCommand(float x, float y) 
	{
		this(null, x, y);		
	}
	
	public DeliverCommand(Command command)
	{
//		if(command.getCommandID() == ID )
//		{
			this.commandID = ID;
			this.dataLogger = command.dataLogger;
			this.parameters = command.parameters;
//		}
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
	

}
