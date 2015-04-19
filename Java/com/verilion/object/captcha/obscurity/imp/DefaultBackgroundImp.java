//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: DefaultBackgroundImp.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.obscurity.imp;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Properties;

import com.verilion.object.captcha.obscurity.BackgroundProducer;
import com.verilion.object.captcha.util.Helper;

/**
 * The default background producer
 * 
 * @author tsawler
 * 
 */
public class DefaultBackgroundImp implements BackgroundProducer {

	private Properties props = null;

	public void setProperties(Properties props) {
		this.props = props;
	}

	public DefaultBackgroundImp() {
	}

	public DefaultBackgroundImp(Properties props) {
		this.props = props;
	}

	public BufferedImage addBackground(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();

		Color from = Helper.getColor(props,
				BackgroundProducer.SIMPLE_CAPCHA_BCKGRND_CLR_FRM,
				Color.lightGray);
		Color to = Helper.getColor(props,
				BackgroundProducer.SIMPLE_CAPCHA_BCKGRND_CLR_T, Color.white);

		// create an opac image
		BufferedImage resultImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D graph = (Graphics2D) resultImage.getGraphics();
		RenderingHints hints = new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);

		hints.add(new RenderingHints(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY));
		hints.add(new RenderingHints(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY));

		hints.add(new RenderingHints(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY));

		graph.setRenderingHints(hints);

		// create the gradient color
		GradientPaint ytow = new GradientPaint(0, 0, from, width, height, to);

		graph.setPaint(ytow);
		
		// draw gradient color
		graph.fill(new Rectangle2D.Double(0, 0, width, height));

		// draw the transparent image over the background
		graph.drawImage(image, 0, 0, null);

		return resultImage;
	}

}
