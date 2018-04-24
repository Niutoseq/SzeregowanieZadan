import java.util.ArrayList;

public class TaskManager
{
  public static ArrayList<Task> tasks = new ArrayList<Task>();

  public static void addTask(Task task)
  {
    tasks.add(task);
  }

  public static ArrayList<Task> getAllTasks()
  {
    return tasks;
  }

  public static void displayAllTasks()
  {
    if (!tasks.isEmpty())
    {
      for (Task task : tasks)
      {
        System.out.print("[Task] "
          + task.getTaskNumber() + ", "
          + task.getMinTime() + ", "
          + task.getMaxTime() + ", ");

        if (!task.getPreviousTasks().isEmpty())
        {
          System.out.print("{");
          for (Task prevTask : task.getPreviousTasks())
          {
            System.out.print(prevTask.getTaskNumber() + ",");
          }
          System.out.print("}");
        }
        else
        {
          System.out.print("empty");
        }
        System.out.print(", ");

        if (!task.getNextTasks().isEmpty())
        {
          System.out.print("{");
          for (Task nextTask : task.getNextTasks())
          {
            System.out.print(nextTask.getTaskNumber() + ",");
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
}
