//------------------------------------------------------------------------------
//Copyright (c) 2004-2007 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2007-01-22
//Revisions
//------------------------------------------------------------------------------
//$Log: WaterRipple.java,v $
//Revision 1.1.2.1  2007/01/22 16:48:22  tcs
//Initial entry into cvs
//
//------------------------------------------------------------------------------
package com.verilion.object.captcha.obscurity.imp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.util.Properties;

import com.jhlabs.image.RippleFilter;
import com.jhlabs.image.TransformFilter;
import com.jhlabs.image.WaterFilter;
import com.verilion.object.captcha.obscurity.GimpyEngine;
import com.verilion.object.captcha.obscurity.NoiseProducer;
import com.verilion.object.captcha.util.Helper;

/**
 * Water ripple imp
 * 
 * @author tsawler
 * 
 */
public class WaterRipple implements GimpyEngine {

	private Properties props = null;

	public WaterRipple(Properties props) {
		this.props = props;
	}

	public WaterRipple() {

	}

	/**
	 * Apply Ripple and Water ImageFilters to distort the image.
	 * 
	 * @param image the image to be distorted
	 * @return the distort image
	 */
	public BufferedImage getDistortedImage(BufferedImage image) {

		BufferedImage imageDistorted = new BufferedImage(image.getWidth(),
				image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D graph = (Graphics2D) imageDistorted.getGraphics();

		// create filter ripple
		RippleFilter filter = new RippleFilter();
		filter.setWaveType(RippleFilter.SINGLEFRAME);
		filter.setXAmplitude(2.6f);
		filter.setYAmplitude(1.7f);
		filter.setXWavelength(15);
		filter.setYWavelength(5);
		filter.setEdgeAction(TransformFilter.RANDOMPIXELORDER);

		// create water filter
		WaterFilter water = new WaterFilter();
		water.setAmplitude(4);
		water.setAntialias(true);
		water.setPhase(15);
		water.setWavelength(70);

		// apply filter water
		FilteredImageSource filtered = new FilteredImageSource(image
				.getSource(), water);
		Image img = Toolkit.getDefaultToolkit().createImage(filtered);

		// apply filter ripple
		filtered = new FilteredImageSource(img.getSource(), filter);
		img = Toolkit.getDefaultToolkit().createImage(filtered);

		graph.drawImage(img, 0, 0, null, null);

		graph.dispose();

		// draw line over the iamge and/or text
		NoiseProducer noise = (NoiseProducer) Helper.ThingFactory.loadImpl(
				Helper.ThingFactory.NOICEIMP, props);
		noise.makeNoise(imageDistorted, .1f, .1f, .25f, .25f);
		noise.makeNoise(imageDistorted, .1f, .25f, .5f, .9f);
		return imageDistorted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.verilion.object.captcha.obscurity.GimpyEngine#setProperties(java.util.Properties)
	 */
	public void setProperties(Properties props) {
		this.props = props;

	}

}
