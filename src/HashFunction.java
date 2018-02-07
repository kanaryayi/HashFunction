import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyPair;

public class HashFunction {
	private static PublicKey publicKey;
	private static PrivateKey privateKey;
	private static String mode = "MD5";

	public static void main(String[] args) {
		File dataFile = new File("data.txt");
		byte[] data = null;
		try {
			data = new String(Files.readAllBytes(Paths.get(dataFile.getPath()))).getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		keyGenerator();
		byte[] oldSignature=signHashValue(data);
		verifyHashValue(data, oldSignature);
	}

	private static void keyGenerator() {
		/*Generate the key pair*/
		KeyPairGenerator keyGenerator = null;
		
		try {
			keyGenerator = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error while creation of Keys");
			e.printStackTrace();
		}
		keyGenerator.initialize(1024);
		KeyPair pair = keyGenerator.genKeyPair();
		publicKey = pair.getPublic();
		privateKey = pair.getPrivate();
	}
	private static byte[] signHashValue(byte[] dataFile){
		/* Sign the data with private key*/
		byte[] signature= null;
		Signature sign = getSignature();
 		
		try {
			sign.initSign(privateKey);
			sign.update(dataFile);
			signature = sign.sign();
		} catch (InvalidKeyException | SignatureException e) {
			System.out.println("Error while signing");
			e.printStackTrace();
		}
			return signature;
	}
	private static void verifyHashValue(byte[] signedData,byte[] oldSignature){
		/* Verify data with public key */
		Signature signature = getSignature();
		boolean isSignatureCorrect = false;
		try {
			signature.initVerify(publicKey);
			signature.update(signedData);
			isSignatureCorrect = signature.verify(oldSignature);
		} catch (InvalidKeyException | SignatureException e) {
			// TODO Auto-generated catch block
			System.out.println("Error while verification!");
			e.printStackTrace();
		}
		if(isSignatureCorrect){
			System.out.println("Correct !!!");
		}
		else{
			System.out.println("Incorrect !!!");
		}
	}
	private static Signature getSignature(){
		
		/*Get signature mode MD5 or SHA-512*/
		
		Signature signature = null;
		switch (mode) {
		case "MD5":
			try {
				signature = Signature.getInstance(mode+"withRSA");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Error no MD5 algo");
				e.printStackTrace();
			}			
			break;
		case "SHA512":
			try {
				signature = Signature.getInstance(mode+"withRSA");
			} catch (NoSuchAlgorithmException e) {
				System.out.println("Error no SHA512 algo");
				e.printStackTrace();
			}	
			break;
		default:
			System.out.println("Enter Valid Algorithm!");
			break;
		}
		return signature;
	}

}
