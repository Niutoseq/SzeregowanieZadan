import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskManager
{
  public static ArrayList<Task> tasks = new ArrayList<Task>();
  public static String globalTaskName = "Z";

  public static MachineManager mm = new MachineManager();

  public static void addTask(Task task)
  {
    tasks.add(task);
  }

  public static void displayTasksScheme()
  {
    App.info("SCHEME:");
    App.info("[Task " + globalTaskName + "<num>]: [<durations>], <t1>, <t2>, <collectionName>[<min{t1,t2}>]");
  }

  public static void displayAllTasks()
  {
    displayTasksScheme();
    App.newLines(1);
    App.info("TASKS:");
    for (Task task : tasks)
    {
      App.info("[Task " + globalTaskName + task.getTaskNumber() + "]: "
              + task.getDurations() + ", " + task.getModDuration1() + ", "
              + task.getModDuration2() + ", "
              + task.getCollection().getCollectionName()
              + "[" + task.getCollection().getCollectionValue() + "]");
    }
  }

  public static void calculateModDurations()
  {
    for (Task task : tasks)
    {
      int modDuration1 = task.getDurations().get(0) + task.getDurations().get(1);
      int modDuration2 = task.getDurations().get(1) + task.getDurations().get(2);
      task.setModDuration1(modDuration1);
      task.setModDuration2(modDuration2);
    }
  }

  public static void assingCollections()
  {
    for (Task task : tasks)
    {
      if (task.getModDuration1() < task.getModDuration2())
      {
        task.setCollection(new Collection("N1", task.getModDuration1()));
      }
      else if (task.getModDuration1() >= task.getModDuration2())
      {
        task.setCollection(new Collection("N2", task.getModDuration2()));
      }
    }
  }

  public static void prepareSchedules()
  {
    ArrayList<Task> scheduleN1 = new ArrayList<Task>();
    ArrayList<Task> scheduleN2 = new ArrayList<Task>();

    for (Task task : tasks)
    {
      if (task.getCollection().getCollectionName().equals("N1"))
      {
        scheduleN1.add(task);
      }
      else if (task.getCollection().getCollectionName().equals("N2"))
      {
        scheduleN2.add(task);
      }
    }

    Collections.sort(scheduleN1, new Comparator<Task>()
    {
      @Override
      public int compare(Task task1, Task task2)
      {
        return task1.getCollection().getCollectionValue() - task2.getCollection().getCollectionValue();
      }
    });

    Collections.sort(scheduleN2, new Comparator<Task>()
    {
      @Override
      public int compare(Task task1, Task task2)
      {
        return task2.getCollection().getCollectionValue() - task1.getCollection().getCollectionValue();
      }
    });

    int time = 0;
    ArrayList<Task> schedule = new ArrayList<Task>();
    schedule.addAll(scheduleN1);
    schedule.addAll(scheduleN2);

    for (Task task : schedule)
    {
      for (int i = 0; i < task.getDurations().get(0); i++)
      {
          mm.machines.get(0).getSchedule().add(task);
      }

      for (int i = 0; i < task.getDurations().get(1); i++)
      {
        if (mm.machines.get(0).getSchedule().size() > time)
        {
          if (mm.machines.get(0).getSchedule().get(time).getTaskNumber() == task.getTaskNumber())
          {
            mm.machines.get(1).getSchedule().add(new Task(0, new ArrayList<Integer>(), 0, 0, new Collection()));
            i--;
          }
        }
        else
        {
          mm.machines.get(1).getSchedule().add(task);
        }
        time++;
      }
    }

    time = 0;
    for (Task task : schedule)
    {
      for (int i = 0; i < task.getDurations().get(2); i++)
      {
        if (mm.machines.get(0).getSchedule().size() > time
            && mm.machines.get(1).getSchedule().size() > time)
        {
          if (mm.machines.get(0).getSchedule().get(time).getTaskNumber() == task.getTaskNumber()
              || mm.machines.get(1).getSchedule().get(time).getTaskNumber() == task.getTaskNumber())
          {
            mm.machines.get(2).getSchedule().add(new Task(0, new ArrayList<Integer>(), 0, 0, new Collection()));
            i--;
          }
          else
          {
            mm.machines.get(2).getSchedule().add(task);
          }
          time++;
        }
        else
        {
          mm.machines.get(2).getSchedule().add(task);
          time++;
        }
      }
    }

    int longestSchedule = 0;
    for (Machine machine : mm.machines)
    {
      if (longestSchedule < machine.getSchedule().size())
      {
        longestSchedule = machine.getSchedule().size();
      }
    }

    for (Machine machine : mm.machines)
    {
      while (machine.getSchedule().size() < longestSchedule)
      {
        machine.getSchedule().add(new Task(0, new ArrayList<Integer>(), 0, 0, new Collection()));
      }
    }
  }
}
