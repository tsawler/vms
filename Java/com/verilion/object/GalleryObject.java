//------------------------------------------------------------------------------
//Copyright (c) 2009 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2009-04-16
//Revisions
//------------------------------------------------------------------------------
//$Log: GalleryObject.java,v $
//Revision 1.1.2.1  2009/07/22 16:29:35  tcs
//*** empty log message ***
//
//------------------------------------------------------------------------------
package com.verilion.object;


/**
 * Populates list object using set/get for Gallery
 * 
 * 
 */
public class GalleryObject {

   private String Gallery = "";

   public GalleryObject() {

   }

   /**
    * @return Returns the row.
    */
   public String getGallery() {
      return Gallery;
   }

   /**
    * @param theRow
    *           The theRow to set.
    */
   public void setGallery(String Gallery) {
      this.Gallery = Gallery;
   }

}
