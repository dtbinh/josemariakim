import lejos.nxt.*;
import lejos.nxt.LCD;

public class Main {

	private static ColorSensor cs;


	public static void main(String[] args) throws InterruptedException {

		LCD.drawString("Testing the color sensor", 0, 2);

		cs = new ColorSensor(SensorPort.S1);

		while (!Button.ESCAPE.isPressed())
		{
			if (cs.black())
			{
				LCD.clear();
				LCD.drawString("Black color detected",0,2);
			}
			if (cs.white())
			{
				LCD.clear();
				LCD.drawString("White color detected", 0, 2);
			}
			if (cs.green())
			{
				LCD.clear();
				LCD.drawString("Green color detected", 0, 2);
			}
			Thread.sleep(10); // Sampling period of 10 ms
		}

	}

}
