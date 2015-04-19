//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-03-13
//Revisions
//------------------------------------------------------------------------------
//$Log: Thumbnail.java,v $
//Revision 1.1.2.1.2.3  2008/09/01 21:11:42  tcs
//*** empty log message ***
//
//Revision 1.1.2.1.2.2  2007/10/30 17:45:35  tcs
//Added method to use external thumbnailer
//
//Revision 1.1.2.1.2.1  2007/08/12 02:09:41  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2007/03/15 17:51:35  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.verilion.database.SingletonObjects;

public class Thumbnail {
	/**
	 * Reads an image in a file and creates a thumbnail in another file.
	 * largestDimension is the largest dimension of the thumbnail, the other
	 * dimension is scaled accordingly. Utilises weighted stepping method to
	 * gradually reduce the image size for better results, i.e. larger steps to
	 * start with then smaller steps to finish with. Note: always writes a JPEG
	 * because GIF is protected or something - so always make your outFilename
	 * end in 'jpg' PNG's with transparency are given white backgrounds
	 */
	public String createThumbnail(String inFilename, String outFilename,
			int largestDimension) {
		try {
			double scale;
			int sizeDifference, originalImageLargestDim;
			if (!inFilename.endsWith(".jpg") && !inFilename.endsWith(".jpeg")
					&& !inFilename.endsWith(".JPG")
					&& !inFilename.endsWith(".JPEG")
					&& !inFilename.endsWith(".GIF")
					&& !inFilename.endsWith(".gif")
					&& !inFilename.endsWith(".PNG")
					&& !inFilename.endsWith(".png")) {
				return "Error: Unsupported image type, please only either JPG, GIF or PNG";
			} else {
				// Image inImage =
				// Toolkit.getDefaultToolkit().getImage(inFilename);
				File theAvatar = new File(inFilename);
				BufferedImage inImage = ImageIO.read(theAvatar);
				if (inImage.getWidth(null) == -1
						|| inImage.getHeight(null) == -1) {
					return "Error loading file: \"" + inFilename + "\"";
				} else {
					// find biggest dimension
					if (inImage.getWidth(null) > inImage.getHeight(null)) {
						scale = (double) largestDimension
								/ (double) inImage.getWidth(null);
						sizeDifference = inImage.getWidth(null)
								- largestDimension;
						originalImageLargestDim = inImage.getWidth(null);
					} else {
						scale = (double) largestDimension
								/ (double) inImage.getHeight(null);
						sizeDifference = inImage.getHeight(null)
								- largestDimension;
						originalImageLargestDim = inImage.getHeight(null);
					}
					// create an image buffer to draw to
					BufferedImage outImage = new BufferedImage(100, 100,
							BufferedImage.TYPE_INT_RGB); // arbitrary init so
															// code
					// compiles
					Graphics2D g2d;
					AffineTransform tx;
					if (scale < 1.0d) // only scale if desired size is smaller
										// than
					// original
					{
						int numSteps = sizeDifference / 100;
						if (numSteps == 0)
							numSteps = 1;
						int stepSize = sizeDifference / numSteps;
						int stepWeight = stepSize / 2;
						int heavierStepSize = stepSize + stepWeight;
						int lighterStepSize = stepSize - stepWeight;
						int currentStepSize, centerStep;
						double scaledW = inImage.getWidth(null);
						double scaledH = inImage.getHeight(null);
						if (numSteps % 2 == 1) // if there's an odd number of
												// steps
							centerStep = (int) Math
									.ceil((double) numSteps / 2d); // find
						// the
						// center
						// step
						else
							centerStep = -1; // set it to -1 so it's ignored
												// later
						Integer intermediateSize = originalImageLargestDim, previousIntermediateSize = originalImageLargestDim;

						for (Integer i = 0; i < numSteps; i++) {
							if (i + 1 != centerStep) // if this isn't the
														// center step
							{
								if (i == numSteps - 1) // if this is the last
														// step
								{
									// fix the stepsize to account for decimal
									// place
									// errors previously
									currentStepSize = previousIntermediateSize
											- largestDimension;
								} else {
									if (numSteps - i > numSteps / 2) // if
																		// we're
																		// in
																		// the
										// first half of the
										// reductions
										currentStepSize = heavierStepSize;
									else
										currentStepSize = lighterStepSize;
								}
							} else // center step, use natural step size
							{
								currentStepSize = stepSize;
							}
							intermediateSize = previousIntermediateSize
									- currentStepSize;
							scale = (double) intermediateSize
									/ (double) previousIntermediateSize;
							scaledW = (int) scaledW * scale;
							scaledH = (int) scaledH * scale;
							outImage = new BufferedImage((int) scaledW,
									(int) scaledH, BufferedImage.TYPE_INT_RGB);
							g2d = outImage.createGraphics();
							g2d.setBackground(Color.WHITE);
							g2d.clearRect(0, 0, outImage.getWidth(), outImage
									.getHeight());
							g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
									RenderingHints.VALUE_RENDER_QUALITY);
							tx = new AffineTransform();
							tx.scale(scale, scale);
							g2d.drawImage(inImage, tx, null);
							g2d.dispose();
							inImage = (BufferedImage) new ImageIcon(outImage)
									.getImage();
							previousIntermediateSize = intermediateSize;
						}
					} else {
						// just copy the original
						outImage = new BufferedImage(inImage.getWidth(null),
								inImage.getHeight(null),
								BufferedImage.TYPE_INT_RGB);
						g2d = outImage.createGraphics();
						g2d.setBackground(Color.WHITE);
						g2d.clearRect(0, 0, outImage.getWidth(), outImage
								.getHeight());
						tx = new AffineTransform();
						tx.setToIdentity(); // use identity matrix so image is
											// copied
						// exactly
						g2d.drawImage(inImage, tx, null);
						g2d.dispose();
					}
					// JPEG-encode the image and write to file.
					OutputStream os = new FileOutputStream(outFilename);
					JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(os);
					encoder.encode(outImage);
					os.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.toString());
			String errorMsg = "";
			errorMsg += "<br>Exception: " + ex.toString();
			errorMsg += "<br>Cause = " + ex.getCause();
			errorMsg += "<br>Stack Trace = ";
			StackTraceElement stackTrace[] = ex.getStackTrace();
			for (int traceLine = 0; traceLine < stackTrace.length; traceLine++) {
				errorMsg += "<br>" + stackTrace[traceLine];
			}
			return errorMsg;
		}
		return ""; // success
	}

