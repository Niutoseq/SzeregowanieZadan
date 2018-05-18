import java.util.ArrayList;
import java.util.Collections;

public class TaskManager
{
  public static ArrayList<Task> tasks = new ArrayList<Task>();
  public static String globalTaskName = "Z";
  public static int globalTaskDuration = 1;

  public static void addTask(Task task)
  {
    tasks.add(task);
  }

  public static void addConnection(int task1number, int task2number)
  {
    Task task1 = tasks.get(task1number-1);
    Task task2 = tasks.get(task2number-1);

    if (task1.getTaskNumber() == task2.getTaskNumber())
      App.closeApp("[Task" + task1.getTaskNumber() + "]: Nie można utworzyć powiązania zadania z samym sobą!");

    if (task1.getTaskNumber() > task2.getTaskNumber())
      App.closeApp("[Task" + task1.getTaskNumber() + "]: Zadanie o numerze wyższym nie może poprzedzać numeru niższego!");

    if (tasks.contains(task1) && tasks.contains(task2))
    {
      task1.getNextTasks().add(task2);
      task2.getPrevTasks().add(task1);
    }
    else
    {
      App.closeApp("Błąd! Któregoś z zadań nie ma na liście zadań.");
    }
  }

  public static void displayConnTaskList(ArrayList<Task> connTaskList)
  {
    if (!connTaskList.isEmpty())
    {
      System.out.print("{");
      int x = 0;
      for (Task task : connTaskList)
      {
        if (x == connTaskList.size()-1)
        {
          System.out.print(globalTaskName + task.getTaskNumber());
          break;
        }
        System.out.print(globalTaskName + task.getTaskNumber() + ", ");
        x++;
      }
      System.out.print("}");
    }
    else System.out.print("empty");
  }

  public static void displayTasksScheme()
  {
    System.out.println("SCHEME:");
    System.out.println("[Task " + globalTaskName + "<num>] <duration>, <start>. <finish>, <level>,");
    for (int i = 0; i < globalTaskName.length(); i++)
    {
      System.out.print(" ");
    }
    System.out.println("             {<prevTasks>}, {<nextTasks>}");
    System.out.print("\n");
  }

  public static void displayAllTasks()
  {
    displayTasksScheme();
    System.out.println("TASKS:");
    for (Task task : tasks)
    {
      System.out.print("[Task " + globalTaskName + task.getTaskNumber() + "]");
      System.out.print(" " + task.getDuration() + ",");
      System.out.print(" " + task.getStartTime() + ",");
      System.out.print(" " + task.getFinishTime() + ",");
      System.out.print(" " + task.getLevel() + ",");
      System.out.print(" ");
      displayConnTaskList(task.getPrevTasks());
      System.out.print(",");
      System.out.print(" ");
      displayConnTaskList(task.getNextTasks());
      System.out.print("\n");
    }
  }

  // public static void calculateTimes()
  // {
  //   for (Task task : tasks)
  //   {
  //     if (task.getPrevTasks().isEmpty())
  //     {
  //       task.setFinishTime(0);
  //       // System.out.println("EMPTY: " + task.getTaskNumber());
  //     }
  //     else
  //     {
  //       for (Task prevTask : task.getPrevTasks())
  //       {
  //         if (task.getFinishTime() <= prevTask.getFinishTime())
  //         {
  //           // System.out.println(task.getFinishTime() + "___" + prevTask.getFinishTime());
  //           task.setFinishTime(prevTask.getFinishTime() + task.getDuration());
  //         }
  //       }
  //     }
  //   }
  // }

  public static void setLevels()
  {
    Collections.reverse(tasks);
    for (Task task : tasks)
    {
      if (task.getNextTasks().isEmpty())
      {
        task.setLevel(1);
      }
      else
      {
        task.setLevel(task.getNextTasks().get(0).getLevel() + 1);
      }
    }
    Collections.reverse(tasks);
  }

  public static Boolean isAllTasksCompleted(ArrayList<Task> tasksToComplete)
  {
    return isAllTasksCompletedHelper(tasksToComplete, 0);
  }

  public static Boolean isAllTasksCompletedHelper(ArrayList<Task> tasksToComplete, int counter)
  {
    for (Task task : tasksToComplete)
    {
      if (!task.getIsCompleted()) counter++;
      isAllTasksCompletedHelper(task.getPrevTasks(), counter);
    }
    if (counter == 0) return true;
    else return false;
  }

  public static void deleteFromEverywhere(Task taskToDelete)
  {
    for (Task task : tasks)
    {
      if (task.getPrevTasks().contains(taskToDelete))
      {
        task.getPrevTasks().remove(taskToDelete);
      }
      if (task.getNextTasks().contains(taskToDelete))
      {
        task.getNextTasks().remove(taskToDelete);
      }
    }
  }

  public static void completeTaskByNumber(int taskNumber)
  {
    Task taskToDelete = new Task();
    for (Task task : tasks)
    {
      if (task.getTaskNumber() == taskNumber)
      {
        task.setIsCompleted(true);
        taskToDelete = task;
        break;
      }
    }
    tasks.remove(taskToDelete);
    deleteFromEverywhere(taskToDelete);
  }
}
