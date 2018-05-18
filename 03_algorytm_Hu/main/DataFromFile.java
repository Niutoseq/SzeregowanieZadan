import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DataFromFile
{
  static ArrayList<String> fileContent = new ArrayList<String>();
  static ArrayList<ArrayList<String>> tasksFromFile = new ArrayList<ArrayList<String>>();

  public static TaskManager tm = new TaskManager();
  public static MachineManager mm = new MachineManager();

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
      App.closeApp("Nie udało się otworzyć pliku " + filename + "!");
    }

    if (fileContent.isEmpty())
    {
      // przypadek, kiedy plik jest pusty
      App.closeApp("Plik " + filename + " jest pusty!");
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

      int machinesNumber = Integer.parseInt(tasksFromFile.get(0).get(1));
      if (machinesNumber <= 0)
      {
        App.closeApp("Błąd! Liczba maszyn musi wynosić co najmniej 1!");
      }

      for (int i = 0; i < machinesNumber; i++)
      {
        mm.addMachine(new Machine((i+1), new ArrayList<Task>()));
      }

      for (int i = 2; i < tasksFromFile.size(); i++)
      {
        int taskNumber = Integer.parseInt(tasksFromFile.get(i).get(0));
        if(taskNumber > tasksFromFile.size())
        {
          App.closeApp("Error: Numeruj zadania po kolei, nie pomijaj numerów.");
        }
        Task task = new Task(taskNumber, tm.globalTaskDuration, 0, new ArrayList<Task>(), new ArrayList<Task>(), false);
        tm.addTask(task);
      }

      for (int i = 2; i < tasksFromFile.size(); i++)
      {
        int taskNumber = Integer.parseInt(tasksFromFile.get(i).get(0));
        String prevCheck = tasksFromFile.get(i).get(1);

        if (prevCheck.equals("tak"))
        {
          if (tasksFromFile.get(i).size() <= 2)
          {
            App.closeApp("[Task" + taskNumber + "]: Wybrano zadania poprzedzające, jednak ich nie sprecyzowano!");
          }
          for (int j = 2; j < tasksFromFile.get(i).size(); j++)
          {
            tm.addConnection(tm.tasks.get(Integer.parseInt(tasksFromFile.get(i).get(j))-1).getTaskNumber(), tm.tasks.get(taskNumber-1).getTaskNumber());
          }
        }
        else if (prevCheck.equals("nie"))
        {
          // brak zadań powiązanych
        }
        else
        {
          App.closeApp("[PrevCheck]: Task" + taskNumber + " (\"" + prevCheck + "\"): Decyzja nierozpoznana!");
        }
      }
    }
  }
}
