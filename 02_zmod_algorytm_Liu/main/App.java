import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class App
{
  static ArrayList<String> fileContent = new ArrayList<String>();
  static ArrayList<ArrayList<String>> tasksFromFile = new ArrayList<ArrayList<String>>();

  public static String globalTaskName = "Z";

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
      closeApp("Nie udało się otworzyć pliku " + filename + "!");
    }

    if (fileContent.isEmpty())
    {
      // przypadek, kiedy plik jest pusty
      closeApp("Plik " + filename + " jest pusty!");
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

      for (int i = 1; i < tasksFromFile.size(); i++)
      {
        int taskNumber = Integer.parseInt(tasksFromFile.get(i).get(0));
        int taskDuration = Integer.parseInt(tasksFromFile.get(i).get(1));
        int taskArrival = Integer.parseInt(tasksFromFile.get(i).get(2));
        int taskFinish = Integer.parseInt(tasksFromFile.get(i).get(3));

        if(taskNumber > tasksFromFile.size())
        {
          closeApp("Error: Numeruj zadania po kolei, nie pomijaj numerów.");
        }
        Task task = new Task(taskNumber, taskDuration, taskArrival, taskFinish, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false, false);
        TaskManager.addTask(task);
      }

      for (int i = 1; i < tasksFromFile.size(); i++)
      {
        String prevCheck = tasksFromFile.get(i).get(4);
        int taskNumber = Integer.parseInt(tasksFromFile.get(i).get(0));

        if (prevCheck.equals("tak"))
        {
          if (tasksFromFile.get(i).size() <= 5)
          {
            closeApp("[Task" + taskNumber + "]: Wybrano zadania poprzedzające, jednak ich nie sprecyzowano!");
          }
          for (int j = 5; j < tasksFromFile.get(i).size(); j++)
          {
            TaskManager.addConnection(TaskManager.tasks.get(Integer.parseInt(tasksFromFile.get(i).get(j))-1).getTaskNumber(), TaskManager.tasks.get(taskNumber-1).getTaskNumber());
          }
        }
        else if (prevCheck.equals("nie"))
        {
          // brak zadań powiązanych
        }
        else
        {
          closeApp("[PrevCheck]: Task" + taskNumber + " (\"" + prevCheck + "\"): Decyzja nierozpoznana!");
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
      result = result + "  \"" + globalTaskName + task.getTaskNumber()
        + "\" [color=darkgreen style=filled fillcolor=palegreen];\n";

      if(!task.getNextTasks().isEmpty())
      {
        for (Task nextTask : task.getNextTasks())
        {
          result = result + "  \"" + globalTaskName + task.getTaskNumber() + "\""
            + " -> " + "\"" + globalTaskName + nextTask.getTaskNumber() + "\""
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
    result = result + "{Time|Task}|";
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
      else result = result + globalTaskName + stask.getTaskNumber();
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

    String filename = "dane.txt";
    prepareData(filename);

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
