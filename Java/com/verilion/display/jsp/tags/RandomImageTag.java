//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-03-03
//Revisions
//------------------------------------------------------------------------------
//$Log: RandomImageTag.java,v $
//Revision 1.1.2.4.4.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.4  2005/03/06 10:43:19  tcs
//Chanaged to derive from superclass
//
//Revision 1.1.2.3  2005/03/05 15:50:31  tcs
//made xhtml 1.1 compliant
//
//Revision 1.1.2.2  2005/03/04 19:30:47  tcs
//Added alt attribute
//
//Revision 1.1.2.1  2005/03/04 18:54:58  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

/**
 * Displays random image from list. All images must be in same directory.
 * Must be at least two images in list, separated by commas, no spaces.
 * 
 * @author tsawler
 *  
 */
public class RandomImageTag extends BaseTag {

   private static final long serialVersionUID = -8464863701596079409L;
   private String path = "";
   private String images;
   private String heights;
   private String widths;
   private String alts;
   private String[] sImages;
   private String[] sHeights;
   private String[] sWidths;
   private String[] sAlts;

   public void setPageContext(PageContext p) {
      pc = p;
   }

   public int doStartTag() throws JspException {
      try {
         StringTokenizer st, hst, wst, ast = null;
         st = new StringTokenizer(images, ",");
         hst = new StringTokenizer(heights, ",");
         wst = new StringTokenizer(widths, ",");
         ast = new StringTokenizer(alts, ",");
         
         int iNumImgs = st.countTokens();
         
         sImages = new String[iNumImgs];
         sHeights = new String[iNumImgs];
         sWidths = new String[iNumImgs];
         sAlts = new String[iNumImgs];
         
         int i = 0;
         
         while ((st.hasMoreElements()) && (i <= (iNumImgs - 1))) {
               sImages[i] = st.nextToken();
               sHeights[i] = hst.nextToken();
               sWidths[i] = wst.nextToken();
               sAlts[i] = ast.nextToken();
               i++;
         }
         Random rand = new java.util.Random();
         int randomNumber = rand.nextInt(iNumImgs);

         String theImage = "<img src=\""
            + path
            + "/"
            + sImages[randomNumber]
            + "\" alt=\""
            + sAlts[randomNumber]
            + "\" height=\""
            + sHeights[randomNumber]
            + "\" width=\""
            + sWidths[randomNumber]
            + "\" style=\"border: 0;\" />";

         pc.getOut().write(theImage);

      } catch (Exception e) {
         e.printStackTrace();
         throw new JspTagException("An IOException occurred.");
      }
      return SKIP_BODY;
   }
   
   /**
    * @return Returns the heights.
    */
   public String getHeights() {
      return heights;
   }
   
   /**
    * @param heights The heights to set.
    */
   public void setHeights(String heights) {
      this.heights = heights;
   }
   
   /**
    * @return Returns the images.
    */
   public String getImages() {
      return images;
   }
   /**
    * @param images The images to set.
    */
   public void setImages(String images) {
      this.images = images;
   }
   
   /**
    * @param widths The widths to set.
    */
   public void setWidths(String widths) {
      this.widths = widths;
   }
   
   /**
    * @return Returns the path.
    */
   public String getPath() {
      return path;
   }
   
   /**
    * @param path The path to set.
    */
   public void setPath(String path) {
      this.path = path;
   }
   
   /**
    * @return Returns the alts.
    */
   public String getAlts() {
      return alts;
   }
   
   /**
    * @param alts The alts to set.
    */
   public void setAlts(String alts) {
      this.alts = alts;
   }
   
   /**
    * @return Returns the widths.
    */
   public String getWidths() {
      return widths;
   }
}