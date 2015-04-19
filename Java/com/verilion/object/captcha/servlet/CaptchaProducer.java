//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: CaptchaProducer.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import com.verilion.object.captcha.obscurity.BackgroundProducer;
import com.verilion.object.captcha.obscurity.GimpyEngine;
import com.verilion.object.captcha.text.TextProducer;
import com.verilion.object.captcha.text.WordRenederer;

/**
 * Classes implementing this interface will be responsible 
 * for creating the base
 * 
 * @author tsawler
 * 
 */
public interface CaptchaProducer {
	/**
	 * Create an image which have witten a distorted text, text given as
	 * parameter. The result image is put on the output stream
	 * 
	 * @param stream
	 *            the OutputStrea where the image is written
	 * @param text
	 *            the distorted characters written on imagage
	 * @throws IOException
	 *             if an error occurs during the image written on output stream.
	 */
	public abstract void createImage(OutputStream stream, String text)
			throws IOException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.object.captcha.servlet.CaptchaProducer#setBackGroundImageProducer(com.verilion.object.captcha.obscurity.BackgroundProducer)
	 */
	public abstract void setBackGroundImageProducer(
			BackgroundProducer background);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.object.captcha.servlet.CaptchaProducer#setObscurificator()
	 */
	public abstract void setObscurificator(GimpyEngine engine);

	/**
	 * @param properties
	 */
	public abstract void setProperties(Properties properties);

	public abstract void setTextProducer(TextProducer textP);

	public abstract String createText();

	public abstract void setWordRenderer(WordRenederer renederer);
}