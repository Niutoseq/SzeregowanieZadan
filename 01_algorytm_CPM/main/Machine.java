import java.util.ArrayList;

public class Machine
{
  private int machineNumber;               // numer zadania
  private Boolean isFree = true;              // czy maszyna jest aktualnie wolna?
  private ArrayList<Task> listOfTasks;     // szereg zadań na maszynie

  public Machine()
  {
    super();
  }

  public Machine(int machineNumber, Boolean isFree, ArrayList<Task> listOftasks)
  {
    super();
    this.machineNumber = machineNumber;
    this.isFree = isFree;
    this.listOfTasks = listOfTasks;
  }

  public int getMachineNumber()
  {
		return machineNumber;
	}
	public void setMachineNumber(int machineNumber)
  {
		this.machineNumber = machineNumber;
	}

  public Boolean getIsFree()
  {
    return isFree;
  }
  public void setIsFree(Boolean isFree)
  {
    this.isFree = isFree;
  }

  public ArrayList<Task> getListOfTasks()
  {
    return listOfTasks;
  }
  public void setListOfTasks(ArrayList<Task> listOfTasks)
  {
    this.listOfTasks = listOfTasks;
  }
}
