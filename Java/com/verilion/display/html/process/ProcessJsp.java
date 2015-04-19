//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-02-22
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessJsp.java,v $
//Revision 1.1.2.2  2005/02/23 13:21:26  tcs
//set to stub
//
//Revision 1.1.2.1  2005/02/22 19:49:30  tcs
//Initial entry. In progress (nothing done yet!)
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.display.HTMLTemplateDb;

/**
* Handle a request for a JSP
* 
* February 22 2005
* 
* @author tsawler
*  
*/
public class ProcessJsp extends ProcessPage {


 /**
  * @param request
  * @param response
  * @param session
  * @throws Exception
  */
 public HTMLTemplateDb ProcessForm(
       HttpServletRequest request,
       HttpServletResponse response,
       HttpSession session,
       Connection conn,
       HTMLTemplateDb MasterTemplate,
       HashMap hm) throws Exception {

    try {

       
    } catch (Exception e) {

    }
    
    return MasterTemplate;
 }

}