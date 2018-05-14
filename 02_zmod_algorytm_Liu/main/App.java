import java.util.ArrayList;

public class App
{
  public static void clearScreen()
  {
    System.out.print("\033[H\033[2J");
  }

  public static void displayHeader()
  {
    System.out.println("    ╦  ╦╦ ╦  ╔═╗╦  ╔═╗╔═╗╦═╗╦╔╦╗╦ ╦╔╦╗    ");
    System.out.println("    ║  ║║ ║  ╠═╣║  ║ ╦║ ║╠╦╝║ ║ ╠═╣║║║    ");
    System.out.println("    ╩═╝╩╚═╝  ╩ ╩╩═╝╚═╝╚═╝╩╚═╩ ╩ ╩ ╩╩ ╩    ");
    System.out.println("┌┬┐┌─┐┌┬┐┬┌─┐┬┌─┐┌┬┐  ┬  ┬┌─┐┬─┐┌─┐┬┌─┐┌┐┌");
    System.out.println("││││ │ │││├┤ │├┤  ││  └┐┌┘├┤ ├┬┘└─┐││ ││││");
    System.out.println("┴ ┴└─┘─┴┘┴└  ┴└─┘─┴┘   └┘ └─┘┴└─└─┘┴└─┘┘└┘");
    System.out.print("\n");
  }

  public static void closeApp(String info)
  {
    System.out.println(info);
    System.exit(0);
  }

  public static void main(String argv[])
  {
    clearScreen();
    displayHeader();

    // schemat: numer, trwanie, przybycie, stop, modStop, prev, next
    TaskManager.addTask(new Task(1, 3, 0, 4, 0, new ArrayList<Task>(), new ArrayList<Task>()));
    TaskManager.addTask(new Task(2, 2, 4, 6, 0, new ArrayList<Task>(), new ArrayList<Task>()));
    TaskManager.addTask(new Task(3, 2, 2, 8, 0, new ArrayList<Task>(), new ArrayList<Task>()));
    TaskManager.addTask(new Task(4, 1, 5, 15, 0, new ArrayList<Task>(), new ArrayList<Task>()));
    TaskManager.addTask(new Task(5, 4, 6, 10, 0, new ArrayList<Task>(), new ArrayList<Task>()));
    TaskManager.addTask(new Task(6, 1, 15, 20, 0, new ArrayList<Task>(), new ArrayList<Task>()));
    TaskManager.addTask(new Task(7, 2, 13, 25, 0, new ArrayList<Task>(), new ArrayList<Task>()));

    TaskManager.addConnection(1, 3);
    TaskManager.addConnection(2, 4);
    TaskManager.addConnection(3, 5);
    TaskManager.addConnection(4, 5);
    TaskManager.addConnection(4, 6);
    TaskManager.addConnection(5, 7);
    TaskManager.addConnection(6, 7);

    TaskManager.assignModifiedFinishTime();
    TaskManager.displayAllTasks();
    System.out.print("\n");
    TaskManager.makeSchedule();
    System.out.print("\n");
  }
}
