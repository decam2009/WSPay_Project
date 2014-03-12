import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by BORIS on 23/02/14.
 */
public class Main
{
  static
  {
    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier
    (new HostnameVerifier()
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

  public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InvalidKeySpecException, SignatureException {

    System.setProperty("jsse.enableSNIExtension", "false");

    int menuID = 0;

    Request request = new Request();
    Transfer transfer = new Transfer("https://dealer.telepayural.ru:8182/external/process");
    String requestText = request.verifyNumber(1, "9128160770");

    String requestSignature = request.sign(requestText); // Запрос с подписью.

    if (!request.verify(requestText, requestSignature))
      {
        System.out.println("Error: Signature was not verified !");
      }
    else
    {
      transfer.SendHeaders(requestSignature);
      transfer.SendRequest(requestText);
      System.out.println(requestText);
      System.out.println(transfer.ReceiveAnswer());
    }
  }
}