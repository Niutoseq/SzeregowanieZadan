import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Graphic
{
  public static TaskManager tm = new TaskManager();
  public static MachineManager mm = new MachineManager();

  public static String color1 = "indigo";         // kolor obramowania
  public static String color2 = "lemonchiffon";   // kolor komórek z zadaniami
  public static String color3 = "orchid";         // kolor nagłówka z czasem
  public static String color4 = "plum";           // kolor kolumny z maszynami
  public static String color5 = "bisque3";        // kolor blanków (przestojów)

  public static void makeSchedule()
  {
    String filenameIn = "schedule.gv";
    String filenameOut = "schedule.ps";
    prepareSchedule(mm.machines);

    try
    {
      Runtime.getRuntime().exec("dot -Tps graphic/in/" + filenameIn + " -o graphic/out/" + filenameOut);
    }
    catch (Exception e)
    {
      System.out.println("Tworzenie harmonogramu nie powiodło się!");
    }

    try
    {
      ProcessBuilder pb = new ProcessBuilder("xdg-open", "graphic/out/" + filenameOut);
      pb.start();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public static void prepareSchedule(ArrayList<Machine> machinesToShedule)
  {
    String mySchedule = new String();

    String begin = "digraph H {\n";
    String body = new String();
    String result = new String();
    String end = "}";

    result = result + "aHtmlTable [shape=plaintext label=<\n";

    int longestSchedule = 0;

    for (Machine machine : mm.machines)
    {
      int currentSchedule = 0;
      for (Task task : machine.getSchedule())
      {
        currentSchedule = currentSchedule + task.getDurations().get(machine.getMachineNumber()-1);
      }

      if (currentSchedule > longestSchedule)
      {
        longestSchedule = currentSchedule;
      }
    }

    result = result + "<table border='1' cellborder='1' color='" + color1 + "' bgcolor='" + color2 + "' cellspacing='0'>\n";
    result = result + "<tr>\n";
    result = result + "<td bgcolor='" + color3 + "'>Time</td>\n";

    for (int i = 0; i < longestSchedule; i++)
    {
      result = result + "<td width='40px' bgcolor='" + color3 + "'>" + i + "-" + (i+1) + "</td>\n";
    }
    result = result + "</tr>\n";

    for (Machine machine : mm.machines)
    {
      result = result + "<tr>\n";
      result = result + "<td bgcolor='" + color4 + "'>" + mm.globalMachineName + machine.getMachineNumber() + "</td>\n";
      for (Task task : machine.getSchedule())
      {
        if (task.getTaskNumber() == 0)
        {
          result = result + "<td bgcolor='" + color5 + "' colspan='" + task.getDurations().get(machine.getMachineNumber()-1) + "'>-</td>\n";
        }
        else
        {
          result = result + "<td colspan='" + task.getDurations().get(machine.getMachineNumber()-1) + "'>" + tm.globalTaskName + task.getTaskNumber() + "</td>\n";
        }
      }
      result = result + "</tr>\n";
    }

    result = result + "</table>\n>];\n";

    body = result;
    mySchedule = begin + body + end;
    // System.out.println(mySchedule);

    try {
      PrintWriter writer = new PrintWriter("graphic/in/schedule.gv", "UTF-8");
      writer.println(mySchedule);
      writer.close();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
