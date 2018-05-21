import java.util.ArrayList;

public class App
{
  public static TaskManager tm = new TaskManager();
  public static MachineManager mm = new MachineManager();
  public static DataFromFile ddf = new DataFromFile();
  public static Graphic graphic = new Graphic();

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

    String filename = "dane.txt";
    ddf.prepareData(filename);

    tm.calculateModDurations();
    tm.assingCollections();

    tm.prepareSchedules();

    tm.displayAllTasks();
    newLines(1);
    mm.displayAllMachines();
    newLines(1);
    graphic.makeSchedule();
  }
}
