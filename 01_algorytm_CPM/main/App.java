import java.util.ArrayList;

public class App
{
  public static void main(String[] args)
  {
    ArrayList<Task> appTasks = new ArrayList<Task>();

    ArrayList<Task> prevTasks1 = new ArrayList<Task>();
    ArrayList<Task> prevTasks2 = new ArrayList<Task>();
    ArrayList<Task> prevTasks3 = new ArrayList<Task>();

    ArrayList<Task> nextTasks1 = new ArrayList<Task>();
    ArrayList<Task> nextTasks2 = new ArrayList<Task>();
    ArrayList<Task> nextTasks3 = new ArrayList<Task>();

    // zadania (tablice wstępnie jako null)
    Task task1 = new Task(1, 0, 0, null, null);
    Task task2 = new Task(2, 0, 0, null, null);
    Task task3 = new Task(3, 0, 0, null, null);

    // ustawienie poprzedników
    prevTasks2.add(task1);
    prevTasks3.add(task1);
    prevTasks3.add(task2);
    task1.setPreviousTasks(prevTasks1);
    task2.setPreviousTasks(prevTasks2);
    task3.setPreviousTasks(prevTasks3);

    // ustawienie następników
    nextTasks1.add(task2);
    nextTasks2.add(task3);
    task1.setNextTasks(nextTasks1);
    task2.setNextTasks(nextTasks2);
    task3.setNextTasks(nextTasks3);

    // dodanie do listy
    TaskManager.addTask(task1);
    TaskManager.addTask(task2);
    TaskManager.addTask(task3);

    // appTasks = TaskManager.getAllTasks();
    TaskManager.displayAllTasks();
  }
}
