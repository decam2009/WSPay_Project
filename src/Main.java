import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.io.File;

/**
 * Created by BORIS on 23/02/14.
 */
public class Main
{
  static
  {
    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier()
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

      /* --Sign and Verify XML */

     /* String filePath = "keys";
      String xmlFile;

      KryptoUtil util = new KryptoUtil();

      util.storeKeyPairs(filePath);
      String xmlFilePath = "xml" + File.separator + "balance.xml";
      String signedXmlFilePath = "xml" + File.separator + "DigitalySignedBal.xml";
      String privateKeyPath = "keys" + File.separator + "private.pem";
      String publicKeyPath = "keys" + File.separator + "public.pem";
      XMLDigitalSignatureGenerator xmlSig = new XMLDigitalSignatureGenerator();
      xmlSig.generateXMLDigitalSignature(xmlFilePath, signedXmlFilePath, privateKeyPath, publicKeyPath);
      try
      {
        boolean validFlag = XMLDigitalSignatureVerifier.isXmlDigitalSignatureValid(signedXmlFilePath, publicKeyPath);
        System.out.println("Validity of XML Digital Signature : " + validFlag);
      }
      catch (Exception ex)
      {
        ex.printStackTrace();
      }*/

      //--Connect to Telepay
      // Send XML to Telepay

      Connect tpcon;
      tpcon = new Connect();
      tpcon.send("xml" + File.separator + "balancepls.xml");
    }
}
