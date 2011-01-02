package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

import lejos.nxt.LCD;

public class Utils 
{
	public static void send(DataOutputStream dos, String command)	
	throws IOException 
	{
		dos.writeChars(command);
		dos.flush();
	}

	public static String receive(DataInputStream dis)
	throws IOException 
	{
		
		char c = dis.readChar();
		String response = "";
		while(c!='\n'){
			response  += c;
			c = dis.readChar();
		}
		return response;
	}
	
	public static String getFileName(String prefix) throws InterruptedException
	{
		int index = 0;
		
		File file;
		String fileName = prefix +  Integer.toString(index) + ".txt";
		
		for(int i = 0; i < 100; i++)
		{
			fileName = prefix +  Integer.toString(index) + ".txt";
			file = new File(fileName);
			if(file.exists()) //check if a file with that name already exists
				index++;
			else
				break;
		}
			
		return fileName;
	}
	
	public static void writeUpperLineToLCD(String line) {
		LCD.clear();
		LCD.drawString(line, 0, 0);
		LCD.refresh();
	}
}
