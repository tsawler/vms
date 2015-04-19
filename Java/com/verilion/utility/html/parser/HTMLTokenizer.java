//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-26
//Revisions
//------------------------------------------------------------------------------
//$Log: HTMLTokenizer.java,v $
//Revision 1.3.6.1.6.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.3.6.1  2005/08/21 10:45:27  tcs
//Cleaned up unused members
//
//Revision 1.3  2004/10/26 12:32:47  tcs
//Improved javadocs
//
//Revision 1.2  2004/10/26 12:29:43  tcs
//Added constructor to parse a passed string instead of file
//
//Revision 1.1  2004/10/26 12:23:17  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.utility.html.parser;

import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Vector;

/**
 * This class tokenizes a stream of HTML tags and blocks of text. After the
 * stream has been tokenized an Enumeration of tokens can be accessed.
 * 
 * @see TagToken
 * @see TextToken
 * @see java.util.Enumeration
 */
public class HTMLTokenizer {

   private final int BUF_LEN = 256; // Maximum length of read buffer.
   private Vector tokens; // Store for finished tokens.

   private char separator; // Stores the current separator character.
   private int start; // Index of the start of the next token.

   /**
    * Constructs a new HTMLTokenizer using the given filename to create the
    * input stream.
    * 
    * @param file the name of the file to open.
    */
   public HTMLTokenizer(String file) {

      InputStream is; // The new input stream.

      // Initialise the variables.
      tokens = new Vector();

      try {
         // Open an input stream using the file name.
         is = new FileInputStream(file);

         // Parse the input stream.
         parseInputStream(is);
      } catch (IOException ioe) {
         return;
      }
   }
   
   /**
    * Constructs a new HTMLTokenizer using a supplied string to create the
    * input stream.
    * 
    * @param psStringToParse the string to parse
    * @param bFile
    */
   public HTMLTokenizer(String psStringToParse, boolean bFile) {

      InputStream is; // The new input stream.

      // Initialise the variables.
      tokens = new Vector();

      try {
         // Open an input stream using the file name.
         is = new ByteArrayInputStream(psStringToParse.getBytes());

         // Parse the input stream.
         parseInputStream(is);
      } catch (IOException ioe) {
         return;
      }
   }
   

   /**
    * Returns an enumeration of the tokens which have been created by the
    * HTMLTokenizer.
    */
   public Enumeration getTokens() {
      return tokens.elements();
   }

   /**
    * Returns the vector in which the tokens are stored.
    */
   public Vector getTokenVector() {
      return tokens;
   }

   /**
    * Parses the input stream given into tokens.
    * 
    * @param is the input stream to parse.
    */
   private void parseInputStream(InputStream is) throws IOException {

      byte[] readbuf; // Refers to the read buffer.
      char[] charbuf; // Read buffer converted to characters.
      StringBuffer unused; // Characters still to be processed.
      int length; // Length of last chunk of read data.
      int i; // Loop variable.

      // Create new buffers.
      readbuf = new byte[BUF_LEN];
      charbuf = new char[BUF_LEN];
      unused = null;

      // Set the separator initially.
      separator = '<';

      // Loop round while the end-of-file has not been reached.
      while (true) {

         // Read in the first chunk of data.
         length = is.read(readbuf);

         // Check for end-of-file.
         if (length < 0)
            break;

         // Convert the byte array to characters.
         for (i = 0; i < length; i++)
            charbuf[i] = (char) readbuf[i];

         // Process it.
         unused = processBuffer(charbuf, unused, length);
      }
   }

   /**
    * Processes the given character array. The token buffer will be updated to
    * start with the contents of the given StringBuffer. Any leftover parts of
    * the buffer that have not been processed are returned in a StringBuffer.
    * The next call to processBuffer will start where the last one left off by
    * putting the returned StringBuffer in the argument list of the next call.
    * 
    * @param charbuf the character array to be processed.
    * @param old the leftovers from the last call.
    * @param len the maximum length of the array to process.
    */
   @SuppressWarnings("unchecked")
private StringBuffer processBuffer(char[] charbuf, StringBuffer old, int len) {

      StringBuffer data; // Stores current token's data.
      int idx; // The index of the next separator.

      // Get a buffer for the current token.
      if (old != null)
         data = old;
      else
         data = new StringBuffer(80);

      // Make sure the start index is initialized properly.
      start = 0;
      idx = -1;

      while (true) {

         // Set the new start index.
         start = idx + 1;

         // Get the index of the separator.
         idx = indexOf(separator, charbuf, start, len);

         // Check if the separator appears or not.
         if (idx < 0) {

            // Update the data buffer.
            if (len - start > 0)
               data.append(charbuf, start, len - start);

            // If there is data in the buffer, return it.
            if (data.length() > 0)
               return data;
            else
               return null;
         }

         // Append the start of the read buffer onto the
         // data buffer.
         data.append(charbuf, start, idx - start);

         // Check if we should create text or a tag.
         if (separator == '<') {

            // Check if there is any content to store.
            if (data.length() > 0) {

               // Create a new TextToken.
               TextToken tt = new TextToken();

               // Put the data into the token.
               tt.setText(data);

               // Store the new TextToken.
               tokens.addElement(tt);
            }
         } else {

            // Convert the data to a string.
            String s = data.toString();

            // Create a new TagToken with it.
            TagToken tt = new TagToken(s);

            // Store the new TagToken.
            tokens.addElement(tt);
         }

         // Create a new, empty data buffer.
         data = new StringBuffer(BUF_LEN);

         // Swap the separator character.
         if (separator == '<')
            separator = '>';
         else
            separator = '<';
      }
   }

   /**
    * Returns the index of the given character in the given byte array or -1 if
    * the character does not appear there.
    * 
    * @param c the test character.
    * @param array the byte array to search.
    * @param start the first index to search.
    * @param len the maximum length to search.
    */
   private int indexOf(char c, char[] array, int start, int len) {
      for (int i = start; i < len; i++)
         if (array[i] == c)
            return i;

      return -1;
   }
}