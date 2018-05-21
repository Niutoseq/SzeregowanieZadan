import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Graphic
{
  public static TaskManager tm = new TaskManager();
  public static MachineManager mm = new MachineManager();

  public static String color1 = "firebrick";
  public static String color2 = "rosybrown1";

  public static void makeSchedule()
  {
    String filenameIn = "schedule.gv";
    String filenameOut = "schedule.ps";
    prepareSchedule(mm.machines);

    try
    {
      Runtime.getRuntime().exec("dot -Tps graphic/in/" + filenameIn + " -o graphic/out/" + filenameOut);
    }
    catch (Exception e)
    {
      System.out.println("Tworzenie harmonogramu nie powiodło się!");
    }

    try
    {
      ProcessBuilder pb = new ProcessBuilder("xdg-open", "graphic/out/" + filenameOut);
      pb.start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void prepareSchedule(ArrayList<Machine> machinesToShedule)
  {
    String mySchedule = new String();
    String begin = "digraph struct {\n";
    begin = begin + "node [shape=record];\n";
    String body = new String();
    String result = new String();
    String end = "}";

    result = result + "struct1 [shape=record, color=" + color1 + ",";
    result = result + " style=filled, fillcolor=" + color2 + ", label=\"";

    int time = 0;
    int longestSchedule = 0;

    result = result + "{Time|";
    for (Machine machine : mm.machines)
    {
      result = result + mm.globalMachineName + machine.getMachineNumber() + "|";
      if (machine.getSchedule().size() > longestSchedule)
      {
        longestSchedule = machine.getSchedule().size();
      }
    }
    result = result.substring(0, result.length() - 1);
    result = result + "}|{";

    for (int i = 0; i < longestSchedule; i++)
    {
      result = result + time + "-" + (time+1) + "|";
      for (Machine machine : mm.machines)
      {
        if (machine.getSchedule().get(i).getTaskNumber() == 0)
        {
          result = result + "-" + "|";
        }
        else
        {
          result = result + tm.globalTaskName + machine.getSchedule().get(i).getTaskNumber() + "|";
        }
      }
      result = result.substring(0, result.length() - 1);
      result = result + "}|{";
      time++;
    }
    result = result.substring(0, result.length() - 2);
    result = result + "\"];\n";
    body = result;
    mySchedule = begin + body + end;
    // System.out.println(mySchedule);

    try {
      PrintWriter writer = new PrintWriter("graphic/in/schedule.gv", "UTF-8");
      writer.println(mySchedule);
      writer.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
