//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: ChineseTextProducer.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.sandbox;

import com.verilion.object.captcha.text.imp.DefaultTextCreator;

/**
 * Text producer imp that returns chinese characters
 * 
 * @author tsawler
 * 
 */
public class ChineseTextProducer extends DefaultTextCreator {

	private String[] simplfiedC = new String[] { "包括焦点",
			"新�?�消点", "�?分目�?�", "索姓�??電", "�?郵件信",
			"主旨請回", "電�?郵件", "給我所有", "討論�?�明",
			"發表新文", "章此討論", "�?�所有文", "章回主題",
			"樹�?覽�?�" };

	public String getText() {
		return simplfiedC[generator.nextInt(simplfiedC.length)];
	}

}
