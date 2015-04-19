//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: BackgroundProducer.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.obscurity;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * Background producer interface for captcha
 * 
 * @author tsawler
 *
 */
public interface BackgroundProducer {
	
	public static final String SIMPLE_CAPCHA_BCKGRND_CLR_FRM = "cap.background.c.from";
	public static final String SIMPLE_CAPCHA_BCKGRND_CLR_T = "cap.background.c.to";
	public static final String SIMPLE_CAPCHA_BCKGRND_CLR = "cap.background.c";
	
	public abstract void setProperties(Properties props);
	public abstract BufferedImage addBackground(BufferedImage image);
}