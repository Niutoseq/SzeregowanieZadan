import java.util.ArrayList;

public class App
{
  public static void displayHeader()
  {
    System.out.print("\033[H\033[2J");
    System.out.println("╔═╗╔═╗╔╦╗  ╔═╗╦  ╔═╗╔═╗╦═╗╦╔╦╗╦ ╦╔╦╗");
    System.out.println("║  ╠═╝║║║  ╠═╣║  ║ ╦║ ║╠╦╝║ ║ ╠═╣║║║");
    System.out.println("╚═╝╩  ╩ ╩  ╩ ╩╩═╝╚═╝╚═╝╩╚═╩ ╩ ╩ ╩╩ ╩");
    // System.out.print("\n");
  }

  public static void displayFrame()
  {
    System.out.println("-------------------------------------------");
  }

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
    Task task1 = new Task(0, 1, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task2 = new Task(0, 2, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task3 = new Task(0, 2, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task4 = new Task(0, 1, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task5 = new Task(0, 5, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task6 = new Task(0, 4, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task7 = new Task(0, 3, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task8 = new Task(0, 7, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());
    Task task9 = new Task(0, 3, 0, 0, new ArrayList<Task>(), new ArrayList<Task>());

    // dodanie zadań do listy zadań
    TaskManager.addTask(task1);
    TaskManager.addTask(task2);
    TaskManager.addTask(task3);
    TaskManager.addTask(task4);
    TaskManager.addTask(task5);
    TaskManager.addTask(task6);
    TaskManager.addTask(task7);
    TaskManager.addTask(task8);
    TaskManager.addTask(task9);


    // CZĘŚĆ 2: GŁÓWNA CZĘŚĆ PROGRAMU

    // wyświetlenie dostępnych maszyn
    // MachineManager.displayAllMachines();
    //
    // displayFrame();

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
    TaskManager.addConnection(task8, task6);
    TaskManager.addConnection(task9, task8);

    TaskManager.calculateTimes();

    displayHeader();
    displayFrame();
    TaskManager.displayTasksScheme();
    displayFrame();
    TaskManager.displayAllTasks();

    // displayFrame();
    // TaskManager.findCriticalPath(TaskManager.tasks);
    // displayFrame();

    // sprawdzić i wprowadzić adekwatne poprawki !!!!!
    // TaskManager.displayCriticalPath(TaskManager.tasks);

    // displayFrame();
    // TaskManager.displayAllPaths(TaskManager.tasks, "");

    displayFrame();
    TaskManager.criticalPathDisplayer(TaskManager.tasks);
    displayFrame();
  }
}
