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
        task.getConnectedTasks().add(secondTask);
      }
    }
  }

  public static ArrayList<Task> getAllTasks()
  {
    return tasks;
  }

  public static void displayAllTasks()
  {
    if (!tasks.isEmpty())
    {
      System.out.println("SCHEME:");
      System.out.println("[Task no. <number>] <duration>, <startTime>, <finishTime>, {<connectedTasks>}");
      System.out.print("\n");

      System.out.println("TASKS:");
      for (Task task : tasks)
      {
        System.out.print("[Task no. " + task.getTaskNumber() + "] "
          + task.getDuration() + ", "
          + task.getStartTime() + ", "
          + task.getFinishTime() + ", ");

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

  public static void findCriticalPath()
  {
    int criticalPath = 0;

    for (Task task : tasks)
    {
      if (criticalPath < task.getFinishTime())
      {
        criticalPath = task.getFinishTime();
      }
    }

    System.out.println("[Critical Path Time]: " + criticalPath);
  }

  // sprawdzić i wprowadzić adekwatne poprawki !!!!!
  public static String displayCriticalPath(ArrayList<Task> tasksToPath)
  {
    Collections.reverse(tasksToPath);
    for (Task task : tasksToPath)
    {
      return task.getTaskNumber() + "Z" + " >- " + displayCriticalPath(task.getConnectedTasks());
    }
    return "]MPC[";
  }
}
