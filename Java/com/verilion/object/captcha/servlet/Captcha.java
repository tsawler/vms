//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: Captcha.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.servlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.verilion.object.captcha.text.DefaultTextCreator;
import com.verilion.object.captcha.text.TextProducer;

/**
 * The captcha servlet
 * 
 * @author tsawler
 */
public class Captcha extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	private final static String SIMPLE_CAPCHA_SESSION_KEY = "SIMPLE_CAPCHA_SESSION_KEY";

	public final static String SIMPLE_CAPCHA_TEXTPRODUCER = "cap.text.producer";

	public final static String SIMPLE_CAPTCHA_TEXTPRODUCER_CHARR = "cap.char.arr";

	public final static String SIMPLE_CAPTCHA_TEXTPRODUCER_CHARRL = "cap.char.arr.l";

	public final static String SIMPLE_CAPTCHA_TEXTPRODUCER_FONTA = "cap.font.arr";

	public final static String SIMPLE_CAPTCHA_TEXTPRODUCER_FONTS = "cap.font.size";

	public final static String SIMPLE_CAPTCHA_TEXTPRODUCER_FONTC = "cap.font.color";

	public final static String SIMPLE_CAPTCHA_PRODUCER = "cap.producer";

	public final static String SIMPLE_CAPTCHA_OBSCURIFICATOR = "cap.obscurificator";

	public final static String SIMPLE_CAPTCHA_BOX = "cap.border";

	public final static String SIMPLE_CAPTCHA_BOX_C = "cap.border.c";

	public final static String SIMPLE_CAPTCHA_BOX_TH = "cap.border.th";

	private Properties props = null;

	private TextProducer textProducer = null;

	private CaptchaProducer captcha = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// this key can be read from any controller to check wether user
		// is a computer or human..
		String capText = textProducer.getText();
		req.getSession().setAttribute(Captcha.SIMPLE_CAPCHA_SESSION_KEY,
				capText);

		CaptchaProducer p = new DefaultCaptchaIml(props);

		p.createImage(resp.getOutputStream(), capText);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Servlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);
		// init method should be thread safe so no
		// worries here...
		props = new Properties();
		Enumeration en = conf.getInitParameterNames();
		while (en.hasMoreElements()) {
			String key = (String) en.nextElement();
			String value = conf.getInitParameter(key);
			props.put(key, value);
		}

		if (props.containsKey(SIMPLE_CAPCHA_TEXTPRODUCER)) {
			String producer = props.getProperty(SIMPLE_CAPCHA_TEXTPRODUCER);
			if (producer != null && !producer.equals("")) {
				try {
					textProducer = (TextProducer) Class.forName(producer)
							.newInstance();
					textProducer.setProperties(props);
				} catch (Exception e) {
				}
			}
			if (textProducer == null) {
				textProducer = new DefaultTextCreator();
				textProducer.setProperties(props);
			}
		}

		if (props.containsKey(SIMPLE_CAPTCHA_PRODUCER)) {
			String producer = props.getProperty(SIMPLE_CAPTCHA_PRODUCER);
			if (producer != null && !producer.equals("")) {
				try {
					captcha = (CaptchaProducer) Class.forName(producer)
							.newInstance();
					captcha.setProperties(props);
				} catch (Exception e) {
				}
			}
			if (captcha == null) {
				captcha = new DefaultCaptchaIml(props);
			}
		}

	}

}
