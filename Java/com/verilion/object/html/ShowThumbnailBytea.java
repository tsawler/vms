//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: ShowThumbnailBytea.java,v $
//Revision 1.1.2.1.4.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1.2.1  2004/12/14 14:45:56  tcs
//Cleaned up code
//
//Revision 1.1  2004/05/23 04:52:49  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.html;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.ReplicateScaleFilter;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * Generates a thumbnail from an image, and sends it to the user's browser
 * 
 * Apr 26, 2004
 * 
 * @author tsawler
 * 
 */
public class ShowThumbnailBytea extends HttpServlet {

	private static final long serialVersionUID = 3618141156060772662L;

	public final void init(ServletConfig config) throws ServletException {
		// No initialization necessary
	}

	public final void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// No difference to us if it's a get or a post.
		this.doPost(request, response);
	}

	public final void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			int targetWidth = 0;
			int targetHeight = 0;

			// Get a path to the image to resize.
			// ImageIcon is a kluge to make sure the image is fully
			// loaded before we proceed.
			Image sourceImage = new ImageIcon(Toolkit.getDefaultToolkit()
					.getImage(request.getPathTranslated())).getImage();

			// Calculate the target width and height
			float scale = Float.parseFloat(request.getParameter("scale")) / 100;
			targetWidth = (int) (sourceImage.getWidth(null) * scale);
			targetHeight = (int) (sourceImage.getHeight(null) * scale);

			BufferedImage resizedImage = this.scaleImage(sourceImage,
					targetWidth, targetHeight);

			// Output the finished image straight to the response as a JPEG!
			response.setContentType("image/jpeg");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response
					.getOutputStream());
			encoder.encode(resizedImage);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	/**
	 * Takes an image and scales it to supplied width/height
	 * 
	 * @param sourceImage
	 * @param width
	 * @param height
	 * @return BufferedImage
	 */
	private BufferedImage scaleImage(Image sourceImage, int width, int height) {
		ImageFilter filter = new ReplicateScaleFilter(width, height);
		ImageProducer producer = new FilteredImageSource(
				sourceImage.getSource(), filter);
		Image resizedImage = Toolkit.getDefaultToolkit().createImage(producer);

		return this.toBufferedImage(resizedImage);
	}

	/**
	 * Converts a supplied image into a buffered image
	 * 
	 * @param image
	 * @return BufferedImage
	 */
	private BufferedImage toBufferedImage(Image image) {
		image = new ImageIcon(image).getImage();
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
				image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics g = bufferedImage.createGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, image.getWidth(null), image.getHeight(null));
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bufferedImage;
	}
}