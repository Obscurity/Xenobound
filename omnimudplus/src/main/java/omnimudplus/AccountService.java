package omnimudplus;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;


public class AccountService {
	
	public static final LockObject accountLock = new LockObject();
	
	public static final LockObject passLock = new LockObject();
	
	public static final LockObject saltLock = new LockObject();
    
    @SuppressWarnings("unchecked")
	public static void storeAccount(Account account)
        	throws EOFException, IOException, ClassNotFoundException {
        	
    	synchronized (accountLock) {
    	
    		HashMap<String, Account> accounts;
    		
    		try {
    	
	        	FileInputStream fis = new FileInputStream("Accounts");
	        	
	        	ObjectInputStream ois = new ObjectInputStream(fis);
	        	
	    		accounts = (HashMap<String, Account>)ois.readObject();
	    		
	    		ois.close();
	    		
	    		fis.close();
    		
    		} catch (FileNotFoundException e) {
    			
    			accounts = new HashMap<String, Account>();
    			
    		}
    		
    		accounts.put(account.getName(), account);
    		
    		FileOutputStream fos = new FileOutputStream("Accounts", false);
    		
    		ObjectOutputStream oos = new ObjectOutputStream(fos);
    		
    		oos.writeObject(accounts);
    		
    		fos.close();
    		
    		oos.close();
        	
        }
    	
    }
    
    public static Account getAccount(String name)
        	throws EOFException, IOException, ClassNotFoundException {
        	
    	synchronized (accountLock) {
    	
    		Account account = null;
    	
    		try { 
    	
	        	FileInputStream fis = new FileInputStream("Accounts");
	        	
	        	ObjectInputStream ois = new ObjectInputStream(fis);
	        	
	        	@SuppressWarnings("unchecked")
	    		HashMap<String, Account> accounts = (HashMap<String, Account>)ois.readObject();
	        	
	    		for (Account temp : accounts.values()) {
	    			
	    			if (name.toLowerCase().equals(temp.getName().toLowerCase())) {
	    				
	    				account = temp;
	    				break;
	    				
	    			}
	    			
	    		}
	    		
	    		ois.close();
	    		
	    		fis.close();
    		
    		} catch (FileNotFoundException e) {
    			
    			return null;
    			
    		}
    		
    		return account;
        	
        }
    	
    }
        
    public static byte[] recoverEncryptedPassword(Account account)
        	throws EOFException, IOException, ClassNotFoundException {
    	
    	synchronized (passLock) {
        	
        	try {
        	
        		FileInputStream fis = new FileInputStream("Passwords");
        	
        		ObjectInputStream ois = new ObjectInputStream(fis);
        	
            	@SuppressWarnings("unchecked")
        		HashMap<String, byte[]> passwords = (HashMap<String, byte[]>)ois.readObject();
            	
        		byte[] encryptedPassword = passwords.get(account.getName());
        		
        		ois.close();
        		
        		fis.close();
        		
            	return encryptedPassword;
    		
        	} catch (FileNotFoundException e) {
        		
        		return null;
        		
        	}
        	
        }
    	
    }
    
    @SuppressWarnings("unchecked")
	public static void storeEncryptedPassword(Account account, byte[] encryptedPassword)
        	throws EOFException, IOException, ClassNotFoundException {
    	
    	synchronized (passLock) {
        	
    		HashMap<String, byte[]> passwords;
    	
    		try {
    	
	        	FileInputStream fis = new FileInputStream("Passwords");
	        	
	        	ObjectInputStream ois = new ObjectInputStream(fis);

	    		passwords = (HashMap<String, byte[]>)ois.readObject();
	    		
	    		ois.close();
	        	
    		} catch (FileNotFoundException e) {
    			
    			passwords = new HashMap<String, byte[]>();
    		}
    		
    		System.out.println("Got this far: " + passwords);
	    		
	    	passwords.put(account.getName(), encryptedPassword);
	    	
	    	System.out.println(passwords);
	    		
	    	FileOutputStream fos = new FileOutputStream("Passwords", false);
	    		
	    	ObjectOutputStream oos = new ObjectOutputStream(fos);
	    		
	    	oos.writeObject(passwords);
	    		
	    	fos.close();
	    		
	    	oos.close();
        	
        }
    	
    }
    
