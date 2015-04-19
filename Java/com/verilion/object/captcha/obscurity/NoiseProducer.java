//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: NoiseProducer.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.obscurity;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * Produce noise for captcha image (interface)
 * @author tsawler
 *
 */
public interface NoiseProducer {
	public abstract void setProperties(Properties props);
	public abstract void makeNoise(
		BufferedImage image,
		float factorOne,
		float factorTwo,
		float factorThree,
		float factorFour);
}