# Steganography-password-manager
This is a simple, user-friendly GUI application in java. It allows users to store passwords and additional data in image files.

This is the main window, as you can see there are 3 main elements: hide password panel, load password panel and image preview

![Screenshot 2025-01-26 134238](https://github.com/user-attachments/assets/39f0b0ad-b2ba-4fd3-9cb6-8a885ee29d1a)

First step is to hide password in image. User should choose path of the image ( browse button) and enter new password or auto-generate secure password and/or password name and additional secure note. The original image will be modified.

![Screenshot 2025-01-26 134414](https://github.com/user-attachments/assets/2fe5a7d7-2efd-43e9-8a30-aaf35e46b7e3)

Second step is to load password from image. User should simply browse the image file again. Decoded password and other data will be immediately visible in corresponding fields.

![Screenshot 2025-01-26 134455](https://github.com/user-attachments/assets/0d50352b-54db-42b6-bb73-6bd1f23f6a29)

# How it works?
Every pixel in the image has some number of color components (channels) like RGBA ( red, green, blue, alpha). If we modify last bit in each of those 1 byte components, changes will not be visible to human eye. So we can basically store up to 12.5% of image size worth of data. The program stores input data in DataTransferObject. Than, object is serialized and UID is created. UID + serialized data transfer object are stored in byte buffer array. All bits from that array are sequentially embeded in LSB of each color component. Some additional password metadata is stored in JSON file, like how many bytes to read after UID is found as well as password name and SHA256 checksum to make sure original file will stay intact.

When we want to load the image everything is done in reverse. We first read 32 bits sequentially from each pixels color components. Now we have UID and we can retrieve password metadata from JSON. The next step is to read all bytes of serialized object. After that, we can compare SHA256 checksum from passwrod metadata with extracted bytes SHA256 checksum. If they are the same, that means no data is lost. Than, if everything is right, password, name and note will be displayed.

# How to use
Import as Maven project in Eclipse IDE and run.
