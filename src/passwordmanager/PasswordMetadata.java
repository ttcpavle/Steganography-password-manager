package passwordmanager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Random;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordMetadata {
	
	public static final String PASSWORD_METADATA_PATH = "password_metadata.json";

	// create new table entry: (not all functionalities are implemented)
	/*
	 * Here is the example of password metadata entry:
	 * |   UID   |     name    |   bytesToRead   |      AESkey      |      hash       |
	 * -------------------------------------------------------------------------------------------
	 * |   324   | my_password |        425      |  a1cb28f0ea1334f | f2cc2860ea1334f |
	 * 
	 * UID - unique identifier stored in hidden file
	 * name - name of the password ( chosen by user)
	 * bytesToRead - calculated size of serialized file to hide in image
	 * AESkey - key used to encrypt serialized file if AES was selected preference (NOT IMPLEMENTED YET)
	 * hash - used to check integrity of the file
	 * 
	 * */
	public static void newJSONEntry(int UID, String name, int bytesToRead, String AESkey, String hash) {
				
        try {
            JSONArray arr;
            Path path = Paths.get(PASSWORD_METADATA_PATH);
            if (Files.exists(path)) {
                String content = Files.readString(path);
                arr = new JSONArray(content);
            } else {
                arr = new JSONArray();
            }

            JSONObject entry = new JSONObject();
            entry.put("UID", UID);
            entry.put("name", name);
            entry.put("bytesToRead", bytesToRead);
            entry.put("AESkey", AESkey);
            entry.put("hash", hash);

            arr.put(entry);

            Files.writeString(path, arr.toString(4));
        } catch (IOException e) {
            e.printStackTrace();;
        }
	    
		
	}
	
	public static int generateUID() {
		Random random = new Random();
		return random.nextInt();
	}
	
	
	public static String generateSHA256(Path tempFilePath){
		try {
		byte[] data = Files.readAllBytes(tempFilePath);
		byte[] hash = MessageDigest.getInstance("SHA-256").digest(data);
		String checksum = new BigInteger(1, hash).toString(16);
		return checksum;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getSHA256(int UID) {
	    Path filePath = Paths.get(PASSWORD_METADATA_PATH);
	    
	    try {
	        String content = Files.readString(filePath);
	        JSONArray jsonArray = new JSONArray(content);
	        
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject obj = jsonArray.getJSONObject(i);
	            if (obj.getInt("UID") == UID) {
	                return obj.getString("hash");
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
	
	public static int getBytesToRead(int UID) {
	    Path filePath = Paths.get(PASSWORD_METADATA_PATH);
	    
	    try {
	        String content = Files.readString(filePath);
	        JSONArray jsonArray = new JSONArray(content);
	        
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject obj = jsonArray.getJSONObject(i);
	            if (obj.getInt("UID") == UID) {
	                return obj.getInt("bytesToRead");
	            }
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {
	        e.printStackTrace();;
	    }
	    
	    return -1;
	}
	
	public static String getAESkey(int UID) {
	    Path filePath = Paths.get(PASSWORD_METADATA_PATH);
	    
	    try {
	        String content = Files.readString(filePath);
	        JSONArray jsonArray = new JSONArray(content);
	        
	        for (int i = 0; i < jsonArray.length(); i++) {
	            JSONObject obj = jsonArray.getJSONObject(i);
	            if (obj.getInt("UID") == UID) {
	                return obj.getString("AESkey");
	            }
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {
	        e.printStackTrace();
	    }
	    
	    return null;
	}
	
}
