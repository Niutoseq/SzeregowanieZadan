import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class App
{
  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
  }

  public static void displayHeader()
  {
    System.out.println("    ╦  ╦╦ ╦  ╔═╗╦  ╔═╗╔═╗╦═╗╦╔╦╗╦ ╦╔╦╗    ");
    System.out.println("    ║  ║║ ║  ╠═╣║  ║ ╦║ ║╠╦╝║ ║ ╠═╣║║║    ");
    System.out.println("    ╩═╝╩╚═╝  ╩ ╩╩═╝╚═╝╚═╝╩╚═╩ ╩ ╩ ╩╩ ╩    ");
    System.out.println("┌┬┐┌─┐┌┬┐┬┌─┐┬┌─┐┌┬┐  ┬  ┬┌─┐┬─┐┌─┐┬┌─┐┌┐┌");
    System.out.println("││││ │ │││├┤ │├┤  ││  └┐┌┘├┤ ├┬┘└─┐││ ││││");
    System.out.println("┴ ┴└─┘─┴┘┴└  ┴└─┘─┴┘   └┘ └─┘┴└─└─┘┴└─┘┘└┘");
    System.out.print("\n");
  }

  public static void makeGraph(ArrayList<Task> tasksToGraph)
  {
    prepareGraph(tasksToGraph);
    try
    {
      Runtime.getRuntime().exec("dot -Tps main/graph.gv -o graph.ps");
    }
    catch (Exception e)
    {
      System.out.println("Tworzenie grafu nie powiodło się!");
    }

    try
    {
      ProcessBuilder pb = new ProcessBuilder("xdg-open", "graph.ps");
      pb.start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static String graphCreator(ArrayList<Task> tasksToGraph, String result)
  {
    for (Task task : tasksToGraph)
    {
      result = result + "  \"Z" + task.getTaskNumber()
        + "\" [color=darkgreen style=filled fillcolor=palegreen];\n";

      if(!task.getNextTasks().isEmpty())
      {
        for (Task nextTask : task.getNextTasks())
        {
          result = result + "  \"Z" + task.getTaskNumber() + "\""
            + " -> " + "\"Z" + nextTask.getTaskNumber() + "\""
            + " [color=black style=filled fillcolor=green3];\n";
        }
      }
    }
    return result;
  }

  public static void prepareGraph(ArrayList<Task> tasksToGraph)
  {
    String begin = "strict digraph G {\n";
    String body = new String();
    String end = "}";

    body = graphCreator(tasksToGraph, "");

    String graphFileContent = begin + body + end;
    // System.out.println(graphFileContent);
    try {
      PrintWriter writer = new PrintWriter("main/graph.gv", "UTF-8");
      writer.println(graphFileContent);
      writer.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void makeSchedule(ArrayList<Task> tasksToSchedule)
  {
    prepareScheduleAlternative();
    try
    {
      Runtime.getRuntime().exec("dot -Tps main/schedule.gv -o schedule.ps");
    }
    catch (Exception e)
    {
      System.out.println("Tworzenie harmonogramu nie powiodło się!");
    }

    try
    {
      ProcessBuilder pb = new ProcessBuilder("xdg-open", "schedule.ps");
      pb.start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void prepareScheduleAlternative()
  {
    String mySchedule = new String();
    String begin = "digraph struct {\n";
    begin = begin + "node [shape=record];\n";
    String body = new String();
    String result = new String();
    result = result + "struct1 [shape=record, color=darkgreen,";
    result = result + " style=filled, fillcolor=palegreen, label=\"";

    int time = 0;
    for (ScheduleTask stask : TaskManager.forSchedule)
    {
      result = result + "{";
      if (stask.getDuration() == 1)
      {
        result = result + time + "-" + (time+1) + "|";
      }
      else if (stask.getDuration() > 1)
      {
        result = result + "{";
        for (int i = 0; i < stask.getDuration() ; i++)
        {
          result = result + time + "-" + (time+1) + "|";
          if (i == stask.getDuration() - 1)
          {
            time--;
          }
          time++;
        }
        result = result.substring(0, result.length() - 1);
        result = result + "}|";
      }
      if (stask.getTaskNumber() == 0)
      {
        result = result + "-";
      }
      else result = result + "Z" + stask.getTaskNumber();
      result = result + "}|";
      time++;
    }
    result = result.substring(0, result.length() - 1);
    result = result + "\"];\n";
    body = result;
    String end = "}";
    mySchedule = begin + body + end;
    // System.out.println(mySchedule);

    try {
      PrintWriter writer = new PrintWriter("main/schedule.gv", "UTF-8");
      writer.println(mySchedule);
      writer.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void prepareScheduleArray()
  {
    for (int i = 0; i < TaskManager.forSchedule.size() - 1; i++)
    {
      for (int j = 0; j < TaskManager.forSchedule.size() - 1; j++)
      {
        if (TaskManager.forSchedule.get(j).getTaskNumber() == TaskManager.forSchedule.get(j+1).getTaskNumber())
        {
          TaskManager.forSchedule.get(j).setDuration(TaskManager.forSchedule.get(j).getDuration() + TaskManager.forSchedule.get(j+1).getDuration());
          TaskManager.forSchedule.get(j+1).setDuration(0);
        }
      }
    }

    ArrayList<ScheduleTask> deleteCandidates = new ArrayList<ScheduleTask>();
    for (ScheduleTask stask : TaskManager.forSchedule)
    {
      if (stask.getDuration() == 0) deleteCandidates.add(stask);
    }
    for (ScheduleTask delCandidate : deleteCandidates)
    {
      TaskManager.forSchedule.remove(delCandidate);
    }
  }

  public static void closeApp(String info)
  {
    System.out.println(info);
    System.exit(0);
  }

  public static void main(String argv[])
  {
    clearScreen();
    displayHeader();

    // schemat: numer, trwanie, przybycie, stop, modStop, opóźnienie, prev, next, isActive?, isCompleted?
    TaskManager.addTask(new Task(1, 3, 0, 4, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false));
    TaskManager.addTask(new Task(2, 2, 4, 6, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false));
    TaskManager.addTask(new Task(3, 2, 2, 8, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false));
    TaskManager.addTask(new Task(4, 1, 5, 15, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false));
    TaskManager.addTask(new Task(5, 4, 6, 10, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false));
    TaskManager.addTask(new Task(6, 1, 15, 20, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false));
    TaskManager.addTask(new Task(7, 2, 13, 25, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false));

    TaskManager.addConnection(1, 3);
    TaskManager.addConnection(2, 4);
    TaskManager.addConnection(3, 5);
    TaskManager.addConnection(4, 5);
    TaskManager.addConnection(4, 6);
    TaskManager.addConnection(5, 7);
    TaskManager.addConnection(6, 7);

    TaskManager.assignModifiedFinishTime();
    TaskManager.displayAllTasks();
    System.out.print("\n");

    makeGraph(TaskManager.tasks);
    TaskManager.makeSchedule();
    System.out.print("\n");

    TaskManager.calculateMaxLateness();

    prepareScheduleArray();
    prepareScheduleAlternative();
    makeSchedule(TaskManager.tasks);

    System.out.print("\n");
  }
}
