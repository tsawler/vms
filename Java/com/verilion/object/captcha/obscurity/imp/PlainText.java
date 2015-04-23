//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: PlainText.java,v $
//Revision 1.1.2.1  2007/03/30 17:20:37  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.obscurity.imp;

import java.awt.image.BufferedImage;
import java.util.Properties;

import com.verilion.object.captcha.obscurity.GimpyEngine;

/**
 * Plain text impl
 * 
 * @author tsawler
 * 
 */
public class PlainText implements GimpyEngine {

	@SuppressWarnings("unused")
	private Properties props = null;

	public PlainText(Properties props) {
		this.props = props;
	}

	public PlainText() {

	}

	/**
	 * Display undistorted text
	 * 
	 * @param image
	 *            the image to be display
	 * @return the distort image
	 */
	public BufferedImage getDistortedImage(BufferedImage image) {
		return image;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verilion.object.captcha.obscurity.GimpyEngine#setProperties(java.
	 * util.Properties)
	 */
	public void setProperties(Properties props) {
		this.props = props;

	}

}
