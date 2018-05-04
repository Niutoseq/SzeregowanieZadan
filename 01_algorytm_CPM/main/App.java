import java.util.ArrayList;

public class App
{
  public static void main(String[] args)
  {
    // CZĘŚĆ 1: PRZYGOTOWANIE DANYCH DO OBSŁUGI

    // // maszyny (tablica wstępnie jako null)
    // Machine machine1 = new Machine(1, true, null);
    // Machine machine2 = new Machine(2, true, null);
    // Machine machine3 = new Machine(3, true, null);
    //
    // // dodanie maszyn do listy maszyn
    // MachineManager.addMachine(machine1);
    // MachineManager.addMachine(machine2);
    // MachineManager.addMachine(machine3);

    // zadania (tablica wstępnie jako null)
    Task task1 = new Task(0, 1, 0, 0, new ArrayList<Task>());
    Task task2 = new Task(0, 2, 0, 0, new ArrayList<Task>());
    Task task3 = new Task(0, 2, 0, 0, new ArrayList<Task>());
    Task task4 = new Task(0, 1, 0, 0, new ArrayList<Task>());
    Task task5 = new Task(0, 5, 0, 0, new ArrayList<Task>());
    Task task6 = new Task(0, 4, 0, 0, new ArrayList<Task>());
    Task task7 = new Task(0, 3, 0, 0, new ArrayList<Task>());

    // dodanie zadań do listy zadań
    TaskManager.addTask(task1);
    TaskManager.addTask(task2);
    TaskManager.addTask(task3);
    TaskManager.addTask(task4);
    TaskManager.addTask(task5);
    TaskManager.addTask(task6);
    TaskManager.addTask(task7);


    // CZĘŚĆ 2: GŁÓWNA CZĘŚĆ PROGRAMU

    // wyświetlenie dostępnych maszyn
    // MachineManager.displayAllMachines();
    //
    // System.out.println("--------------------------------");

    // nadanie numerów zadaniom
    TaskManager.assignNumbersToTasks();

    // ustawienie zależności
    TaskManager.addConnection(task4, task1);
    TaskManager.addConnection(task4, task2);
    TaskManager.addConnection(task5, task2);
    TaskManager.addConnection(task5, task3);
    TaskManager.addConnection(task6, task4);
    TaskManager.addConnection(task6, task5);
    TaskManager.addConnection(task7, task6);

    TaskManager.calculateTimes();
    TaskManager.displayAllTasks();

    System.out.println("--------------------------------");

    TaskManager.findCriticalPath();

    // sprawdzić i wprowadzić adekwatne poprawki !!!!!
    String result = TaskManager.displayCriticalPath(TaskManager.tasks);
    String output = new StringBuilder(result).reverse().toString();
    System.out.println(output);

    System.out.println("--------------------------------");
  }
}