	public String createThumbnailUsingJpgtn(String inFilename,
			String outFilename, int largestDimension) {
		try {
			ExecuteExternal myEx = new ExecuteExternal();
			String path = SingletonObjects.getInstance().getSystem_path();
			System.out.println("executing: \n" + path + "../bin/jpgtn -S -s "
					+ largestDimension + " '" + inFilename + "' > '"
					+ outFilename + "'"
					);
			myEx.CmdExec(path + "../bin/jpgtn -S -s "
					+ largestDimension + " '" + inFilename + "' > '"
					+ outFilename + "'");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.toString());
			String errorMsg = "";
			errorMsg += "<br>Exception: " + ex.toString();
			errorMsg += "<br>Cause = " + ex.getCause();
			errorMsg += "<br>Stack Trace = ";
			StackTraceElement stackTrace[] = ex.getStackTrace();
			for (int traceLine = 0; traceLine < stackTrace.length; traceLine++) {
				errorMsg += "<br>" + stackTrace[traceLine];
			}
			return errorMsg;
		}
		return ""; // success
	}
	
	public String createThumbnailUsingJpgtn(String inFilename,
			String outFilename, int largestDimension, String whichDimension) {
		try {
			ExecuteExternal myEx = new ExecuteExternal();
			String path = SingletonObjects.getInstance().getSystem_path();
			System.out.println("executing: \n" + path + "../bin/jpgtn -S -s "
					+ largestDimension 
					+ " -" + whichDimension.toUpperCase()
					+ " '" + inFilename + "' > '"
					+ outFilename + "'"
					);
			myEx.CmdExec(path + "../bin/jpgtn -S -s "
					+ largestDimension + " '" + inFilename + "' > '"
					+ outFilename + "'");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println(ex.toString());
			String errorMsg = "";
			errorMsg += "<br>Exception: " + ex.toString();
			errorMsg += "<br>Cause = " + ex.getCause();
			errorMsg += "<br>Stack Trace = ";
			StackTraceElement stackTrace[] = ex.getStackTrace();
			for (int traceLine = 0; traceLine < stackTrace.length; traceLine++) {
				errorMsg += "<br>" + stackTrace[traceLine];
			}
			return errorMsg;
		}
		return ""; // success
	}
}