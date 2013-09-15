package com.interaudit.util;

import java.io.IOException;
import java.util.regex.*;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;

public class Utils {

    /**
     * Encodes the given String for javascript
     * @param string
     * @return the encoded string
     */
    public static String jsEncode(String string) {
        if(string == null) {
            return null;
        }
        return string
                    .replaceAll("\\\\", "\\\\\\")
                    .replaceAll("\\\r\\\n", "\\\\n")
                    .replaceAll("\\\n", "\\\\n")
                    .replaceAll("\\\b", "\\\\b")
                    .replaceAll("\\\f", "\\\\f")
                    .replaceAll("\\\r", "\\\\r")
                    .replaceAll("\\\t", "\\\\t");
//                    .replaceAll("\\\"", "\\\\\"")
//                    .replaceAll("/", "\\\\/");
    }
    /**
     * 
     * @param is
     * @param os
     * @throws IOException
     */
    public static void copyBytes(InputStream is, OutputStream os) throws IOException {
        
        byte[] b = new byte[1024];
        int length = 0;
        while ( (length = is.read(b)) > -1 ) {
            os.write(b, 0, length);
        }
        os.flush();
    }
    /**
     * 
     * @param fileName
     * @return
     */
    public static String getFileExtension(String fileName) {
        String fileExtension = null;
        int indexOfExtensionDelimiter = fileName.lastIndexOf(".");
        if (indexOfExtensionDelimiter > -1) {
            fileExtension = fileName.substring(indexOfExtensionDelimiter + 1,
                    fileName.length());
        }
        return fileExtension;
    }
    
    public static boolean isValidEmail(String email){
    	

        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");

        //Match the given string with the pattern
        Matcher m = p.matcher(email);

        //check whether match is found 
        boolean matchFound = m.matches();
        /*
        if (matchFound)
        	Logger.("Valid Email Id.");
        else
          System.out.println("Invalid Email Id.");
          */
        
        return matchFound;
    }
    
    /*
     

class regexSample 
{
   public static void main(String args[]) 
   {
      
   }
}

     */
}
