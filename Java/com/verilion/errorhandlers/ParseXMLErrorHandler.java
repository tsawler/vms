//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-10
//Revisions
//------------------------------------------------------------------------------
//$Log: ParseXMLErrorHandler.java,v $
//Revision 1.1.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/11/10 19:45:19  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.errorhandlers;

import java.io.FileNotFoundException;
import java.text.MessageFormat;

import javax.servlet.http.HttpSession;

import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Error handler for SAX Parse errors and SAX errors
 * 
 * August 30, 2004
 * 
 * @author tsawler
 * 
 */
public class ParseXMLErrorHandler extends DefaultHandler {
   private MessageFormat message = new MessageFormat("({0}: {1}, {2}): {3}");

   // private HttpSession session;

   /**
    * @param session
    */
   public ParseXMLErrorHandler(HttpSession session) {
      super();
      // this.session = session;
   }

   public ParseXMLErrorHandler() {
      super();
   }

   /**
    * Write an error message to exceptions file
    * 
    * @param e
    * @throws FileNotFoundException
    */
   private void print(SAXParseException e) throws FileNotFoundException {

      try {

         // get the error message
         String msg = message.format(new Object[] { e.getSystemId(),
               new Integer(e.getLineNumber()),
               new Integer(e.getColumnNumber()), e.getMessage() });

         // write it to the exceptions file
         System.out.println(msg);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public void warning(SAXParseException e) throws SAXParseException {
      try {
         print(e);
      } catch (Exception e1) {
         e1.printStackTrace();
      }
      throw e;
   }

   public void error(SAXParseException e) throws SAXParseException {
      try {
         print(e);
      } catch (Exception e1) {
         e1.printStackTrace();
      }
      throw e;
   }

   public void fatalError(SAXParseException e) throws SAXParseException {
      try {
         print(e);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
      throw e;
   }
}