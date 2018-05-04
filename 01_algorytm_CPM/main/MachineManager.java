import java.util.ArrayList;

public class MachineManager
{
  public static ArrayList<Machine> machines = new ArrayList<Machine>();

  public static void addMachine(Machine machine)
  {
    machines.add(machine);
  }

  public static ArrayList<Machine> getAllMachines()
  {
    return machines;
  }

  public static void displayAllMachines()
  {
    if (!machines.isEmpty())
    {
      System.out.println("MACHINES:");
      for (Machine machine : machines)
      {
        System.out.print("[Machine no. " + machine.getMachineNumber() + "] "
          + machine.getIsFree() + ", "
          + machine.getListOfTasks()
          + "\n");
      }
    }
    else
    {
      System.out.println("No machines to be displayed.");
    }
  }
}
