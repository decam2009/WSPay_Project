import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openssl.PEMParser;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by BORIS on 21/02/14.
 */
public class EnDeCoder
{
  public byte[] getKeyData (String filePath)
  {
    File file = new File(filePath);
    byte[] buffer = new byte [(int) file.length()];
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

  PrivateKey prk = getStoredPrivateKey("keys" + File.separator + "private.pem");
  PublicKey pbk = getStoredPublicKey("keys" + File.separator + "public.pem");

  public String sign (String message) throws SignatureException
  {
  try
    {
      System.out.println(this.prk);
      Signature sign =  Signature.getInstance("SHA1withRSA");
      sign.initSign(this.prk);
      sign.update(message.getBytes("UTF-8"));
      return new String(Base64.decodeBase64(sign.sign()));
    }
  catch (Exception e)
    {
      throw new SignatureException (e);
    }
  }

  public boolean verify (String message, String signature) throws SignatureException
  {
    try
    {
      Signature sign = Signature.getInstance("SHA1withRSA");
      sign.initVerify(this.pbk);
      sign.update(message.getBytes("UTF-8"));
      return sign.verify(Base64.encodeBase64(signature.getBytes()));
    }
    catch (Exception e)
    {
      throw new SignatureException (e);
    }
  }
}
