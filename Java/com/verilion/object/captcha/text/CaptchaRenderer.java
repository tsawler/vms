//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: CaptchaRenderer.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.text;

import java.awt.image.BufferedImage;

/**
 * Renderer interface for captcha
 * 
 * @author tsawler
 *
 */
public interface CaptchaRenderer {
	
	public BufferedImage renderCaptcha (String word, int width, int height) ;

}
