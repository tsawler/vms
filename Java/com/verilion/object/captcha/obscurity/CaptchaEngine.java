//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: CaptchaEngine.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.obscurity;

import java.awt.image.BufferedImage;

/**
 * Captcha engine interface
 * 
 * @author tsawler
 *
 */
public interface CaptchaEngine  {
	
	public abstract BufferedImage getDistortedImage(BufferedImage image);
	
}
