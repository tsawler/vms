//------------------------------------------------------------------------------
//Copyright (c) 2003-2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-11-16
//Revisions
//------------------------------------------------------------------------------
//$Log: AutoCompleteObject.java,v $
//Revision 1.1.2.1  2005/11/16 19:00:04  tcs
//Initial entry
//
//------------------------------------------------------------------------------

package com.verilion.object.jsremoting;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Object for building lists for ajax
 * @author Trevor
 *
 */
public class AutoCompleteObject implements Serializable {

   private static final long serialVersionUID = -2918566063459118511L;
   private String item;
   private String value;

   /**
    * Constructor for Car.
    */
   public AutoCompleteObject() {
      super();
   }

   /**
    * Constructor for Car.
    */
   public AutoCompleteObject(String item, String value) {
      super();
      this.item = item;
      this.value = value;
   }

   /**
    * @return Returns the item.
    */
   public String getItem() {
      return item;
   }

   /**
    * @param item
    *           The item to set.
    */
   public void setItem(String item) {
      this.item = item;
   }

   /**
    * @return Returns the value.
    */
   public String getValue() {
      return value;
   }

   /**
    * @param value
    *           The value to set.
    */
   public void setValue(String value) {
      this.value = value;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString() {
      return new ToStringBuilder(this).append("item", item).append(
            "value",
            value).toString();
   }

}
