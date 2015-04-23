//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: CaptchaServlet.java,v $
//Revision 1.1.2.1.2.1  2007/03/30 17:20:18  tcs
//Made a readable version
//
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

import com.verilion.object.captcha.util.Helper;

/**
 * CaptchaServlet servlet
 * 
 * @author tsawler
 */
public class CaptchaServlet extends HttpServlet implements Servlet {

	private static final long serialVersionUID = 1L;

	private Properties props = null;

	private CaptchaProducer captchaProducer = null;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// System.out.println("calling catpcha");
		// this key can be read from any controller to check wether user
		// is a computer or human.
		String capText = captchaProducer.createText();
		req.getSession().setAttribute(Constants.SIMPLE_CAPCHA_SESSION_KEY,
				capText);

		// String simpleC =(String)
		// req.getSession().getAttribute(Constants.SIMPLE_CAPCHA_SESSION_KEY);

		// notice we don't store the captext in the producer. This is because
		// the thing is not thread safe and we do use the producer as an
		// instance variable in the servlet.
		captchaProducer.createImage(resp.getOutputStream(), capText);

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

		this.captchaProducer = (CaptchaProducer) Helper.ThingFactory.loadImpl(
				Helper.ThingFactory.CPROD, props);

	}

}