    @SuppressWarnings("unchecked")
	public static void storeSalt(Account account, byte[] salt)
    	throws EOFException, IOException, ClassNotFoundException {
    	
    	synchronized (saltLock) {
    	
	    	HashMap<String, byte[]> salts;
	    	
	    	try {
	    	
		    	FileInputStream fis = new FileInputStream("PasswordSalts");
		    	
		    	ObjectInputStream ois = new ObjectInputStream(fis);
		    	
		    	if (ois.available() == 0) {
		    		
		    		throw new FileNotFoundException();
		    		
		    	}
		    	
		    	Object test = ois.readObject();
		    	
				salts = (HashMap<String, byte[]>)test;
				
				ois.close();
				
				fis.close();
			
	    	} catch (FileNotFoundException e) {
	    		
	    		System.out.println("Salt is being created at this time.");
	    		
	    		salts = new HashMap<String, byte[]>();
	    		
	    	}
	    	
	    	System.out.println(salts);
			
			salts.put(account.getName(), salt);
			
			System.out.println(salts);
			
			try {
			
				FileOutputStream fos = new FileOutputStream("PasswordSalts", false);
			
				ObjectOutputStream oos = new ObjectOutputStream(fos);
			
				oos.writeObject(salts);
				
				System.out.println("All of this was reached.");
			
				fos.close();
			
				oos.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
    	
    }
    
	public static byte[] recoverSalt(Account account)
    	throws EOFException, IOException, ClassNotFoundException {
		
		synchronized (saltLock) {
			
	    	HashMap<String, byte[]> salts;
	    	
	    	try {
	    	
		    	FileInputStream fis = new FileInputStream("PasswordSalts");
		    	
		    	ObjectInputStream ois = new ObjectInputStream(fis);
		    	
		    	Object test = ois.readObject();
		    	
				salts = (HashMap<String, byte[]>)test;
				
				ois.close();
				
				fis.close();
			
	    	} catch (FileNotFoundException e) {
	    		
	    		return null;
	    		
	    	}
	    	
	    	System.out.println("Salt recovered.");
	    	
	    	System.out.println("Account: " + account.getName());
	    	
	    	System.out.println(salts.get(account.getName()));
	    	
	    	System.out.println(salts);
	    	
			byte[] salt = salts.get(account.getName());
			
	    	return salt;
    	
		}
    	
    }
	
	public static boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) 
			throws NoSuchAlgorithmException, InvalidKeySpecException {
    	// Encrypt the clear-text password using the same salt that was used to
    	// encrypt the original password
    	byte[] encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
    	
    	// Authentication succeeds if encrypted password that the user entered
    	// is equal to the stored hash
    	return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
    }

    public static byte[] getEncryptedPassword(String password, byte[] salt)
    		throws NoSuchAlgorithmException, InvalidKeySpecException {
    	// PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
    	// specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
    	String algorithm = "PBKDF2WithHmacSHA1";
    	// SHA-1 generates 160 bit hashes, so that's what makes sense here
    	int derivedKeyLength = 160;
    	// Pick an iteration count that works for you. The NIST recommends at
    	// least 1,000 iterations:
    	// http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
    	// iOS 4.x reportedly uses 10,000:
    	// http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
    	int iterations = 25000;
		 
    	KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);
		 
    	SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);
		 
    	return f.generateSecret(spec).getEncoded();
    }
    
    public static void removeEncryptedPassword(Account account)
    	throws IOException, ClassNotFoundException {
    	
    	synchronized (passLock) {
    	
	    	try {
	        	
	    		FileInputStream fis = new FileInputStream("Passwords");
	    	
	    		ObjectInputStream ois = new ObjectInputStream(fis);
	    	
	    		HashMap<String, byte[]> passwords = (HashMap<String, byte[]>)ois.readObject();
	        	
	    		passwords.remove(account.getName());
	    		
	    		ois.close();
	    		
	    		fis.close();
	    		
		    	FileOutputStream fos = new FileOutputStream("Passwords", false);
	    		
		    	ObjectOutputStream oos = new ObjectOutputStream(fos);
		    		
		    	oos.writeObject(passwords);
		    		
		    	fos.close();
		    		
		    	oos.close();
			
	    	} catch (FileNotFoundException e) {
	    		
	    		e.printStackTrace();
	    		return;
	    		
	    	}
    	
    	}
    	
    }

    public static byte[] generateSalt() throws NoSuchAlgorithmException {
    	// VERY important to use SecureRandom instead of just Random
    	SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
    	
    	// Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
    	byte[] salt = new byte[8];
    	random.nextBytes(salt);
    	
    	return salt;
	}
    
	public static boolean hasPassword(Account account) {
		
		try {
		
			return recoverEncryptedPassword(account) != null;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
    
}
