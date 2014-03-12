import java.net.MalformedURLException;

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
            "<verify service=\"" + pservice + "\" account=\"" + paccount +"\"/>\n" +
            "</request>";
  }


}
