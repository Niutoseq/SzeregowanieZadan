import java.util.ArrayList;

public class Task
{
  private int taskNumber;               // numer zadania
  private int duration;                 // czas trwania
  private int arrivalTime;              // czas przybycia
  private int finishTime;               // czas zakończenia
  private int modFinishTime;            // zmodyfikowany czas zakończenia
  private ArrayList<Task> prevTasks;    // zadania, na które oczekuje obecne
  private ArrayList<Task> nextTasks;    // zadania oczekujące na obecne
  private Boolean isActive = false;     // true gdy obecne, false gdy jeszcze nie
  private Boolean isCompleted = false;  // true gdy zakończone, false gdy nie

  public Task()
  {
    super();
  }

  public Task(int taskNumber, int duration, int arrivalTime,
              int finishTime, int modFinishTime,
              ArrayList<Task> prevTasks, ArrayList<Task> nextTasks)
  {
    super();
    this.taskNumber = taskNumber;
    this.duration = duration;
    this.arrivalTime = arrivalTime;
    this.finishTime = finishTime;
    this.modFinishTime = modFinishTime;
    this.prevTasks = prevTasks;
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

  public int getArrivalTime()
  {
    return arrivalTime;
  }
  public void setArrivalTime(int arrivalTime)
  {
    this.arrivalTime = arrivalTime;
  }

  public int getFinishTime()
  {
    return finishTime;
  }
  public void setFinishTime(int finishTime)
  {
    this.finishTime = finishTime;
  }

  public int getModFinishTime()
  {
    return modFinishTime;
  }
  public void setModFinishTime(int modFinishTime)
  {
    this.modFinishTime = modFinishTime;
  }

  public ArrayList<Task> getPrevTasks()
  {
    return prevTasks;
  }
  public void setPrevTasks(ArrayList<Task> prevTasks)
  {
    this.prevTasks = prevTasks;
  }

  public ArrayList<Task> getNextTasks()
  {
    return nextTasks;
  }
  public void setNextTasks(ArrayList<Task> nextTasks)
  {
    this.nextTasks = nextTasks;
  }

  public Boolean getIsActive()
  {
    return this.isActive;
  }
  public void setIsActive(Boolean isActive)
  {
    this.isActive = isActive;
  }

  public Boolean getIsCompleted()
  {
    return this.isCompleted;
  }
  public void setIsCompleted(Boolean isCompleted)
  {
    this.isCompleted = isCompleted;
  }
}
