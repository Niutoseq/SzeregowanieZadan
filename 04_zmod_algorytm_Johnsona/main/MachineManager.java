import java.util.ArrayList;

public class MachineManager
{
  public static ArrayList<Machine> machines = new ArrayList<Machine>();
  public static String globalMachineName = "M";

  public static TaskManager tm = new TaskManager();

  public static void addMachine(Machine machine)
  {
    machines.add(machine);
  }

  public static void displayMachinesScheme()
  {
    App.info("SCHEME");
    App.info("[Machine " + globalMachineName + "<num>] <schedule>");
  }

  public static void displayAllMachines()
  {
    displayMachinesScheme();
    App.newLines(1);
    App.info("MACHINES:");

    int longestSchedule = 0;
    for (Machine machine : machines)
    {
      if (longestSchedule < machine.getSchedule().size())
      {
        longestSchedule = machine.getSchedule().size();
      }
    }

    App.info("[Time]");
    System.out.print("   ");
    for (int i = 0; i < longestSchedule; i++)
    {
      String myTime = "" + (i+1);
      while (myTime.length() < 2) myTime = " " + myTime;
      System.out.print(myTime + " ");
    }
    System.out.print("\n");

    for (Machine machine : machines)
    {
      App.info("[Machine " + globalMachineName + machine.getMachineNumber() + "]");
      System.out.print("   ");
      for (Task task : machine.getSchedule())
      {
        if (task.getTaskNumber() == 0)
        {
          System.out.print("-- ");
        }
        else
        {
          System.out.print(tm.globalTaskName + task.getTaskNumber() + " ");
        }
      }
      System.out.print("\n");
    }
  }

  public static void compressSchedules()
  {
    for (Machine machine : machines)
    {
      for (Task task : machine.getSchedule())
      {
        if (task.getTaskNumber() == 0)
        {
          ArrayList<Integer> durations = new ArrayList<Integer>();
          durations.add(1);
          durations.add(1);
          durations.add(1);
          task.setDurations(durations);
        }
      }
    }

    for (Machine machine : machines)
    {
      for (int i = 0; i < machine.getSchedule().size() - 1; i++)
      {
        if (machine.getSchedule().get(i).getTaskNumber() == machine.getSchedule().get(i+1).getTaskNumber())
        {
          if (machine.getSchedule().get(i).getTaskNumber() == 0)
          {
            ArrayList<Integer> durations = new ArrayList<Integer>();
            durations.add(machine.getSchedule().get(i).getDurations().get(machine.getMachineNumber()-1)+1);
            durations.add(machine.getSchedule().get(i).getDurations().get(machine.getMachineNumber()-1)+1);
            durations.add(machine.getSchedule().get(i).getDurations().get(machine.getMachineNumber()-1)+1);
            machine.getSchedule().get(i).setDurations(durations);
          }
          machine.getSchedule().remove(i+1);
          i--;
        }
      }
    }
  }
}
