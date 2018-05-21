import java.util.ArrayList;

public class Task
{
  private int taskNumber;               // numer zadania
  private ArrayList<Integer> durations; // czasy trwania na maszynach
  private int modDuration1;             // zmodyfikowany czas wykonania 1
  private int modDuration2;             // zmodyfikowany czas wykonania 2
  private Collection collection;        // zbiór, do którego należy zadanie

  public Task()
  {
    super();
  }

  public Task(int taskNumber, ArrayList<Integer> durations,
              int modDuration1, int modDuration2, Collection collection)
  {
    super();
    this.taskNumber = taskNumber;
    this.durations = durations;
    this.modDuration1 = modDuration1;
    this.modDuration2 = modDuration2;
    this.collection = collection;
  }

  public int getTaskNumber()
  {
    return taskNumber;
  }
  public void setTaskNumber(int taskNumber)
  {
    this.taskNumber = taskNumber;
  }

  public ArrayList<Integer> getDurations()
  {
    return durations;
  }
  public void setDurations(ArrayList<Integer> durations)
  {
    this.durations = durations;
  }

  public int getModDuration1()
  {
    return modDuration1;
  }
  public void setModDuration1(int modDuration1)
  {
    this.modDuration1 = modDuration1;
  }

  public int getModDuration2()
  {
    return modDuration2;
  }
  public void setModDuration2(int modDuration2)
  {
    this.modDuration2 = modDuration2;
  }

  public Collection getCollection()
  {
    return collection;
  }
  public void setCollection(Collection collection)
  {
    this.collection = collection;
  }
}
