import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

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


  public PrivateKey getPrivateKey () throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
  {
      PrivateKey prk;
      String privateKeyPath = "keys" + File.separator + "private.pem";
      File pemPrivate = new File(privateKeyPath);
      FileInputStream in = new FileInputStream(pemPrivate);
      byte [] keyBites = new byte[in.available()];
      in.read(keyBites);
      in.close();

      String privateKey = new String(keyBites, "UTF-8");
      privateKey = privateKey.replaceAll("(-+BEGIN RSA PRIVATE KEY-+\\r?\\n|-+END RSA PRIVATE KEY-+\\r?\\n?)", "");

      PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBites);
      KeyFactory kf = KeyFactory.getInstance("RSA");
      prk = kf.generatePrivate(spec);
      return prk;
  }

   //PrivateKey prk;
   PublicKey pbk;

  public String sign (String message) throws SignatureException
  {
  try
    {
      System.out.println(getPrivateKey());
      Signature sign =  Signature.getInstance("SHA1withRSA");
      sign.initSign(getPrivateKey());
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
