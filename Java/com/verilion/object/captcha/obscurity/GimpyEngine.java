//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: GimpyEngine.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.obscurity;

import java.util.Properties;

/**
 * Gimpy engine interface
 * 
 * @author tsawler
 * 
 */
public interface GimpyEngine extends CaptchaEngine {
	
	public void setProperties(Properties props);
	
	
}