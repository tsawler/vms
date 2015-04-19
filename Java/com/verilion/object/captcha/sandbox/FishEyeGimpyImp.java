//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: FishEyeGimpyImp.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.sandbox;

import java.awt.image.BufferedImage;
import java.util.Properties;

import com.verilion.object.captcha.obscurity.GimpyEngine;

/**
 * Makes "fish eye" effect for captcha
 * 
 * @author tsawler
 * 
 * 
 */
public class FishEyeGimpyImp implements GimpyEngine {

	@SuppressWarnings("unused")
	private Properties props = null;

	private int imgH;

	private int imgW;

	public BufferedImage getDistortedImage(BufferedImage image) {

		imgH = image.getHeight();
		imgW = image.getWidth();

		// we need this later to do the operations on.
		int pix[] = new int[imgH * imgW];
		int j = 0;

		for (int j1 = 0; j1 < imgW; j1++) {
			for (int k1 = 0; k1 < imgH; k1++) {
				pix[j] = image.getRGB(j1, k1);
				j++;
			}

		}

		double distance = ranInt(imgW / 4, imgW / 3);

		// put the distortion in the (dead) middle
		int wMid = image.getWidth() / 2;
		int hMid = image.getHeight() / 2;

		// again iterate over all pixels..
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {

				int relX = x - wMid;
				int relY = y - hMid;

				double d1 = Math.sqrt(relX * relX + relY * relY);
				if (d1 < distance) {

					int j2 = wMid
							+ (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (x - wMid));
					int k2 = hMid
							+ (int) (((fishEyeFormula(d1 / distance) * distance) / d1) * (double) (y - hMid));
					image.setRGB(x, y, pix[j2 * imgH + k2]);
				}
			}

		}

		return image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.object.captcha.obscurity.GimpyEngine#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props) {
		this.props = props;

	}

	private int ranInt(int i, int j) {
		double d = Math.random();
		return (int) ((double) i + (double) ((j - i) + 1) * d);
	}

	private double fishEyeFormula(double s) {
		// implementation of:
		// g(s) = - (3/4)s3 + (3/2)s2 + (1/4)s, with s from 0 to 1.
		if (s < 0.0D)
			return 0.0D;
		if (s > 1.0D)
			return s;
		else
			return -0.75D * s * s * s + 1.5D * s * s + 0.25D * s;
	}

}
