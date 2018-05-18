import java.util.ArrayList;

public class MachineManager
{
  public static ArrayList<Machine> machines = new ArrayList<Machine>();
  public static TaskManager tm = new TaskManager();
  public static String globalMachineName = "M";

  public static void addMachine(Machine machine)
  {
    machines.add(machine);
  }

  public static void displayScheduleList(ArrayList<Task> tasksToSchedule)
  {
    if (!tasksToSchedule.isEmpty())
    {
      System.out.print("   ");
      for (Task task : tasksToSchedule)
      {
        if (task.getTaskNumber() == 0)
        {
          System.out.print("--- ");
        }
        else
        {
          String taskToDisplay = tm.globalTaskName + task.getTaskNumber();
          while (taskToDisplay.length() < tm.globalTaskName.length() + 2)
          {
            taskToDisplay = " " + taskToDisplay;
          }
          System.out.print(taskToDisplay + " ");
        }
      }
    }
    else
    {
      System.out.print("empty");
    }
  }

  public static void displayMachinesScheme()
  {
    System.out.println("SCHEME:");
    System.out.println("[Machine " + globalMachineName + "<num>] <schedule>");
    // for (int i = 0; i < globalMachineName.length(); i++)
    // {
    //   System.out.print(" ");
    // }
    System.out.print("\n");
  }

  public static void displayAllMachines()
  {
    displayMachinesScheme();
    System.out.println("MACHINES:");

    int longestSchedule = 0;
    for (Machine machine : machines)
    {
      if (longestSchedule < machine.getSchedule().size())
      {
        longestSchedule = machine.getSchedule().size();
      }
    }

    System.out.println("[Time]");
    System.out.print("   ");
    for (int i = 1; i <= longestSchedule; i++)
    {
      String numberToDisplay = new String();
      numberToDisplay = numberToDisplay + i * (tm.globalTaskDuration);
      while (numberToDisplay.length() < tm.globalTaskName.length() + 2)
      {
        numberToDisplay = " " + numberToDisplay;
      }
      System.out.print(numberToDisplay + " ");
    }
    System.out.print("\n");

    for (Machine machine : machines)
    {
      System.out.println("[Machine " + globalMachineName + machine.getMachineNumber() + "]");
      displayScheduleList(machine.getSchedule());
      System.out.print("\n");
    }
  }

  public static void prepareSchedules()
  {
    int time = 0;

    while (!tm.isAllTasksCompleted(tm.tasks))
    {
      int maxLevel = tm.tasks.get(0).getLevel();
      ArrayList<Task> tasksWithNoPrevs = new ArrayList<Task>();
      ArrayList<Task> tasksToProceed = new ArrayList<Task>();

      for (Task task : tm.tasks)
      {
        if (task.getPrevTasks().isEmpty() && !task.getIsCompleted())
        {
          tasksWithNoPrevs.add(task);
        }
      }

      if (tasksWithNoPrevs.size() <= machines.size())
      {
        // System.out.println(time + ": Less or equals...");
        tasksToProceed = tasksWithNoPrevs;
        while (tasksToProceed.size() < machines.size())
        {
          tasksToProceed.add(new Task(0, tm.globalTaskDuration, 0, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false));
        }
      }
      else
      {
        // System.out.println(time + ": More and more...");
        for (int i = 0; i < machines.size(); i++)
        {
          tasksToProceed.add(i, tasksWithNoPrevs.get(i));
        }
      }

      for (int i = 0; i < tasksToProceed.size(); i++)
      {
        machines.get(i).getSchedule().add(tasksToProceed.get(i));
        Task taskToProceed = tasksToProceed.get(i);
        tm.completeTaskByNumber(taskToProceed.getTaskNumber());
      }
      time++;
    }
  }
}
