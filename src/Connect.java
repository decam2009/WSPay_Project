import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by BORIS on 24/02/14.
 */
public class Connect
{
    public static HttpsURLConnection con;


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

    public void send (String pfile)
    {
      final String address = "https://dealer.telepayural.ru:8182/external/process";
      File xmlFile = new File (pfile);
      URL url = null;
      try
      {
        url = new URL(address);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes (pfile);
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();
        System.out.println(con.getURL().getFile());
        System.out.println("Response code :" + responseCode);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputline;
        StringBuffer response = new StringBuffer();
        while ((inputline = in.readLine()) != null)
        {
          response.append(inputline);
        }
        in.close();
        System.out.println(response.toString());
      }
      catch (MalformedURLException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
}
