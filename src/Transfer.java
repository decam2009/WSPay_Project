import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by BORIS on 24/02/14.
 */
public class Transfer extends Signature
{
    private static HttpsURLConnection con;
    private  static URL url = null;

    Transfer (String paddress) throws MalformedURLException {
      this.url = new URL (paddress);
    }

    public void SendHeaders(String psignature) throws IOException
    {
      this.con = (HttpsURLConnection) this.url.openConnection();
      this.con.setRequestMethod("POST");
      this.con.setRequestProperty("PayLogic-Signature", psignature);
      this.con.setDoOutput(true);
    }

    public void SendRequest(String prequestXML) throws IOException
    {
      DataOutputStream dos = new DataOutputStream(this.con.getOutputStream());
      dos.writeBytes(prequestXML);
      dos.flush();
      dos.close();
    }

    public String ReceiveAnswer () throws IOException
    {
      int responceCode = this.con.getResponseCode();
      String inputline;
      StringBuffer response = new StringBuffer();

      System.out.println("Response code :" + responceCode);
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
      while((inputline = bufferedReader.readLine()) != null)
      {
        response.append(inputline);
      }
      bufferedReader.close();
      return response.toString();
    }
}
