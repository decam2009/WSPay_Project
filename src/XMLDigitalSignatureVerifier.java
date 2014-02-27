import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.PublicKey;

import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLDigitalSignatureVerifier 
{
  private static Document getXmlDocument (String xmlFilePath)
  {
	  Document doc = null; 
	  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	  dbf.setNamespaceAware(true);
	  try
	  {
	    doc = dbf.newDocumentBuilder().parse(new FileInputStream(xmlFilePath));
	  }
	  catch(ParserConfigurationException ex)
	  {
		  ex.printStackTrace();
	  }
	  catch(FileNotFoundException ex)
	  {
		  ex.printStackTrace();
	  }
	  catch(SAXException ex)
	  {
		ex.printStackTrace();
	  }
	  catch(IOException ex)
	  {
		 ex.printStackTrace();
	  }
	  return doc;
  }
  
  public static boolean isXmlDigitalSignatureValid (String signedXmlFilePath, String publicKeyFilePath) throws Exception
  {
	  boolean validFlag;
	  Document doc = getXmlDocument(signedXmlFilePath);
	  NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
	  if (nl.getLength() == 0)
	    {
		  throw new Exception("No XML Digital Signature found, document is discarded");  
	    }
	  PublicKey publicKey = new KryptoUtil().getStoredPublicKey(publicKeyFilePath);
	  DOMValidateContext valContext = new DOMValidateContext(publicKey, nl.item(0));
	  XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
	  XMLSignature signature = fac.unmarshalXMLSignature(valContext);
	  validFlag = signature.validate(valContext);
	  return validFlag;
  }
}
