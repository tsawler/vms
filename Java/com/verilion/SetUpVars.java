//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-07-12
//Revisions
//------------------------------------------------------------------------------
//$Log: SetUpVars.java,v $
//Revision 1.1.2.1  2005/07/12 16:55:00  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion;

import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Set up app variables from context entries
 * 
 * @author tsawler
 *  
 */
public class SetUpVars {

   public static String InitVars() throws Exception {

      Context ctx = new InitialContext();
      String hostName = "";

      if (ctx == null) {
         System.out.println("No context!");
         throw new Exception("No context!");
      }

      Context envCtx = (Context) ctx.lookup("java:comp/env");
      hostName = (String) envCtx.lookup("host");

      return hostName;
   }
}