import java.io.File;

public class TestXmlSignature {

	public static void main(String[] args)
	{
	  String filePath = "keys";
	  
	  KryptoUtil util = new KryptoUtil();
	  util.storeKeyPairs(filePath);
	  String xmlFilePath = "xml" + File.separator + "employeesalary.xml";
	  String signedXmlFilePath = "xml" + File.separator + "DigitalySignedEmpSel.xml";
	  String privateKeyPath = "keys" + File.separator + "privatekey.key";
	  String publicKeyPath = "keys" + File.separator + "publickey.key";
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
