// default package

// provides helper methods to print byte[]
import static javax.xml.bind.DatatypeConverter.printHexBinary;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import javax.crypto.Cipher;

/**
 * Public key cryptography using the RSA algorithm.
 */
public class AsymCrypto {

	public static void main(String[] args) throws Exception {

		// check args, get key size and plaintext
		if (args.length != 2) {
			System.err.println("args: keyBits (text)");
			return;
		}
		final int keySize = Integer.parseInt(args[0]);
		System.out.print("Key size (in bits): ");
		System.out.println(keySize);

		final String plainText = args[1];
		final byte[] plainBytes = plainText.getBytes();
		System.out.println("Text:");
		System.out.println(plainText);
		System.out.println("Bytes:");
		System.out.println(printHexBinary(plainBytes));

		// generate an RSA key
		System.out.println("Start generating RSA keys");
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(keySize);
		KeyPair keys = keyGen.generateKeyPair();
		System.out.println("Finish generating RSA keys");

		System.out.println("Private Key:");
		System.out.println(printHexBinary(keys.getPrivate().getEncoded()));
		System.out.println("Public Key:");
		System.out.println(printHexBinary(keys.getPublic().getEncoded()));

		// get an RSA cipher object and print the provider
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		System.out.println(cipher.getProvider().getInfo());

		// encrypt the plaintext using the public key
		System.out.println("Text:");
		System.out.println(plainText);
		System.out.println("Bytes:");
		System.out.println(printHexBinary(plainBytes));

		System.out.println("Ciphering with public key ...");
		cipher.init(Cipher.ENCRYPT_MODE, keys.getPublic());
		byte[] cipherBytes = cipher.doFinal(plainBytes);

		System.out.println("Result:");
		System.out.println(printHexBinary(cipherBytes));

		// decrypt the ciphertext using the private key
		System.out.println("Deciphering with private key ...");
		cipher.init(Cipher.DECRYPT_MODE, keys.getPrivate());
		byte[] newPlainBytes = cipher.doFinal(cipherBytes);
		System.out.println("Result:");
		System.out.println(printHexBinary(newPlainBytes));

		System.out.println("Text:");
		String newPlainText = new String(newPlainBytes);
		System.out.println(newPlainText);

	}

}
