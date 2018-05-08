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
    for (Task task : tasks)
    {
      if (task == firstTask)
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
    // System.out.print("\n");
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
    }
  }

  public static void assignNumbersToTasks()
  {
    int i = 1;
    int numberOfTasks = tasks.size();

    // nadanie numerów "po kolei"
    for (Task task : tasks)
    {
      task.setTaskNumber(i);
      i++;
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

  public static void findCriticalPath(ArrayList<Task> tasksToPath)
  {
    int criticalPath = 0;
    String criticalPathString = new String();

    for (Task task : tasksToPath)
    {
      if (criticalPath < task.getFinishTime())
      {
        criticalPath = task.getFinishTime();
        // System.out.println("Z" + task.getTaskNumber());
        criticalPathString = criticalPathString + "Z" + task.getTaskNumber() + " ";
      }
    }
    System.out.println("[Critical Path String]: " + criticalPathString);
    System.out.println("[Critical Path Time]:   " + criticalPath);
  }

  public static ArrayList<Task> getAllRootElements(ArrayList<Task> tasksToRoot)
  {
    ArrayList<Task> rootElements = new ArrayList<Task>();
    for (Task task : tasksToRoot)
    {
      if (task.getConnectedTasks().isEmpty())
      {
        rootElements.add(task);
      }
    }
    return rootElements;
  }

  public static void criticalPath(ArrayList<Task> tasksToPath, String indent)
  {
    for (Task task : tasksToPath)
    {
      // System.out.println(indent + "Z" + task.getTaskNumber() + "[" + task.getConnectedTasks().isEmpty() + "]");
      System.out.println(indent + "Z" + task.getTaskNumber() + "[" + task.getFinishTime() + "]");
      // System.out.print(task.getNextTasks().isEmpty() ? "\n" : "");
      criticalPath(task.getNextTasks(), indent + "  ");
    }
  }

  // TA WERSJA CHYBA NAJLEPSZA, POTESTOWAĆ I SIĘ UPEWNIĆ !!!!!
  public static void criticalPath2(ArrayList<Task> tasksToPath, ArrayList<Task> finalPath)
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
      criticalPath2(maxTask.getConnectedTasks(), finalPath);
    }
  }

  public static void criticalPathDisplayer(ArrayList<Task> tasksToPath)
  {
    ArrayList<Task> rootElements = getAllRootElements(tasksToPath);
    ArrayList<Task> cp = new ArrayList<Task>();
    // criticalPath(rootElements, "");
    criticalPath2(tasksToPath, cp);
    System.out.println("[Critical Path Time]:   " + cp.get(0).getFinishTime());

    Collections.reverse(cp);
    System.out.print("[Critical Path String]: ");
    for (Task task : cp)
    {
      System.out.print("Z" + task.getTaskNumber() + " ");
    }
    System.out.print("\n");
  }

  // sprawdzić i wprowadzić adekwatne poprawki !!!!!
  public static String displayCriticalPathControler(ArrayList<Task> tasksToPath)
  {
    Collections.reverse(tasksToPath);
    for (Task task : tasksToPath)
    {
      return task.getTaskNumber() + "Z " + displayCriticalPathControler(task.getConnectedTasks());
    }
    return "";
  }

  public static void displayCriticalPath(ArrayList<Task> tasksToPath)
  {
    String result = TaskManager.displayCriticalPathControler(tasksToPath);
    String output = new StringBuilder(result).reverse().toString();
    // System.out.println("[Critical Path]:" + output);
  }

  public static void displayAllPaths(ArrayList<Task> tasksToPath, String indent)
  {
    // startTask.setExplored(true);
    for (Task task : tasksToPath)
    {
      System.out.println(indent + "Task" + task.getTaskNumber() + "[" + task.getDuration() + "]" + "[" + task.getStartTime() + "," + task.getFinishTime() + "]");
      if (task.getConnectedTasks() != null)
      {
        displayAllPaths(task.getConnectedTasks(), indent + "_______");
      }
    }
  }
}
