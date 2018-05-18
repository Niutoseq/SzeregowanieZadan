import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Graphic
{
  public static TaskManager tm = new TaskManager();
  public static MachineManager mm = new MachineManager();

  public static String color1 = "midnightblue";
  public static String color2 = "lightsteelblue";

  public static void makeGraphic(String whichOne)
  {
    String filenameIn = new String();
    String filenameOut = new String();

    if (whichOne == "graph")
    {
      prepareGraph(tm.tasks);
      filenameIn = "graph.gv";
      filenameOut = "graph.ps";
    }
    else if (whichOne == "schedule")
    {
      prepareSchedule(mm.machines);
      filenameIn = "schedule.gv";
      filenameOut = "schedule.ps";
    }
    else App.closeApp("Błąd! Rodzaj grafiki nierozpoznany!");

    try
    {
      Runtime.getRuntime().exec("dot -Tps graphic/in/" + filenameIn + " -o graphic/out/" + filenameOut);
    }
    catch (Exception e)
    {
      System.out.println("Tworzenie \"" + whichOne + "\" nie powiodło się!");
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

  public static void prepareGraph(ArrayList<Task> tasksToGraph)
  {
    String begin = "strict digraph G {\n";
    String body = new String();
    String end = "}";

    for (Task task : tasksToGraph)
    {
      body = body + "  \"" + tm.globalTaskName + task.getTaskNumber()
        + "\" [color=" + color1 + " style=filled fillcolor=" + color2 + "];\n";

      if(!task.getNextTasks().isEmpty())
      {
        for (Task nextTask : task.getNextTasks())
        {
          body = body + "  \"" + tm.globalTaskName + task.getTaskNumber() + "\""
            + " -> " + "\"" + tm.globalTaskName + nextTask.getTaskNumber() + "\""
            + " [color=black style=filled fillcolor=" + color1 + "];\n";
        }
      }
    }

    String graphFileContent = begin + body + end;
    // System.out.println(graphFileContent);
    try {
      PrintWriter writer = new PrintWriter("graphic/in/graph.gv", "UTF-8");
      writer.println(graphFileContent);
      writer.close();
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
      result = result + time*tm.globalTaskDuration + "-" + (time+1)*tm.globalTaskDuration + "|";
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
