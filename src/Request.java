import java.net.MalformedURLException;
import java.util.Date;

/**
 * Created by BORIS on 11/03/14.
 */
public class Request extends Signature
{

  private int point = 21173;

  public String getBalance ()
  {
      return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
              "<request point=\"" + this.point + "\"> \n" +
              "<balance/>\n" +
              "</request>";
  }

  public String verifyNumber (int pservice, String paccount)
  {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<request point=\"" + this.point + "\"> \n" +
            "<verify service=\"" + Integer.toString(pservice) + "\" account=\"" + paccount +"\"/>\n" +
            "</request>";
  }

  public String getPayment (int pid, int psum, int pcheck, int pservice, String paccount)
  {
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<request point=\"" + this.point + "\"> \n" +
            "<payment id=\"" + pid + "\" sum=\"" + psum + "\" check=\"" + pcheck + "\" service=\"" + pservice + "\"\n" +
            "account=\"" + paccount + "\" date=\"2014-15-03T04:00:00+0600\"/>\n" +
            "</request>";
  }

   public String getStatus (int pid)
   {
     return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
             "<request point=\"" + this.point + "\"> \n" +
              "<status id=\"" + pid + "\"/>\n" +
              "</request>";

   }

}
