import java.util.ArrayList;

public class App
{
  public static TaskManager tm = new TaskManager();
  public static MachineManager mm = new MachineManager();

  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
  }

  public static void displayHeader()
  {
    clearScreen();
    info(" ╦╔═╗╦ ╦╔╗╔╔═╗╔═╗╔╗╔  ╔═╗╦  ╔═╗╔═╗╦═╗╦╔╦╗╦ ╦╔╦╗");
    info(" ║║ ║╠═╣║║║╚═╗║ ║║║║  ╠═╣║  ║ ╦║ ║╠╦╝║ ║ ╠═╣║║║");
    info("╚╝╚═╝╩ ╩╝╚╝╚═╝╚═╝╝╚╝  ╩ ╩╩═╝╚═╝╚═╝╩╚═╩ ╩ ╩ ╩╩ ╩");
    info("     ┌┬┐┌─┐┌┬┐┬┌─┐┬┌─┐┌┬┐  ┬  ┬┌─┐┬─┐┌─┐┬┌─┐┌┐┌");
    info("───  ││││ │ │││├┤ │├┤  ││  └┐┌┘├┤ ├┬┘└─┐││ ││││");
    info("     ┴ ┴└─┘─┴┘┴└  ┴└─┘─┴┘   └┘ └─┘┴└─└─┘┴└─┘┘└┘");
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

    int tasksNumber = 5;
    int machinesNumber = 3;

    for (int i = 1; i <= tasksNumber; i++)
    {
      tm.addTask(new Task(i, new ArrayList<Integer>(), 0, 0, new Collection()));
    }

    for (int i = 1; i <= machinesNumber; i++)
    {
      mm.addMachine(new Machine(i, new ArrayList<Task>()));
    }

    ArrayList<Integer> tmpDurations1 = new ArrayList<Integer>();
    ArrayList<Integer> tmpDurations2 = new ArrayList<Integer>();
    ArrayList<Integer> tmpDurations3 = new ArrayList<Integer>();
    ArrayList<Integer> tmpDurations4 = new ArrayList<Integer>();
    ArrayList<Integer> tmpDurations5 = new ArrayList<Integer>();

    // --- zadanie 1 ---------------------------
    tmpDurations1.add(4);
    tmpDurations1.add(1);
    tmpDurations1.add(3);
    tm.tasks.get(0).setDurations(tmpDurations1);

    // --- zadanie 2 ---------------------------
    tmpDurations2.add(3);
    tmpDurations2.add(3);
    tmpDurations2.add(5);
    tm.tasks.get(1).setDurations(tmpDurations2);

    // --- zadanie 3 ---------------------------
    tmpDurations3.add(5);
    tmpDurations3.add(2);
    tmpDurations3.add(4);
    tm.tasks.get(2).setDurations(tmpDurations3);

    // --- zadanie 4 ---------------------------
    tmpDurations4.add(6);
    tmpDurations4.add(1);
    tmpDurations4.add(4);
    tm.tasks.get(3).setDurations(tmpDurations4);

    // --- zadanie 5 ---------------------------
    tmpDurations5.add(3);
    tmpDurations5.add(2);
    tmpDurations5.add(4);
    tm.tasks.get(4).setDurations(tmpDurations5);

    tm.calculateModDurations();
    tm.assingCollections();

    tm.prepareSchedules();

    tm.displayAllTasks();
    newLines(1);
    mm.displayAllMachines();
    newLines(1);
  }
}
