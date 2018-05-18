import java.util.ArrayList;
import java.util.Comparator;

public class Task
{
  private int taskNumber;               // numer zadania
  private int duration;                 // czas trwania
  private int level;                    // poziom zadania
  private ArrayList<Task> prevTasks;    // zadania, na które oczekuje obecne
  private ArrayList<Task> nextTasks;    // zadania oczekujące na obecne
  private Boolean isCompleted = false;  // true gdy zakończone, false gdy nie

  public Task()
  {
    super();
  }

  public Task(int taskNumber, int duration, int level, ArrayList<Task> prevTasks,
              ArrayList<Task> nextTasks, Boolean isCompleted)
  {
    super();
    this.taskNumber = taskNumber;
    this.duration = duration;
    this.level = level;
    this.prevTasks = prevTasks;
    this.nextTasks = nextTasks;
    this.isCompleted = isCompleted;
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

  public int getLevel()
  {
    return level;
  }
  public void setLevel(int level)
  {
    this.level = level;
  }
  public static Comparator<Task> compareLevels = new Comparator<Task>()
  {
    public int compare(Task task1, Task task2)
    {
      int lvl1 = task1.getLevel();
      int lvl2 = task2.getLevel();
      return lvl2 - lvl1;
    }
  };

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

  public Boolean getIsCompleted()
  {
    return this.isCompleted;
  }
  public void setIsCompleted(Boolean isCompleted)
  {
    this.isCompleted = isCompleted;
  }
}
