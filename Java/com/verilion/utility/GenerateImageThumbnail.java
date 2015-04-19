//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-05-02
//Revisions
//------------------------------------------------------------------------------
//$Log: GenerateImageThumbnail.java,v $
//Revision 1.1.2.1  2005/05/04 13:57:12  tcs
//In progress
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.verilion.database.SingletonObjects;

public class GenerateImageThumbnail {

   public File file;
   public File newDirFile;
   public int h = 0;
   public int w = 0;

   public GenerateImageThumbnail(File file, int h, int w) {

      this.file = file;
      this.h = h;
      this.w = w;

   }

   public File process() {

      File fileOut = null;
      
      try {

         Image image = Toolkit.getDefaultToolkit().getImage(file.getAbsolutePath().toString());
         MediaTracker mediaTracker = new MediaTracker(new Container());
         mediaTracker.addImage(image, 0);
         mediaTracker.waitForID(0);
         // determine thumbnail size from WIDTH and HEIGHT
         int thumbWidth = w;
         int thumbHeight = h;
         double thumbRatio = (double)thumbWidth / (double)thumbHeight;
         int imageWidth = image.getWidth(null);
         int imageHeight = image.getHeight(null);
         double imageRatio = (double)imageWidth / (double)imageHeight;
         if (thumbRatio < imageRatio) {
           thumbHeight = (int)(thumbWidth / imageRatio);
         } else {
           thumbWidth = (int)(thumbHeight * imageRatio);
         }
         // draw original image to thumbnail image object and
         // scale it to the new size on-the-fly
         BufferedImage thumbImage = new BufferedImage(thumbWidth, 
           thumbHeight, BufferedImage.TYPE_INT_RGB);
         Graphics2D graphics2D = thumbImage.createGraphics();
         graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
           RenderingHints.VALUE_INTERPOLATION_BILINEAR);
         graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
         // save thumbnail image to OUTFILE
         BufferedOutputStream out = new BufferedOutputStream(new
           FileOutputStream(new File (SingletonObjects.getInstance().getUpload_path() + "/UserImages/tn_" + file.getName())));
         System.out.println("writing " + SingletonObjects.getInstance().getUpload_path() + "/UserImages/tn_" + file.getName());
         JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
         JPEGEncodeParam param = encoder.
           getDefaultJPEGEncodeParam(thumbImage);
         int quality = 80;
         quality = Math.max(0, Math.min(quality, 100));
         param.setQuality((float)quality / 100.0f, false);
         encoder.setJPEGEncodeParam(param);
         encoder.encode(thumbImage);
         fileOut = new File (SingletonObjects.getInstance().getUpload_path() + "/UserImages/tn_" + file.getName());
         out.close(); 
         
      } catch (Exception e) {

         e.printStackTrace();

      }

      return fileOut;

   }

}