// ------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-11-30
//Revisions
//------------------------------------------------------------------------------
//$Log: Store.java,v $
//Revision 1.1.2.3  2005/12/03 03:22:43  tcs
//In progress...
//
//Revision 1.1.2.2  2005/12/01 18:39:34  tcs
//In progress...
//
//Revision 1.1.2.1  2005/11/30 19:45:17  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.object.html.store;

import java.sql.Connection;
import java.text.NumberFormat;

import javax.servlet.http.HttpSession;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Category;
import com.verilion.database.Item;
//import com.verilion.database.SingletonObjects;
import com.verilion.object.Errors;

/**
 * Builds html for various parts of the online shopping system.
 * <P>
 * November 30th, 2005
 * <P>
 * com.verilion.object.html.modules
 * 
 * @author tsawler
 * 
 */
public class Store {

   /**
    * Builds store menu (product menu)
    * 
    * @param conn
    * @param categoryId
    * @param session
    * @return String
    * @throws Exception
    */
   public static String MakeMenuHtml(
         Connection conn,
         int categoryId,
         HttpSession session) throws Exception {

      String theMenu = "";
      XDisconnectedRowSet rs = new XDisconnectedRowSet();
      //int cat_level = SingletonObjects.getInstance().getStore_max_subcategories();
      
      try {
         Category myCategory = new Category();
         myCategory.setConn(conn);

         rs = myCategory.GetSubcategory();
         
         theMenu = "<table class=\"newsbox\" style=\"text-align: left;\">"
               + "<tr style=\"width: 100%;\"><td colspan=\"2\" class=\"moduletitle\">Product Menu</td></tr>";

         while (rs.next()) {
            theMenu += "<tr><td>"
                  + "<a href=\"/store/jpage/1/p/Home/cat/"
                  + rs.getInt("category_id")
                  + "/content.do\">"
                  + rs.getString("category_name")
                  + "</a></td></tr>";
            
               // check to see if our selected category has sub categories
               Category myCat = new Category();
               myCat.setConn(conn);
               myCat.setParent_category_id(rs.getInt("category_id"));
               XDisconnectedRowSet crs = new XDisconnectedRowSet();
               crs = myCat.GetSubcategory();
               
               while (crs.next()) {
                  theMenu += "<tr><td>&nbsp;&nbsp;- "
                        + "<a href=\"/store/jpage/1/p/Home/cat/"
                        + crs.getInt("category_id")
                        + "/content.do\">"
                        + crs.getString("category_name")
                        + "</a></td></tr>";
                  Category myScat = new Category();
                  myScat.setConn(conn);
                  myScat.setParent_category_id(crs.getInt("category_id"));
                  XDisconnectedRowSet crs2 = new XDisconnectedRowSet();
                  crs2 = myScat.GetSubcategory();
                  
                  while (crs2.next()) {
                     theMenu += "<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;- "
                           + "<a href=\"/store/jpage/1/p/Home/cat/"
                           + crs2.getInt("category_id")
                           + "/content.do\">"
                           + crs2.getString("category_name")
                           + "</a></td></tr>";
                  }
                  crs2.close();
                  crs2 = null;
               }
               crs.close();
               crs = null;
         }
         theMenu += "</table>";
         rs.close();
         rs = null;
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("Store.makeMenuHtml:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }

      // return the completed html to calling class
      return theMenu;
   }

   /**
    * Builds the html for product listings (short form)
    * 
    * @param conn
    * @param itemId
    * @param session
    * @return String - formatted html
    * @throws Exception
    */
   public static String GetItemDetail(
         Connection conn,
         int itemId,
         int cat_id,
         HttpSession session) throws Exception {

      String theMenu = "";
      XDisconnectedRowSet rs = new XDisconnectedRowSet();

      try {
         Item myItem = new Item();
         myItem.setConn(conn);
         myItem.setItem_id(itemId);

         rs = myItem.GetItemDetail();

         while (rs.next()) {
            NumberFormat fmt = NumberFormat.getCurrencyInstance();
            theMenu = "<table class=\"collapse\" style=\"width: 90%; text-align: left;\">";
            theMenu += "<tr><td valign=\"top\" style=\"width: 180px;\">"
                  + "<div style=\"text-align: center;\">picture</div>"
                  + "</td><td valign=\"top\">"
                  + "<a href=\"/store/jpage/1/p/Home"
                  + "/cat/"
                  + cat_id
                  + "/itemdetail/"
                  + rs.getInt("item_id")
                  + "/content.do\"><strong>"
                  + rs.getString("item_name")
                  + "</strong></a><br />"
                  + rs.getString("item_long_description")
                  + "<br /><br />"
                  + "Price: "
                  + fmt.format(rs.getFloat("item_price"))
                  + "<br /><br />"
                  + "<a href=\"/store/jpage/1/p/Home/itemdetail/"
                  + rs.getInt("item_id")
                  + "/cat/"
                  + cat_id
                  + "/add/1"
                  + "/content.do\">Add to cart</a>"
                  + "</td>"
                  + "</tr></table>";
         }
         rs.close();
         rs = null;
      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("Store.makeMenuHtml:Exception:" + e.toString());
      } finally {
         if (rs != null) {
            rs.close();
            rs = null;
         }
      }

      // return the completed html to calling class
      return theMenu;
   }
}