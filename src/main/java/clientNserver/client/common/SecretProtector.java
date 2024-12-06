package clientNserver.client.common;

import clientNserver.server.common.AuthenticateConstants;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
    public String decrypt( String data ) throws Exception {
        String[] inputString = data.split( ":::" );
        String encryptedSessionKey = inputString[0];
        String encryptedData = inputString[1];
        Cipher cipher = Cipher.getInstance( AuthenticateConstants.KEYGEN_ALGORITHM );
        cipher.init( Cipher.DECRYPT_MODE, this.secretKey );
        byte[] decryptedSessionKeyBytes = cipher.doFinal( Base64.getDecoder().decode( encryptedSessionKey ) );
        SecretKey sessionKey = new SecretKeySpec( decryptedSessionKeyBytes, AuthenticateConstants.ENCRYPTION_ALGORITHM );
        cipher = Cipher.getInstance( AuthenticateConstants.ENCRYPTION_ALGORITHM );
        cipher.init( Cipher.DECRYPT_MODE, sessionKey );
        byte[] decryptedDataBytes = cipher.doFinal( Base64.getDecoder().decode( encryptedData ) );
        return new String( decryptedDataBytes );
    }
}
