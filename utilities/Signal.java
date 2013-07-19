package utilities;
import java.io.IOException;

import core.Task;

public class Signal {
	private static String lastSignal;

	
	public static void sendSignal(String type, String imagerie)
	{
		if (imagerie == "IO" || imagerie == "EEG" )
		{
			if ((imagerie == "IO" && type != lastSignal)||imagerie == "EEG" )
			{
				try {
					String cmd = "C:\\Ruby186\\bin\\ruby.exe C:/ruby_executable/send.rb " + converter(type, imagerie);
					Runtime.getRuntime().exec(cmd);
					WriteLog.writingStuff("Signal : " + converter(type, imagerie), "data/console_" + Task.mainTask.getSujetID() + ".txt");
					
				} catch (IOException e) {
					WriteLog.writingStuff("Signal : " + converter(type, imagerie) + " " +  e.getMessage(), "data/console_" + Task.mainTask.getSujetID() + ".txt");
				}
				if (imagerie == "IO"){ lastSignal = type;}
			}
		}
	}
	
	private static int converter (String s)
	{
		return converter (s, "IOcw6");
	}
	
	private static int converter (String s, String imagerie)
	{
		if (imagerie == "IO")
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
		if (imagerie == "EEG")
		{
			if (s == "urgency")
				return 6;
			else if (s == "pause")
				return 5;
			else if (s == "neutre")
				return 4;
			else if (s == "bloc0back")
				return 0;
			else if (s == "bloc1back")
				return 1;
			else if (s == "bloc2back")
				return 2;
			else if (s == "bloc3back")
				return 3;
			
			else if (s == "0backMatch")
				return 10;
			else if (s == "1backMatch")
				return 11;	
			else if (s=="2backMatch")
				return 12;
			else if (s=="3backMatch")
				return 13;
			
			else if (s == "0backNotMatch")
				return 20;
			else if (s == "1backNotMatch")
				return 21;	
			else if (s=="2backNotMatch")
				return 22;
			else if (s=="3backNotMatch")
				return 23;
						
			else if (s == "goodAnsMatch")
				return 40;
			else if (s == "badAnsMatch")
				return 41;	
			else if (s=="goodAnsNotMatch")
				return 50;
			else if (s=="badAnsNotMatch")
				return 51;
		}
		return 64;
	}
}
