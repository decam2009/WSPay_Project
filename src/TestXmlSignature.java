import java.io.File;

public class TestXmlSignature {

	public static void main(String[] args)
	{
	  String filePath = "keys";
	  
	 // KryptoUtil util = new KryptoUtil();
	 // util.storeKeyPairs(filePath);
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
	  }
	}

}
