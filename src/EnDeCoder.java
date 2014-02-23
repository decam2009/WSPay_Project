import org.apache.commons.codec.binary.Base64;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

/**
 * Created by BORIS on 21/02/14.
 */
public class EnDeCoder
{
        PrivateKey prk;
        PublicKey pbk;

        public String sign (String message) throws SignatureException
        {
            try
            {
                Signature sign =  Signature.getInstance("SHA1withRSA");
                sign.initSign(prk);
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
                sign.initVerify(pbk);
                sign.update(message.getBytes("UTF-8"));
                return sign.verify(Base64.encodeBase64(signature.getBytes()));
            }
            catch (Exception e)
            {
                throw new SignatureException (e);
            }
        }
}
