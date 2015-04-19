//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-12-01
//Revisions
//------------------------------------------------------------------------------
//$Log: CategoryObject.java,v $
//Revision 1.1.2.1  2005/12/01 18:37:38  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.database.objects;


/**
 * Populates list object using set/get. 
 * 
 * @author Trevor Sawler
 */
public class CategoryObject {

   private String category_name;
   private int category_id = 0;

   
   public CategoryObject() {

   }


   /**
    * @return Returns the category_id.
    */
   public int getCategory_id() {
      return category_id;
   }


   /**
    * @param category_id The category_id to set.
    */
   public void setCategory_id(int category_id) {
      this.category_id = category_id;
   }


   /**
    * @return Returns the category_name.
    */
   public String getCategory_name() {
      return category_name;
   }


   /**
    * @param category_name The category_name to set.
    */
   public void setCategory_name(String category_name) {
      this.category_name = category_name;
   }
   
}
