//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: TextProducer.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.text;

import java.util.Properties;

/**
 * Text producer interface
 * 
 * @author tsawler
 *
 */
public interface TextProducer {

	public void setProperties(Properties props);

	public abstract String getText();

}