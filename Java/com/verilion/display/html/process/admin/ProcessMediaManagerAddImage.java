//------------------------------------------------------------------------------
//Copyright (c) 2004-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-04
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessMediaManagerAddImage.java,v $
//Revision 1.1.2.2  2005/05/06 11:55:58  tcs
//Complete (but unused)
//
//Revision 1.1.2.1  2005/05/04 14:23:33  tcs
//Initial entry to cvs
//
//------------------------------------------------------------------------------
package com.verilion.display.html.process.admin;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.verilion.display.HTMLTemplateDb;
import com.verilion.display.html.process.ProcessPage;

/**
* Media manager add image form page
* 
* <P>
* May 4th 2005
* <P>
* 
* @author tsawler
*  
*/
public class ProcessMediaManagerAddImage extends ProcessPage {

public HTMLTemplateDb ProcessForm(
       HttpServletRequest request,
       HttpServletResponse response,
       HttpSession session,
       Connection conn,
       HTMLTemplateDb MasterTemplate,
       HashMap hm) throws Exception {

    String path = request.getParameter("path");

    try {
       MasterTemplate.searchReplace("$PATH$", path);
    } catch (Exception e) {

    }
    
    return MasterTemplate;

 }
}