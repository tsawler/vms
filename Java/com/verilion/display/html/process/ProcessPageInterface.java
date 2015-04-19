//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-18
//Revisions
//------------------------------------------------------------------------------
//$Log: ProcessPageInterface.java,v $
//Revision 1.2  2004/10/27 15:37:42  tcs
//Comments and formatting
//
//Revision 1.1  2004/10/27 11:47:46  tcs
//Initial entry
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
 * Interface for pages that require processing
 * 
 * <P>
 * October 18, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
interface ProcessPageInterface {

   /**
    * Decides whether to display a page or process a form.
    * 
    * @param request
    * @param response
    * @param session
    * @param conn
    * @param MasterTemplate
    * @param hm
    * @return HTMLTemplateDb
    * @throws Exception
    */
   public HTMLTemplateDb ChooseAction(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception;

   /**
    * Method to display a page. Extend this class and override this method. Used
    * for page specific processing (i.e. displaying dynamically created forms,
    * tables of data, etc.)
    * 
    * @param request
    * @param response
    * @param session
    * @param conn
    * @param MasterTemplate
    * @param hm
    * @return HTMLTemplateDb
    * @throws Exception
    */
   public HTMLTemplateDb BuildPage(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception;

   /**
    * Method to take posted form information and do something with it.
    * 
    * @param request
    * @param response
    * @param session
    * @param conn
    * @param MasterTemplate
    * @param hm
    * @return HTMLTemplateDb
    * @throws Exception
    */
   public HTMLTemplateDb ProcessForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception;

   /**
    * Repopulate rejected form with original data. The assumption here is that
    * we will be getting the original data back as part of the request, so we
    * can repopulate the form.
    * 
    * @param request
    * @param response
    * @param session
    * @param conn
    * @param MasterTemplate
    * @param hm
    * @return HTMLTemplateDb
    * @throws Exception
    */
   public HTMLTemplateDb ProcessRejectedForm(
         HttpServletRequest request,
         HttpServletResponse response,
         HttpSession session,
         Connection conn,
         HTMLTemplateDb MasterTemplate,
         HashMap hm) throws Exception;

}