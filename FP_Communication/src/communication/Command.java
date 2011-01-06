package communication;


import java.util.*;

public class Command 
{
	protected String commandID;
	protected Hashtable parameters;
	protected DataLogger dataLogger;
	protected boolean logging;
	
	public Command(DataLogger logger)
	{
		commandID = "";
		parameters = new Hashtable();
		dataLogger = logger;
		logging = (this.dataLogger != null);
	}

	public Command()
	{
		this(null);
	}
	
 	public void Deserialize(String frame)
 	{
 		//log if we are debugging
 		if(logging) this.dataLogger.writeLine("Parsing the command");
 		
 		//parse the command ID
		if(frame.indexOf("FETCH")> -1)
		{
			this.commandID = FetchCommand.ID;
		}
		else if(frame.indexOf("ACK")> -1)
		{
			this.commandID = ACKCommand.ID;
		}
		else if(frame.indexOf("DELIVER")> -1)
		{
			this.commandID = DeliverCommand.ID;
		}
		else
		{
			this.commandID = "";
		}
		if(logging) this.dataLogger.writeLine(commandID);
		
		//Parse the parameters
		this.parameters = new Hashtable();
		int index = frame.indexOf(' ');
		int start = index +1;
		index = frame.indexOf(' ', start);
		while(index > -1)
		{
			String parameter = frame.substring(start, index);
			parseParameter(parameter);
			
			start = index + 1;
			index = frame.indexOf(' ', start);
		}
		//add the last parameter, if any
		if(frame.indexOf('=', start) > -1)
		{
			String parameter = frame.substring(start);
			parseParameter(parameter);
			
		}
		
		if(logging) this.dataLogger.writeLine("Command parsed");
		
 	}
	
 	public String Serialize()
 	{
 		String frame = "";
 		frame += commandID;
 		
 		Enumeration keys = parameters.keys();
 		while(keys.hasMoreElements())
 		{
 			String id = (String) keys.nextElement();
 			frame += " " + id + "=" + (String)parameters.get(id);
 		}
 		frame += "\n";
 		return frame;
 	}
 	
 	private void parseParameter(String parameter) {
		if(logging) this.dataLogger.writeLine(parameter);
		String parameterID = parameter.substring(0, parameter.indexOf('='));
		String parameterValue = parameter.substring(parameter.indexOf('=') + 1);
		if(logging) this.dataLogger.writeLine(parameterID + " : " + parameterValue);
		parameters.put(parameterID, parameterValue);
	}
	
	public String getCommandID()
	{
		return this.commandID;
	}
	
	public void setCommandID(String cID)
	{
		commandID = cID;
	}
	
	public Hashtable getParameters()
	{
		return this.parameters;
	}
	
	public void addParameter(String paramID, String paramValue)
	{
		this.parameters.put(paramID, paramValue);
	}
	
	public String getParameter(String paramID)
	{
		return (String) this.parameters.get(paramID);
	}
	
	public String toString()
	{
		String result = "";
		result += "Command ID : ";
		result += commandID;
		result += '\n';
		
		result += "Parameters: \n";
		Enumeration keys = parameters.keys();
		while(keys.hasMoreElements())
		{
			String paramID = (String) keys.nextElement();
			String paramValue = (String) parameters.get(paramID);
			
			result += paramID;
			result += " : ";
			result += paramValue;
			result += "\n";
		}
		return result;
		
	}
	
}
