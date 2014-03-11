import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
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

    Request request = new Request();

    String requestSignature = request.sign(request.allXmlFile); // Запрос с подписью.

    if (!request.verify(request.allXmlFile, requestSignature))
      {
        System.out.println("Error: Signature was not verified !");
      }
    else
    {
      request.send(requestSignature);
    }
  }
}