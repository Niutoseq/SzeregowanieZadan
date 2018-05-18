import java.util.ArrayList;

public class App
{
  public static TaskManager tm = new TaskManager();
  public static MachineManager mm = new MachineManager();
  public static Graphic graphic = new Graphic();

  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
  }

  public static void displayHeader()
  {
    clearScreen();
    info("╔═╗╦  ╔═╗╔═╗╦═╗╦ ╦╔╦╗╔╦╗  ╦ ╦╦ ╦");
    info("╠═╣║  ║ ╦║ ║╠╦╝╚╦╝ ║ ║║║  ╠═╣║ ║");
    info("╩ ╩╩═╝╚═╝╚═╝╩╚═ ╩  ╩ ╩ ╩  ╩ ╩╚═╝");
  }

  public static void info(String text)
  {
    System.out.println(text);
  }

  public static void newLines(int linesNumber)
  {
    for (int i = 0; i < linesNumber; i++)
    {
      info("");
    }
  }

  public static void closeApp(String text)
  {
    info(text);
    System.exit(0);
  }

  public static void main(String args[])
  {
    displayHeader();
    newLines(1);

    int tasksNumber = 12;             // liczba zadań do wykonania
    int machinesNumber = 3;           // liczba dostępnych maszyn

    if (tasksNumber <= 0 || machinesNumber <= 0)
    {
      closeApp("Błąd! Liczba zadań/maszyn musi wynosić co najmniej 1!");
    }

    for (int i = 1; i <= tasksNumber; i++)
    {
      tm.addTask(new Task(i, tm.globalTaskDuration, 0, 0, 0, new ArrayList<Task>(), new ArrayList<Task>(), false));
    }

    for (int i = 1; i <= machinesNumber; i++)
    {
      mm.addMachine(new Machine(i, new ArrayList<Task>()));
    }

    tm.addConnection(1, 7);
    tm.addConnection(2, 7);
    tm.addConnection(3, 8);
    tm.addConnection(4, 9);
    tm.addConnection(5, 9);
    tm.addConnection(6, 10);
    tm.addConnection(7, 10);
    tm.addConnection(8, 11);
    tm.addConnection(9, 11);
    tm.addConnection(10, 12);
    tm.addConnection(11, 12);

    graphic.makeGraphic("graph");

    tm.calculateTimes();
    tm.setLevels();

    tm.displayAllTasks();
    newLines(1);

    mm.prepareSchedules();
    mm.displayAllMachines();

    graphic.makeGraphic("schedule");
    newLines(1);
  }
}
