import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class App
{
  static ArrayList<String> fileContent = new ArrayList<String>();
  static ArrayList<ArrayList<String>> tasksFromFile = new ArrayList<ArrayList<String>>();

  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
  }

  public static void displayHeader()
  {
    System.out.println("╔═╗╦═╗╦╔╦╗╦╔═╗╔═╗╦    ╔═╗╔═╗╔╦╗╦ ╦  ╔╦╗╔═╗╔╦╗╦ ╦╔═╗╔╦╗");
    System.out.println("║  ╠╦╝║ ║ ║║  ╠═╣║    ╠═╝╠═╣ ║ ╠═╣  ║║║║╣  ║ ╠═╣║ ║ ║║");
    System.out.println("╚═╝╩╚═╩ ╩ ╩╚═╝╩ ╩╩═╝  ╩  ╩ ╩ ╩ ╩ ╩  ╩ ╩╚═╝ ╩ ╩ ╩╚═╝═╩╝");
    System.out.println("     ┌─┐┌─┐┌┬┐┬┬  ┬┬┌┬┐┬ ┬  ┌─┐┌┐┌  ┌┐┌┌─┐┌┬┐┌─┐      ");
    System.out.println("───  ├─┤│   │ │└┐┌┘│ │ └┬┘  │ ││││  ││││ │ ││├┤   ─── ");
    System.out.println("     ┴ ┴└─┘ ┴ ┴ └┘ ┴ ┴  ┴   └─┘┘└┘  ┘└┘└─┘─┴┘└─┘      ");
  }

  public static void displayFrame()
  {
    System.out.println("------------------------------------------------------");
  }

  public static void info(String infoText)
  {
    System.out.println(infoText);
  }

  public static void prepareData(String fileToPrepare)
  {
    Scanner in;
    String filename = fileToPrepare;
    String outString = new String();

    try {
      in = new Scanner(new FileReader(filename));
      while(in.hasNextLine()) {
          fileContent.add(in.nextLine());
      }
      in.close();
    }
    catch (FileNotFoundException e)
    {
      info("Nie udało się otworzyć pliku " + filename + "!");
      displayFrame();
      // e.printStackTrace();
      System.exit(0);
    }

    if (fileContent.isEmpty())
    {
      // przypadek, kiedy plik jest pusty
      info("Plik " + filename + " jest pusty!");
      displayFrame();
      System.exit(0);
    }
    else
    {
      // przypadek, kiedy plik nie jest pusty
      for (String line : fileContent)
      {
        String[] splited = line.split("\\s");
        ArrayList<String> splitResult = new ArrayList<String>();
        for (String part : splited)
        {
          splitResult.add(part);
        }
        tasksFromFile.add(splitResult);
      }

      for (int i = 0; i < tasksFromFile.size(); i = i + 3)
      {
        int taskNumber = Integer.parseInt(tasksFromFile.get(i).get(1));
        int taskDuration = Integer.parseInt(tasksFromFile.get(i+1).get(1));

        if(taskNumber > tasksFromFile.size())
        {
          info("Error: Numeruj zadania po kolei, nie pomijaj numerów.");
          displayFrame();
          System.exit(0);
        }
        Task task = new Task(taskNumber, taskDuration, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
        TaskManager.addTask(task);
      }

      for (int i = 0; i < tasksFromFile.size(); i = i + 3)
      {
        String prevCheck = tasksFromFile.get(i+2).get(1);
        int taskNumber = Integer.parseInt(tasksFromFile.get(i).get(1));

        if (prevCheck.equals("tak"))
        {
          if (tasksFromFile.get(i+2).size() <= 2)
          {
            info("[Task" + taskNumber + "]: Wybrano zadania poprzedzające, jednak ich nie sprecyzowano!");
            displayFrame();
            System.exit(0);
          }
          for (int j = 2; j < tasksFromFile.get(i+2).size(); j++)
          {
            TaskManager.addConnection(TaskManager.tasks.get(taskNumber-1), TaskManager.tasks.get(Integer.parseInt(tasksFromFile.get(i+2).get(j))-1));
          }
        }
        else if (prevCheck.equals("nie"))
        {
          // brak zadań powiązanych
        }
        else
        {
          info("[PrevCheck]: Task" + taskNumber + ": Decyzja nierozpoznana!");
          displayFrame();
          System.exit(0);
        }
      }
    }
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
      info("Tworzenie grafu nie powiodło się!");
    }
  }

  public static String graphCreator(ArrayList<Task> tasksToGraph, String result)
  {
    for (Task task : tasksToGraph)
    {
      result = result + "  \"Z" + task.getTaskNumber()
        + " (" + task.getStartTime() + "," + task.getFinishTime() + ") "
        + task.getDuration() + "\"[color=darkgreen style=filled fillcolor=palegreen];\n";

      if(!task.getConnectedTasks().isEmpty())
      {
        for (Task prevTask : task.getConnectedTasks())
        {
          result = result + "  \"Z" + prevTask.getTaskNumber()
            + " (" + prevTask.getStartTime() + "," + prevTask.getFinishTime()
            + ") " + prevTask.getDuration() + "\" -> \"Z"
            + task.getTaskNumber() + " (" + task.getStartTime()
            + "," + task.getFinishTime() + ") " + task.getDuration()
            + "\";\n";
        }
      }
      else
      {
        result = result + "  \"Z" + task.getTaskNumber()
          + " (" + task.getStartTime() + "," + task.getFinishTime() + ") "
          + task.getDuration() + "\";\n";
      }
    }

    ArrayList<Task> cp = TaskManager.returnCriticalPath(TaskManager.tasks);
    int counter = 0;
    for (Task cpTask : cp)
    {
      if (counter == cp.size()-1)
      {
        result = result + "  \"Z" + cpTask.getTaskNumber()
          + " (" + cpTask.getStartTime() + "," + cpTask.getFinishTime()
          + ") " + cpTask.getDuration() + "\" [color=darkviolet];\n";
          break;
      }
      result = result + "  \"Z" + cpTask.getTaskNumber()
        + " (" + cpTask.getStartTime() + "," + cpTask.getFinishTime()
        + ") " + cpTask.getDuration() + "\" -> ";
      counter++;
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
      ProcessBuilder pb = new ProcessBuilder("xdg-open", "graph.ps");
      pb.start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void main(String[] args)
  {
    // String filename = "dane1.txt";
    String filename = "dane2.txt";

    clearScreen();
    displayHeader();
    displayFrame();
    prepareData(filename);

    TaskManager.calculateTimes();
    TaskManager.displayTasksScheme();
    displayFrame();
    TaskManager.displayAllTasks();
    displayFrame();
    TaskManager.criticalPathDisplayer(TaskManager.tasks);
    displayFrame();
    makeGraph(TaskManager.tasks);
    TaskManager.scheduleDisplayer(TaskManager.tasks);
    displayFrame();
  }
}
