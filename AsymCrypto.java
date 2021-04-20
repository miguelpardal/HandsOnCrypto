// default package

// provides helper methods to print byte[]
import static jakarta.xml.bind.DatatypeConverter.printHexBinary;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

/**
 * Public key cryptography using the RSA algorithm.
 */
public class AsymCrypto {

	/** Print output? */
	private static boolean outputFlag = true; 
	
	/** Helper method to control output with flag */
	private static void println(Object o) {
		if (outputFlag)
			System.out.println(o);
	}
	
	/** Main method */
	public static void main(String[] args) throws Exception {

		// check args, get key size and plaintext
		if (args.length != 2) {
			System.err.println("args: keyBits (text)");
			return;
		}
		final int keySize = Integer.parseInt(args[0]);
		println("Key size (in bits): " + keySize);

		final String plainText = args[1];
		final byte[] plainBytes = plainText.getBytes();
		println("Text:");
		println(plainText);
		println("Bytes:");
		println(printHexBinary(plainBytes));

		// generate an RSA key
		println("Start generating RSA keys");
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(keySize);
		KeyPair keys = keyGen.generateKeyPair();
		println("Finish generating RSA keys");

		println("Private Key:");
		println(printHexBinary(keys.getPrivate().getEncoded()));
		println("Public Key:");
		println(printHexBinary(keys.getPublic().getEncoded()));

		// get an RSA cipher object and print the provider
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		println(cipher.getProvider().getInfo());

		// encrypt the plaintext using the public key
		println("Text:");
		println(plainText);
		println("Bytes:");
		println(printHexBinary(plainBytes));

		println("Ciphering with public key ...");
		cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic());
		byte[] cipherBytes = cipher.doFinal(plainBytes);

		println("Result:");
		println(printHexBinary(cipherBytes));

		// decrypt the ciphertext using the private key
		println("Deciphering with private key ...");
		cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
		byte[] newPlainBytes = cipher.doFinal(cipherBytes);
		println("Result:");
		println(printHexBinary(newPlainBytes));

		println("Text:");
		String newPlainText = new String(newPlainBytes);
		println(newPlainText);

	}

}
