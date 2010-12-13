
import java.util.*;
import lejos.nxt.*;

public class Command_parser 
{
	protected String commandID;
	protected Hashtable parameters;
	protected DataLogger_parser dataLogger;
	protected boolean logging;
	
	public Command_parser(DataLogger_parser logger)
	{
		commandID = "";
		parameters = new Hashtable();
		dataLogger = logger;
		logging = (this.dataLogger != null);
	}

	
 	public void Deserialize(String frame)
 	{
 		if(logging) this.dataLogger.writeLine("Parsing the command");
		this.commandID = "";
		LCD.drawString("creating command", 0, 0);
		this.parameters = new Hashtable();
		if(frame.indexOf("FETCH")> -1)
		{
			this.commandID = "FETCH";
			if(logging) this.dataLogger.writeLine(commandID);
			LCD.drawString(commandID, 0, 0);
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
