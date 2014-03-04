import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BORIS on 24/02/14.
 */
public class Connect extends EnDeCoder
{
    public static HttpsURLConnection con;
    public String allXmlFile = "";



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

    public String getXmlFileAsString (String pfile)
    {
      File xmlFile = new File(pfile);
      String XmlFileAsText = "";
      try
      {
        FileReader fileReader = new FileReader(xmlFile);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        List<String> arr = new ArrayList<String>();
        String line = "";
        while ((line = bufferedReader.readLine()) != null)
        {
          arr.add(line);
        }
        bufferedReader.close();
        for (String item : arr)
          {
            XmlFileAsText = XmlFileAsText + item;
          }
      } catch (FileNotFoundException e)
      {
        e.printStackTrace();
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
      return XmlFileAsText;
    }

    public void send (String pfile)
    {
      final String address = "https://dealer.telepayural.ru:8182/external/process";
      URL url = null;
      try
      {
        url = new URL(address);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("PayLogic-Signature","ga6puFY5VZwuOH2oQNKok+JetQ+13h0FT3x2ylv8gwnELsFZ7lJs+mVzOliVaAYvlEpQXexsAjGDibM60mFsQf9C9yI8Tkmit10x9CL+o4d0eP7ItWmiwdEnFphuwjUBX14XZ/D+Gr9qwz+R8Y338cXGm7kBHnSvaz/teqLKjQE=");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(this.allXmlFile);
        System.out.println(con.getRequestProperty("PayLogic-Signature"));
        wr.flush();
        wr.close();
        int responseCode = con.getResponseCode();

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
