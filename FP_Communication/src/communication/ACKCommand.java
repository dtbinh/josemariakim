package communication;

public class ACKCommand extends Command {

	public static String ID = "ACK";
	public ACKCommand(DataLogger logger) 
	{
		super(logger);
		this.commandID = ID;
	}

	public ACKCommand() 
	{
		this.commandID = ID;
	}

}
