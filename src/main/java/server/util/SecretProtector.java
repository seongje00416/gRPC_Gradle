package server.util;

import server.common.AuthenticateConstants;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class SecretProtector {
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public SecretProtector() {
        try{
            KeyPairGenerator keygen = KeyPairGenerator.getInstance( AuthenticateConstants.KEYGEN_ALGORITHM );
            keygen.initialize( AuthenticateConstants.KEY_SIZE );
            KeyPair keyPair = keygen.generateKeyPair();
            this.privateKey = keyPair.getPrivate();
            this.publicKey = keyPair.getPublic();
        } catch( Exception e ) {System.out.println( "Encryptor Error" );}
    }
    public PublicKey getPublicKey() { return this.publicKey; }
    public String decrypt( String data ) throws Exception {
        String[] inputString = data.split( ":::" );
        String encryptedSessionKey = inputString[0];
        String encryptedData = inputString[1];
        Cipher cipher = Cipher.getInstance( AuthenticateConstants.KEYGEN_ALGORITHM );
        cipher.init( Cipher.DECRYPT_MODE, this.privateKey );
        byte[] decryptedSessionKeyBytes = cipher.doFinal( Base64.getDecoder().decode( encryptedSessionKey ) );
        SecretKey sessionKey = new SecretKeySpec( decryptedSessionKeyBytes, AuthenticateConstants.ENCRYPTION_ALGORITHM );
        cipher = Cipher.getInstance( AuthenticateConstants.ENCRYPTION_ALGORITHM );
        cipher.init( Cipher.DECRYPT_MODE, sessionKey );
        byte[] decryptedDataBytes = cipher.doFinal( Base64.getDecoder().decode( encryptedData ) );
        return new String( decryptedDataBytes );
    }
}
