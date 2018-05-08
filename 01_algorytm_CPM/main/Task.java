import java.util.ArrayList;

public class Task
{
  private int taskNumber;                  // numer zadania
  private int duration;                    // czas trwania zadania
  private int startTime;                   // czas rozpoczęcia
  private int finishTime;                  // czas zakończenia
  private ArrayList<Task> connectedTasks;  // zadania, od których obecne jest zależne
  private ArrayList<Task> nextTasks;       // zadania zależne od obecnego zadania

  public Task()
  {
    super();
  }

  public Task(int taskNumber, int duration, int startTime,
              int finishTime, ArrayList<Task> connectedTasks,
              ArrayList<Task> nextTasks)
  {
    super();
    this.taskNumber = taskNumber;
    this.duration = duration;
    this.startTime = startTime;
    this.finishTime = finishTime;
    this.connectedTasks = connectedTasks;
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

  public int getDuration()
  {
    return duration;
  }
  public void setDuration(int duration)
  {
    this.duration = duration;
  }

  public int getStartTime()
  {
    return startTime;
  }
  public void setStartTime(int startTime)
  {
    this.startTime = startTime;
  }

  public int getFinishTime()
  {
    return finishTime;
  }
  public void setFinishTime(int finishTime)
  {
    this.finishTime = finishTime;
  }

  public ArrayList<Task> getConnectedTasks()
  {
    return connectedTasks;
  }
  public void setConnectedTasks(ArrayList<Task> connectedTasks)
  {
    this.connectedTasks = connectedTasks;
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
