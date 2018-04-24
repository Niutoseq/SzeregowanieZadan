import java.util.ArrayList;

public class Task
{
  private int taskNumber;         // numer zadania
  private int minTime;            // czas najwcześniej możliwy
  private int maxTime;            // czas najpóźniej dopuszczalny
  private ArrayList<Task> previousTasks;   // zadania, od których aktualne jest zależne
  private ArrayList<Task> nextTasks;       // zadania zależne od aktualnego

  public Task()
  {
    super();
  }

  public Task(int taskNumber, int minTime, int maxTime,
              ArrayList<Task> previousTasks, ArrayList<Task> nextTasks)
  {
    super();
    this.taskNumber = taskNumber;
    this.minTime = minTime;
    this.maxTime = maxTime;
    this.previousTasks = previousTasks;
    this.nextTasks = nextTasks;
  }

  public int getTaskNumber()
  {
		return taskNumber;
	}
	public void setTaskNumber(int taskNumber)
  {
		this.taskNumber = taskNumber;
	}

  public int getMinTime()
  {
    return minTime;
  }
  public void setMinTime(int minTime)
  {
    this.minTime = minTime;
  }

  public int getMaxTime()
  {
    return maxTime;
  }
  public void setMaxTime(int maxTime)
  {
    this.maxTime = maxTime;
  }

  public ArrayList<Task> getPreviousTasks()
  {
    return previousTasks;
  }
  public void setPreviousTasks(ArrayList<Task> previousTasks)
  {
    this.previousTasks = previousTasks;
  }

  public ArrayList<Task> getNextTasks()
  {
    return nextTasks;
  }
  public void setNextTasks(ArrayList<Task> nextTasks)
  {
    this.nextTasks = nextTasks;
  }
}
