import java.io.IOException;

/**
 * Created by BORIS on 23/02/14.
 */
public class Main
{
    public static void main(String[] args)
    {
      Connect tpcon;
      tpcon = new Connect();
      tpcon.Start();
      try
      {
        tpcon.test(Connect.con);
      } catch (IOException e)
      {
        e.printStackTrace();
      }

    }
}
