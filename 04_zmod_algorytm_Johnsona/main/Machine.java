import java.util.ArrayList;

public class Machine
{
  private int machineNumber;            // numer maszyny
  private ArrayList<Task> schedule;     // uszeregowanie na danej maszynie

  public Machine()
  {
    super();
  }

  public Machine(int machineNumber, ArrayList<Task> schedule)
  {
    super();
    this.machineNumber = machineNumber;
    this.schedule = schedule;
  }

  public int getMachineNumber()
  {
    return machineNumber;
  }
  public void setMachineNumber(int machineNumber)
  {
    this.machineNumber = machineNumber;
  }

  public ArrayList<Task> getSchedule()
  {
    return schedule;
  }
  public void setSchedule(ArrayList<Task> schedule)
  {
    this.schedule = schedule;
  }
}
