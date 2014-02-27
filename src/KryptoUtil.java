import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


public class KryptoUtil 
{

	private static final String ALGORITHM = "RSA";
	
	private KeyPair generateKeyPairs()
	{
	  KeyPair keyPair = null;
	  KeyPairGenerator keyGen;
	  try
	  {
		keyGen = KeyPairGenerator.getInstance(ALGORITHM);
		keyGen.initialize(1024);
		keyPair = keyGen.genKeyPair();
	  }
	  catch(NoSuchAlgorithmException ex)
	  {
		  ex.printStackTrace();
	  }
	  return keyPair;	
	}
	
	public void storeKeyPairs (String dirPath)
	{
		KeyPair keyPair = generateKeyPairs();
		PrivateKey privateKey = keyPair.getPrivate();
		PublicKey publicKey = keyPair.getPublic();
		storeKeys (dirPath+File.separator+"publickey.key", publicKey);
		storeKeys (dirPath+File.separator+"privatekey.key", privateKey);
	}
	
	private void storeKeys (String filePath, Key key)
	{
	  byte[] keyBytes = key.getEncoded();
	  OutputStream outStream = null;
	  try
	  {
	    outStream = new FileOutputStream(filePath);
	    outStream.write(keyBytes);
	  }
	  catch(Exception ex)
	  {
	    ex.printStackTrace();
	  } 
	  finally
	  {
	    if (outStream != null)
	    {
	      try
	      {
	    	  outStream.close();
	      }
	      catch (IOException ex)
	      {
	    	  ex.printStackTrace();
	      }
	    }
	  }
	}
	
	public byte[] getKeyData (String filePath)
	{
	  File file = new File(filePath);
	  byte[] buffer = new byte[(int) file.length()];
	  FileInputStream fis = null;
	  try
	  {
	    fis = new FileInputStream(file);
	    fis.read(buffer);
	  }
	  catch(FileNotFoundException ex)
	  {
		  ex.printStackTrace();
	  }
	  catch(IOException ex)
	  {
		  ex.printStackTrace();
	  }
	  finally
	  {
		  if (fis != null)
		    {
			  try
			  {
			    fis.close();
			  }
			  catch(IOException ex)
			  {
			    ex.printStackTrace();
			  }
		    }
	  }
	  return buffer;  	
	}
	
	public PrivateKey getStoredPrivateKey(String filePath) 
	{
	  
		PrivateKey privateKey = null;
		byte[] keydata = getKeyData(filePath);
		PKCS8EncodedKeySpec encodedPrivateKey = new PKCS8EncodedKeySpec(keydata);
		KeyFactory keyFactory = null;
		try
		{
	      keyFactory = KeyFactory.getInstance("RSA");
		}
		catch(NoSuchAlgorithmException ex)
		{
		  ex.printStackTrace();
		}
		try
		{
		  privateKey = keyFactory.generatePrivate(encodedPrivateKey);
		}
		catch (InvalidKeySpecException ex)
		{
		  ex.printStackTrace();
		}
		return privateKey;
	}
	
	public PublicKey getStoredPublicKey (String filePath)
	{
	  PublicKey publicKey = null;
	  byte[] keydata = getKeyData (filePath);
	  KeyFactory keyFactory = null;
	  try
	  {
	    keyFactory = KeyFactory.getInstance("RSA");
	  }
	  catch(NoSuchAlgorithmException ex)
	  {
	    ex.printStackTrace();
	  }
	  X509EncodedKeySpec encodedPublicKey = new X509EncodedKeySpec(keydata);
	  try
	  {
	    publicKey = keyFactory.generatePublic(encodedPublicKey);
	  }
	  catch(NullPointerException ex)
	  {
		  ex.printStackTrace();
	  }
	  catch(InvalidKeySpecException ex)
	  {
		  ex.printStackTrace();
	  }
	  return publicKey;
	}

}
