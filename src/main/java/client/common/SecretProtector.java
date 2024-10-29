package client.common;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.PublicKey;
import java.util.Base64;

public class SecretProtector {
    private final SecretKey secretKey;
    private final PublicKey publicKey;

    public SecretProtector( PublicKey serverPublicKey ) throws Exception {
        this.publicKey = serverPublicKey;
        KeyGenerator keyGenerator = KeyGenerator.getInstance( ClientConstants.ENCRYPT_ALGORITHM );
        keyGenerator.init( ClientConstants.KEY_SIZE );
        this.secretKey = keyGenerator.generateKey();
    }
    public String encrypt( String data ) throws Exception {
        Cipher cipher = Cipher.getInstance( ClientConstants.ENCRYPT_ALGORITHM );
        cipher.init( Cipher.ENCRYPT_MODE, this.publicKey );
        String encryptedSessionKey = Base64.getEncoder().encodeToString( cipher.doFinal( this.secretKey.getEncoded() ) );
        cipher = Cipher.getInstance( ClientConstants.ENCRYPT_ALGORITHM );
        cipher.init( Cipher.ENCRYPT_MODE, this.secretKey );
        String encryptedData = Base64.getEncoder().encodeToString( cipher.doFinal( data.getBytes() ) );
        return encryptedSessionKey + ":::" + encryptedData;
    }
}
