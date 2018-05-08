import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TaskManager
{
  public static ArrayList<Task> tasks = new ArrayList<Task>();

  public static void addTask(Task task)
  {
    tasks.add(task);
  }

  public static void addConnection(Task firstTask, Task secondTask)
  {
    if (firstTask.getTaskNumber() == secondTask.getTaskNumber())
    {
      System.out.println("[Task" + firstTask.getTaskNumber() + "]: Nie można utworzyć powiązania zadania z samym sobą!");
      App.displayFrame();
      System.exit(0);
    }

    if (firstTask.getTaskNumber() < secondTask.getTaskNumber())
    {
      System.out.println("[Task" + firstTask.getTaskNumber() + "]: Zadanie o numerze wyższym nie może poprzedzać numeru niższego!");
      App.displayFrame();
      System.exit(0);
    }

    for (Task task : tasks)
    {
      if (task.getTaskNumber() == firstTask.getTaskNumber())
      {
        firstTask.getConnectedTasks().add(secondTask);
        secondTask.getNextTasks().add(firstTask);
      }
    }
  }

  public static ArrayList<Task> getAllTasks()
  {
    return tasks;
  }

  public static void displayTasksScheme()
  {
    System.out.println("SCHEME:");
    System.out.println("[Task no. <num>] <duration>, <start>, <finish>, {<prevTasks>}, {<nextTasks>}");
  }

  public static void displayAllTasks()
  {
    if (!tasks.isEmpty())
    {
      System.out.println("TASKS:");
      for (Task task : tasks)
      {
        System.out.print("[Task no. " + task.getTaskNumber() + "] "
          + task.getDuration() + ", "
          + task.getStartTime() + ", "
          + task.getFinishTime() + ", ");

        // zadania, od których obecne jest zależne
        if (!task.getConnectedTasks().isEmpty())
        {
          System.out.print("{");
          int i = 0;
          for (Task connTask : task.getConnectedTasks())
          {
            if (i++ == task.getConnectedTasks().size() - 1)
            {
              System.out.print(connTask.getTaskNumber());
              break;
            }
            System.out.print(connTask.getTaskNumber() + ", ");
          }
          System.out.print("}");
        }
        else
        {
          System.out.print("empty");
        }
        System.out.print(", ");

        // zadania zależne od obecnego zadania
        if (!task.getNextTasks().isEmpty())
        {
          System.out.print("{");
          int i = 0;
          for (Task nextTask : task.getNextTasks())
          {
            if (i++ == task.getNextTasks().size() - 1)
            {
              System.out.print(nextTask.getTaskNumber());
              break;
            }
            System.out.print(nextTask.getTaskNumber() + ", ");
          }
          System.out.print("}");
        }
        else
        {
          System.out.print("empty");
        }
        System.out.print("\n");
      }
    }
    else
    {
      System.out.println("No tasks to be displayed.");
      App.displayFrame();
      System.exit(0);
    }
  }

  public static void calculateTimes()
  {
    for (Task task : tasks)
    {
      for (Task connTask : task.getConnectedTasks())
      {
        if (task.getTaskNumber() == connTask.getTaskNumber())
        {
          connTask.setStartTime(task.getStartTime());
          connTask.setFinishTime(task.getFinishTime());
        }
      }

      if(task.getConnectedTasks().isEmpty())
      {
        task.setStartTime(0);
        task.setFinishTime(task.getDuration());
      }
      else
      {
        int[] finishTimes = new int[task.getConnectedTasks().size()];
        for (int i = 0; i < task.getConnectedTasks().size(); i++)
        {
          finishTimes[i] = task.getConnectedTasks().get(i).getFinishTime();
        }
        Arrays.sort(finishTimes);
        int maxFinishTime = finishTimes[finishTimes.length - 1];
        task.setStartTime(maxFinishTime);
        task.setFinishTime(task.getStartTime() + task.getDuration());
      }
    }
  }

  // wyznaczanie ścieżki krytycznej
  public static void findCriticalPath(ArrayList<Task> tasksToPath, ArrayList<Task> finalPath)
  {
    Task maxTask = new Task();
    for (Task task : tasksToPath)
    {
      if (task.getFinishTime() > maxTask.getFinishTime())
      {
        maxTask = task;
      }
    }
    finalPath.add(maxTask);
    if (!maxTask.getConnectedTasks().isEmpty())
    {
      findCriticalPath(maxTask.getConnectedTasks(), finalPath);
    }
  }

  // wyświetlanie ścieżki krytycznej
  public static void criticalPathDisplayer(ArrayList<Task> tasksToPath)
  {
    ArrayList<Task> cp = new ArrayList<Task>();
    findCriticalPath(tasksToPath, cp);
    System.out.println("[Critical Path Time]:   " + cp.get(0).getFinishTime());

    Collections.reverse(cp);
    System.out.print("[Critical Path String]: ");
    int x = 0;
    for (Task task : cp)
    {
      if (x == cp.size()-1)
      {
        System.out.print("Z" + task.getTaskNumber());
        break;
      }
      System.out.print("Z" + task.getTaskNumber() + " -> ");
      x++;
    }
    System.out.print("\n");
  }
}
