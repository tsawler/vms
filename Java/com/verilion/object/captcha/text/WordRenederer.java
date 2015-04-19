//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: WordRenederer.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.text;

import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * Word renderer interface
 * 
 * @author Administrator
 * 
 */
public interface WordRenederer {
	/** 
	 * Render a word to a BufferedImage.
	 * 
	 * @param word The word to be rendered.
	 * @param width The width of the image to be created.
	 * @param height The heigth of the image to be created.
	 * @return The BufferedImage created from the word,
	 */
	public abstract BufferedImage renderWord(
		String word,
		int width,
		int height);
	/**
	 * @param properties
	 */
	public abstract void setProperties(Properties properties);
}