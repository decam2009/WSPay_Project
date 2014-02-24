import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by BORIS on 24/02/14.
 */
public class Connect
{
    public static HttpsURLConnection con;

    public void Start()
    {
        String address = "https://dealer.telepayural.ru:8182/external/process/";
        URL url;
        try
        {
          url = new URL(address);
          this.con = (HttpsURLConnection) url.openConnection();
        }
        catch (MalformedURLException mf)
        {
          mf.printStackTrace();
        }
        catch (IOException cn)
        {
          cn.printStackTrace();
        }
    }

    public void test(HttpsURLConnection pcon) throws IOException
    {
      try
      {
        System.out.println("----------Content of the URL----------");
        BufferedReader br = new BufferedReader(new InputStreamReader(pcon.getInputStream()));
        String input;
        while ((input = br.readLine()) != null)
        {
          System.out.println(input);
        }
        br.close();
      }
      catch(IOException e)
      {
        e.getStackTrace();
      }
    }
}
