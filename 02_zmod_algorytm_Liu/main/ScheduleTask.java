public class ScheduleTask
{
  private int taskNumber;               // numer zadania
  private int duration = 0;             // czas trwania w harmonogramie

  public ScheduleTask()
  {
    super();
  }

  public ScheduleTask(int taskNumber, int duration)
  {
    super();
    this.taskNumber = taskNumber;
    this.duration = duration;
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
}
