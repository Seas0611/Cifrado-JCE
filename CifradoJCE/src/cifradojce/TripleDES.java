package cifradojce;

import java.io.UnsupportedEncodingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.InvalidAlgorithmParameterException;
import java.security.MessageDigest;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;

import sun.misc.BASE64Encoder;

/**
 * @author Joe Prasanna Kumar
 * @author Sergio Aponte
 * @author Alejandro Ramirez
 * This program provides the following cryptographic functionalities
 * 1. Encryption using 3DES
 * 2. Decryption using 3DES
 * 
 * High Level Algorithm :
 * 1. Generate a TripleDES key (specify the Key size during this phase) 
 * 2. Create the Cipher 
 * 3. To Encrypt : Initialize the Cipher for Encryption
 * 4. To Decrypt : Initialize the Cipher for Decryption
 * 
 */

public class TripleDES {
	public static void main(String[] args) throws InvalidKeyException, IllegalBlockSizeException, UnsupportedEncodingException {
		
		String strDataToEncrypt = new String();
		String strCipherText = new String();
		String strDecryptedText = new String();
		
		try{
		/**
		 *  Step 1. Generate an AES key using KeyGenerator
		 *  		Initialize the keysize to 168 
		 * 
		 */
		KeyGenerator keyGen = KeyGenerator.getInstance("DESede");
		keyGen.init(168);
		SecretKey secretKey = keyGen.generateKey();
		
		/**
		 *  Step2. Create a Cipher by specifying the following parameters
		 * 			a. Algorithm name - here it is TripleDes como DESede
		 */
		
		Cipher aesCipher = Cipher.getInstance("DESede");
		
		/**
		 *  Step 3. Initialize the Cipher for Encryption 
		 */
		
		aesCipher.init(Cipher.ENCRYPT_MODE,secretKey);
		
		/**
		 *  Step 4. Encrypt the Data
		 *  		1. Declare / Initialize the Data. Here the data is of type String
		 *  		2. Convert the Input Text to Bytes
		 *  		3. Encrypt the bytes using doFinal method 
		 */
		strDataToEncrypt = "I am learning to cypher sensitive data employing JCE in my SDSW course";
		byte[] byteDataToEncrypt = strDataToEncrypt.getBytes();
		byte[] byteCipherText = aesCipher.doFinal(byteDataToEncrypt);
                strCipherText = new BASE64Encoder().encode(byteCipherText);
		System.out.println("Cipher Text generated using TripleDES is " +strCipherText);
                
                
                MessageDigest md1 = MessageDigest.getInstance("SHA1");
                System.out.println("Message digest object info: ");
                System.out.println("   Algorithm = "+md1.getAlgorithm());
                System.out.println("   Provider = "+md1.getProvider());
                System.out.println("   toString = "+md1.toString());

                
                md1.update(strCipherText.getBytes()); 
                byte[] output = md1.digest();
                System.out.println();
                System.out.println("SHA1(\""+strCipherText+"\") =");
                System.out.println(" es:  "+bytesToHex(output));
                
                /*
                String input;
                input = "abc";
                md1.update(input.getBytes()); 
                output = md1.digest();
                System.out.println();
                System.out.println("SHA1(\""+input+"\") =");
                System.out.println(" es:  "+bytesToHex(output));

                input = "abcdefghijklmnopqrstuvwxyz";
                md1.update(input.getBytes()); 
                output = md1.digest();
                System.out.println();
                System.out.println("SHA1(\""+input+"\") =");
                System.out.println(" es:  "+bytesToHex(output));
                */
    
                /**
		 *  Step 5. Decrypt the Data
		 *  		1. Initialize the Cipher for Decryption 
		 *  		2. Decrypt the cipher bytes using doFinal method 
		 */
		aesCipher.init(Cipher.DECRYPT_MODE,secretKey,aesCipher.getParameters());
		byte[] byteDecryptedText = aesCipher.doFinal(byteCipherText);
		strDecryptedText = new String(byteDecryptedText);
		System.out.println("Decrypted Text message is " +strDecryptedText);
                
              
                
                
                
		}
		
		catch (NoSuchAlgorithmException noSuchAlgo)
		{
			System.out.println(" No Such Algorithm exists " + noSuchAlgo);
		}
		
			catch (NoSuchPaddingException noSuchPad)
			{
				System.out.println(" No Such Padding exists " + noSuchPad);
			}
		
				catch (InvalidKeyException invalidKey)
				{
					System.out.println(" Invalid Key " + invalidKey);
				}
				
				catch (BadPaddingException badPadding)
				{
					System.out.println(" Bad Padding " + badPadding);
				}
				
				catch (IllegalBlockSizeException illegalBlockSize)
				{
					System.out.println(" Illegal Block Size " + illegalBlockSize);
				}
				
				catch (InvalidAlgorithmParameterException invalidParam)
				{
					System.out.println(" Invalid Parameter " + invalidParam);
				}
	}

    private static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
                         '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
      StringBuffer buf = new StringBuffer();
      for (int j=0; j<b.length; j++) {
         buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
         buf.append(hexDigit[b[j] & 0x0f]);
      }
      return buf.toString();

    }

}
