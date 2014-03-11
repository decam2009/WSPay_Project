import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by BORIS on 05/03/14.
 */

public class Signature
{
  public PrivateKey getPrivateKey () throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
  {
    File pemPrivate = new File ("keys" + File.separator + "privated.der");
    FileInputStream fis = new FileInputStream(pemPrivate);
    byte [] keyBytes = new byte[fis.available()];
    fis.read(keyBytes);
    fis.close();

    PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);

    KeyFactory kf = KeyFactory.getInstance("RSA");
    PrivateKey privateKey = kf.generatePrivate(spec);
    return privateKey;
  }

  public PublicKey getPublicKey () throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
  {

    File pemPublic = new File("keys" + File.separator + "publicd.der");
    FileInputStream fis = new FileInputStream(pemPublic);
    byte[] keyBytes = new byte [fis.available()];
    fis.read(keyBytes);
    fis.close();

    X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);

    KeyFactory kf = KeyFactory.getInstance("RSA");
    PublicKey publicKey = kf.generatePublic(spec);
    return  publicKey;
  }

  public String sign (String pmessage) throws SignatureException
  {
    try
    {
      java.security.Signature sign =  java.security.Signature.getInstance("SHA1withRSA");
      sign.initSign(getPrivateKey());
      sign.update(pmessage.getBytes("UTF-8"));
      return new String(Base64.encodeBase64(sign.sign()),"UTF-8");
    }
    catch (Exception e)
    {
      throw new SignatureException (e);
    }
  }

  public boolean verify (String pmessage, String psignature) throws SignatureException
  {
    try
    {
      java.security.Signature sign = java.security.Signature.getInstance("SHA1withRSA");
      sign.initVerify(getPublicKey());
      sign.update(pmessage.getBytes("UTF-8"));
      return sign.verify(Base64.decodeBase64(psignature.getBytes()));
    }
    catch (Exception e)
    {
      throw new SignatureException (e);
    }
  }
}
