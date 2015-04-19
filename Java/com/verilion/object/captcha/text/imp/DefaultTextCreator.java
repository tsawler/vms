//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: DefaultTextCreator.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.text.imp;

import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import com.verilion.object.captcha.servlet.Constants;
import com.verilion.object.captcha.text.TextProducer;

/**
 * Default TextProducer implementation. Takes character array and produces a
 * random String with the getText() method.
 * 
 * @author tsawler
 */
public class DefaultTextCreator implements TextProducer {

	public Random generator = new Random();

	@SuppressWarnings("unused")
	private Properties properties;

	private int capLength = 5;

	private char[] captchars = new char[] { 'a', 'b', 'c', 'd', 'e', '2', '3',
			'4', '5', '6', '7', '8', 'g', 'f', 'y', 'n', 'm', 'n', 'p', 'w',
			'x' };

	public DefaultTextCreator() {

	}

	public DefaultTextCreator(Properties properties) {
		super();
		this.setProperties(properties);
	}

	public void setCharArray(char[] chars) {
		this.captchars = chars;
	}

	public void setProperties(Properties props) {
		this.properties = props;
		if (props != null) {
			String charString = props
					.getProperty(Constants.SIMPLE_CAPTCHA_TEXTPRODUCER_CHARR);
			if (charString != null && !charString.equals("")) {

				StringTokenizer token = new StringTokenizer(charString, ",");
				this.captchars = new char[token.countTokens()];
				int cnt = 0;
				while (token.hasMoreTokens()) {
					captchars[cnt] = ((String) token.nextElement())
							.toCharArray()[0];
					cnt++;
				}

			}

			String l = props
					.getProperty(Constants.SIMPLE_CAPTCHA_TEXTPRODUCER_CHARRL);
			if (l != null && !l.equals("")) {
				try {
					capLength = Integer.parseInt(l);
				} catch (Exception e) {
					// TODO: handle exception
				}
				if (capLength < 2)
					capLength = 5;
			}
		}

	}

	public String getText() {
		int car = captchars.length - 1;

		String capText = "";
		for (int i = 0; i < capLength; i++) {
			capText += captchars[generator.nextInt(car) + 1];
		}

		return capText;

	}

}
