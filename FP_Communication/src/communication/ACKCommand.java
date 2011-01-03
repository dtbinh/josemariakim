package communication;

public class ACKCommand extends Command {

	public ACKCommand(DataLogger logger) 
	{
		super(logger);
		this.commandID = "ACK";
	}

	public ACKCommand() 
	{
		this.commandID = "ACK";
	}

}
