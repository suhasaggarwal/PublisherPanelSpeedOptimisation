package util;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;


public class GenerateToken {


public static String getEncryptedToken(String username){
	
	
	    String key = "Bar12345Bar12345"; // 128 bit key
        String initVector = "RandomInitVector";
	    String ts = String.valueOf(System.currentTimeMillis());
	 //   String rand = UUID.randomUUID().toString();
	    String emailId = username;
	    //String hash =  DigestUtils.sha1Hex(ts + emailId);
	    String encryptedtext = EncryptionModule.encrypt(null,null,emailId);
        return encryptedtext;
  



}


public static String getDecryptedToken(String token){
	
	
    String key = "Bar12345Bar12345"; // 128 bit key
    String initVector = "RandomInitVector";
    String ts = String.valueOf(System.currentTimeMillis());
    String rand = UUID.randomUUID().toString();
    String hash =  DigestUtils.sha1Hex(ts + rand);
    String token1 = EncryptionModule.decrypt(key, initVector,token);

return token1;



}





}
