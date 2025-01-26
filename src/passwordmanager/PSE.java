package passwordmanager;

import javax.imageio.ImageIO;

import exceptions.ImageTooSmallException;
import exceptions.InvalidChecksumException;
import exceptions.UIDNotFoundException;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.awt.image.Raster;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;


// Password Steganographic Encoding
public class PSE {
	
	private static final String tempFilePath = "tmp/data_to_embed.dat";
	private static final String tempFilePath2 = "tmp/serialized_object.dat";
	boolean encrypted;
	
	public void hideData(
			String imagePath, 
			String name, 
			String password, 
			String note, 
			boolean encrypted) 
					throws ImageTooSmallException {
		
		this.encrypted = encrypted;
		
		// store data in DataTransferObject (in this case password, name and note)
		DataTransferObject dto = new DataTransferObject();
		dto.setName(name);
		dto.setPassword(password);
		dto.setNote(note);
		
		Path path = Paths.get(tempFilePath);		
		
		// serialize object (save it in tempFilePath)
		File datafile = new File(tempFilePath);
		try(ObjectOutputStream data = 
				new ObjectOutputStream(
						new BufferedOutputStream(
								new FileOutputStream(datafile)))){
			data.writeObject(dto);
		} catch (NoSuchFileException e) {
		    e.printStackTrace();;
		} catch (IOException e) {
		    e.printStackTrace();;
		}
		
		byte[] fileBytes = null; // serialized file bytes
		byte[] bytesBuffer = null; // bytes to embed in image (UID + fileBytes)
		int bytesToRead = 0;
		// extract all bytes from that file
		try {
			fileBytes = Files.readAllBytes(path);
		} catch (IOException e) {
		    e.printStackTrace();;
		}
		
		// store UID, bytesToRead, sha256 checksum and AES key in json
		int uid = PasswordMetadata.generateUID();
		bytesToRead = fileBytes.length;
		String SHA256checksum = PasswordMetadata.generateSHA256(path);
		PasswordMetadata.newJSONEntry(uid, name, bytesToRead, "key", SHA256checksum);
		
		// store UID and serialized file bytes in single bytes buffer
		bytesBuffer = new byte[4 + bytesToRead];
		bytesBuffer[0] = (byte)((uid >> 24) & 0xFF);
		bytesBuffer[1] = (byte)((uid >> 16) & 0xFF);
		bytesBuffer[2] = (byte)((uid >> 8) & 0xFF);
		bytesBuffer[3] = (byte)((uid) & 0xFF);
		System.arraycopy(fileBytes, 0, bytesBuffer, 4, bytesToRead);
		
		// EMBED DATA ====================================================================
		
		try{
			File img = new File(imagePath); // open image
			BufferedImage image = ImageIO.read(img); // read image to BufferedImage object
			WritableRaster raster = image.getRaster();	// use image as raster
			int width = raster.getWidth(); // image (raster) width
			int height = raster.getHeight(); // image (raster) height
			int numChannels = raster.getNumBands(); // get the number of channels (bands), for example rgb is 3
			int[] pixel = new int[numChannels]; // buffer to store pixel data
			int byteIndex = 0; // byte index in bytes buffer to write
			int bitIndex = 0; // bit index in current byte
			
			// loop through image pixels
			outerLoop:
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					
					if (byteIndex == bytesBuffer.length) break outerLoop;
					// get (x,y) pixel data into pixel array
					raster.getPixel(x, y, pixel);
					
		            // loop through channels ( some images have different number of channels)
		            for (int channel = 0; channel < numChannels; channel++) {
		                
		            	// set last bit of color component (for example red) to 0 and insert next bit from current byte
		                pixel[channel] = (pixel[channel] & 0xFE) | ((bytesBuffer[byteIndex] >> (7 - bitIndex)) & 1);
		                bitIndex++;

		                if (bitIndex == 8) {
		                    bitIndex = 0;
		                    byteIndex++;
		                    // if last byte is reached, set last pixel and break
		                    if (byteIndex == bytesBuffer.length) { 
		                    	raster.setPixel(x, y, pixel);
		                    	break outerLoop;
		                    	};
		                }
		            }
		            
		            raster.setPixel(x, y, pixel);
					
				}
			}
			// store image and delete serialized object file
			ImageIO.write(image, "png", img);
			Files.delete(path);
            
		}catch(IOException e) {
		    e.printStackTrace();;
		}catch(IllegalArgumentException e) {
		    e.printStackTrace();;
		}
		
		
	}
	
	public DataTransferObject loadData(String imagePath) throws InvalidChecksumException, UIDNotFoundException {
		
		this.encrypted = false;
		DataTransferObject temp = null;
				
		Path path = Paths.get(tempFilePath2);
		
		try{
			File img = new File(imagePath); // image to read data from
			BufferedImage image = ImageIO.read(img); // read image to BufferedImage object
			Raster raster = image.getRaster(); // use image as raster
			int width = raster.getWidth(); // image (raster) width
			int height = raster.getHeight(); // image (raster) height
			int numChannels = raster.getNumBands(); // get the number of channels (bands), for example rgb is 3
			int[] pixel = new int[numChannels]; // buffer to store pixel data
			int uid = 0; // unique id to read as header
			int bytesToRead = 0; // bytesToRead to buffer ( retrieved from password metadata)
			int[] bitsInByte = new int[8]; // holds bits of byte
			
			// READ HEADER (UID) ===========================================================================
			
			int bitCount = 0;
			outerLoop:
            for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {

	                raster.getPixel(x, y, pixel);
	                
	                for(int channel = 0; channel < numChannels; channel++) {
	                	uid = (uid << 1) | (pixel[channel] & 1);
	                	bitCount++;
	                	if (bitCount == 32) break outerLoop;
	                }
				}
			}
			
            bytesToRead = PasswordMetadata.getBytesToRead(uid);
            if(bytesToRead == -1) {
            	throw new UIDNotFoundException("No hidden data detected in this image");
            }
            
            // READ DATA ===========================================================================
            
            byte[] bytesBuffer = new byte[4 + bytesToRead]; // read bytes into this buffer (UID + file bytes)
    		int byteIndex = 0; // when iterating bytesBuffer
    		int bitIndex = 0; // bit index in bitsInByte array
    		
    		// loop through image pixels
    		outerLoop:
            for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					// stop reading if buffer is full
					if (byteIndex == bytesBuffer.length) break outerLoop;
					
					raster.getPixel(x, y, pixel);
					
					// write bits one by one looping through each channel of each pixel and reading last bit in color component
					for(int channel = 0; channel < numChannels; channel++) {
						bitsInByte[bitIndex] = (int)(pixel[channel] & 1);
						bitIndex++;
						
						// if byte is full, write it to bytesBuffer
						if (bitIndex == 8) {
							bitIndex = 0;
							bytesBuffer[byteIndex] = bitsArrayToByte(bitsInByte);							
							byteIndex++;
							if (byteIndex == bytesBuffer.length) break outerLoop;
						}
						
					}
				}
			}
    		
    		// extract only file bytes
    		byte[] serializedFileBytes = new byte[bytesToRead];
    		System.arraycopy(bytesBuffer, 4, serializedFileBytes, 0, bytesToRead);
    		
            // recreate serialized object file
            try (FileOutputStream f = new FileOutputStream(tempFilePath2)) {
                f.write(serializedFileBytes);
            }
                        
            // check if original file is corrupted/changed
            String originalHash = PasswordMetadata.getSHA256(uid);
            String toCompareHash = PasswordMetadata.generateSHA256(path);            
            if(originalHash == null || !originalHash.equals(toCompareHash)) {
            	throw new InvalidChecksumException("Original data was modified");
            }
            
            try (ObjectInputStream in = new ObjectInputStream(
            		new BufferedInputStream(
            				new FileInputStream(tempFilePath2)))){
            	temp = (DataTransferObject)in.readObject();
            	
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
			
            
            Files.delete(path);
            
		}catch(IOException e) {
		    e.printStackTrace();
		}catch(IllegalArgumentException e) {
		    e.printStackTrace();
		}
		return temp;
	}

	private byte bitsArrayToByte(int[] bits) {
		return (byte)(bits[7] | (bits[6] << 1) | (bits[5] << 2) | (bits[4] << 3) | (bits[3] << 4) | (bits[2] << 5) | (bits[1] << 6) | (bits[0] << 7));
	}
	

}
