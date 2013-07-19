package utilities;
import java.io.IOException;

import core.Task;

public class Signal {
	private static String lastSignal;

	
	public static void sendSignal(String type, String imagerie)
	{
		if (imagerie == "IO")
		{
			if (type != lastSignal)
			{
				try {
					String cmd = "C:\\Ruby186\\bin\\ruby.exe C:/ruby_executable/send.rb " + converter(type);
					Runtime.getRuntime().exec(cmd);
					WriteLog.writingStuff("Signal : " + converter(type), "data/console_" + Task.mainTask.getSujetID() + ".txt");
				} catch (IOException e) {
					WriteLog.writingStuff("Signal : " + converter(type) + " " +  e.getMessage(), "data/console_" + Task.mainTask.getSujetID() + ".txt");
				}
				lastSignal = type;
			}
		}
	}
	
	private static int converter (String s)
	{
		if (s == "start")
			return 0;
		else if (s == "SPG")
			return 1;
		else if (s == "SPD")
			return 2;
		else if (s == "SM")
			return 3;
		else if (s == "DM")
			return 4;
		else if (s == "instruction")
			return 5;
		else if (s == "pause")
			return 6;	
		else if (s=="urgency")
			return 7;
		else
			return 7;
	}
}
