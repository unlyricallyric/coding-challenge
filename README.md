# Hi, thank you for taking time to look at this project :)

## Pre-requisites
- java 11
- spring boot 2.44
- IDE: would be best to use Intellij

## How to run the project
Simply open pom.xml as project so that the Intellij will be
automatically download and resolve maven dependencies.

Once the above is complete, please locate file "StoreApplication"
under store/src/main/java/com/shopify/store/StoreApplication.
Right-click the file and select "Run StoreApplication".

Once the application is up and running, please open your favorite
browser and enter "localhost:8080"

## Project functionality
- There are 2 pre-defined users
    ###### username: seller
    ###### password: password
    ###### username: buyer
    ###### password: password

- What's on the Navbar?
    1. "Add Image"<br/>
        - User is able to select one/multiple files to upload.
    2. "All Images"
        - This is the Image Marketplace where user is able to see all images from others that are set to public.
    3. "My Images"
        - This is user's image store, user is able to see all his/her own images and make operation such 
          as delete. ** user is only able to delete his/her own images.
          
### Business logic
          
- What's happening when uploading an image?
    1. White listed file extension (to avoid executable files with special extensions)
        - Only certain extensions are allowed such as jpg, jpeg, png etc
    2. Using UUID to replace original file name
        - When uploading file to server, an uuid will be generated to replace original file name in order
      to avoid file name collision.
    3. Using MD5 hashing 
       - to determine if there is already an exact same file on the server, if there is, will be 
    using pointer instead of uploading the same file twice.
    4. Private/Public
       - user is able to set the image to private so that other will not be able to see the image.
         
- What's happening when deleting an image?
    1. Check pointers
        - Checking if the file is referenced to multiple images, if multiple images are pointing to the
    same file, only the reference will be marked as removed. The file will be removed from server only
          if it is pointing to 1 image.
       
