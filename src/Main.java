import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.security.SignatureException;

/**
 * Created by BORIS on 23/02/14.
 */
public class Main
{
  static
  {
    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier (new HostnameVerifier()
    {
      public boolean verify (String hostname, SSLSession sslSession)
      {
        if (hostname.equals("dealer.telepayural.ru"))
          {
            return true;
          }
          return false;
      }
    });
  }

  public static void main(String[] args)
  {
    System.setProperty("jsse.enableSNIExtension", "false");
    Connect tpcon;
    String xmlPath = "xml" + File.separator + "balancepls.xml";
    String privateKeyfilePath = "keys" + File.separator + "private.pem";
    String publicKeyfilePath = "keys" + File.separator + "public.pem";

    tpcon = new Connect();
   // System.out.println(tpcon.getXmlFileAsString(xmlPath));
    try
    {
      System.out.println(tpcon.sign("П"));
      System.out.println(tpcon.verify("П",tpcon.sign("П")));
    }
    catch (SignatureException e)
    {
      e.printStackTrace();
    }
    // tpcon.getStoredPrivateKey(privateKeyfilePath);
   // tpcon.send("xml" + File.separator + "balancepls.xml");
  }
}
