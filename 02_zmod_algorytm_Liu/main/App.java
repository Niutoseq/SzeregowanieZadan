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
    TaskManager.makeSchedule();
    System.out.print("\n");
    makeGraph(TaskManager.tasks);

    // for (Task task : TaskManager.tasks)
    // {
    //   System.out.println(task.getTaskNumber() + ": " + task.getLateness());
    // }

    TaskManager.calculateMaxLateness();
    System.out.print("\n");
  }
}
