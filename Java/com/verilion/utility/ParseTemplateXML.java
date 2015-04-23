//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-14
//Revisions
//------------------------------------------------------------------------------
//$Log: ParseTemplateXML.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/07/06 16:38:11  tcs
//Corrected javadocs
//
//Revision 1.1  2004/06/15 02:43:16  tcs
//Initial entry. Broken. Does not get all image file names.
//
//------------------------------------------------------------------------------
package com.verilion.utility;

import java.io.IOException;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * Handles parse of uploaded XML documents. Called by UploadXML
 * 
 * @author tsawler
 * 
 */
public class ParseTemplateXML {

	public static void parseFile(String myFileName) throws IOException,
			SAXException, Exception {

		try {
			// instantiate Digester and disable XML validation
			Digester digester = new Digester();
			digester.setValidating(false);

			// instantiate parser class
			digester.addObjectCreate("vcmstemplate", ParseTemplateXML.class);

			// instantiate details class
			digester.addObjectCreate("vcmstemplate/details",
					TemplateObject.class);

			// set different properties of Contact instance using specified
			// methods
			digester.addCallMethod("vcmstemplate/details/template-name",
					"setTemplate_name", 0);
			digester.addCallMethod("vcmstemplate/details/author", "setAuthor",
					0);
			digester.addCallMethod("vcmstemplate/details/author-email",
					"setAuthor_email", 0);
			digester.addCallMethod("vcmstemplate/details/copyright",
					"setCopyright", 0);
			digester.addCallMethod("vcmstemplate/details/author-url",
					"setAuthor_url", 0);
			digester.addCallMethod("vcmstemplate/details/version",
					"setVersion", 0);
			digester.addCallMethod("vcmstemplate/details/html-template",
					"setHtml_filename", 0);
			digester.addCallMethod("vcmstemplate/details/stylesheet", "setCss",
					0);

			// instantiate the images class
			digester.addObjectCreate("vcmstemplate/images", ImagesObject.class);

			// set different properties of images instance using specified
			// methods
			digester.addCallMethod("vcmstemplate/images/image-filename",
					"setImage_name", 0);

			// call appropriate method when we see vcmstemplate/details
			digester.addSetNext("vcmstemplate/details",
					"DisplayTemplateXMLValues");

			// call appropriate method when we see vcmstemplate/images
			digester.addSetNext("vcmstemplate/images",
					"DisplayTemplateImageNames");

			// now that rules and actions are configured, start the parse
			// ParseTemplateXML abp = (ParseTemplateXML) digester.parse(new
			// File(myFileName));

		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * JavaBean class which holds properties on each entry in the uploaded XML
	 * file. Must be static and public.
	 * 
	 * Jun 14, 2004
	 * 
	 * @author tsawler
	 *
	 */
	public static class TemplateObject {
		private String template_name;
		private String author;
		private String copyright;
		private String author_email;
		private String author_url;
		private String version;
		private String files;
		private String css;
		private String html_filename;

		/**
		 * @return Returns the html_filename.
		 */
		public String getHtml_filename() {
			return html_filename;
		}

		/**
		 * @param html_filename
		 *            The html_filename to set.
		 */
		public void setHtml_filename(String html_filename) {
			this.html_filename = html_filename;
		}

		/**
		 * @return Returns the author.
		 */
		public String getAuthor() {
			return author;
		}

		/**
		 * @param author
		 *            The author to set.
		 */
		public void setAuthor(String author) {
			this.author = author;
		}

		/**
		 * @return Returns the author_email.
		 */
		public String getAuthor_email() {
			return author_email;
		}

		/**
		 * @param author_email
		 *            The author_email to set.
		 */
		public void setAuthor_email(String author_email) {
			this.author_email = author_email;
		}

		/**
		 * @return Returns the author_url.
		 */
		public String getAuthor_url() {
			return author_url;
		}

		/**
		 * @param author_url
		 *            The author_url to set.
		 */
		public void setAuthor_url(String author_url) {
			this.author_url = author_url;
		}

		/**
		 * @return Returns the copyright.
		 */
		public String getCopyright() {
			return copyright;
		}

		/**
		 * @param copyright
		 *            The copyright to set.
		 */
		public void setCopyright(String copyright) {
			this.copyright = copyright;
		}

		/**
		 * @return Returns the css.
		 */
		public String getCss() {
			return css;
		}

		/**
		 * @param css
		 *            The css to set.
		 */
		public void setCss(String css) {
			this.css = css;
		}

		/**
		 * @return Returns the files.
		 */
		public String getFiles() {
			return files;
		}

		/**
		 * @param files
		 *            The files to set.
		 */
		public void setFiles(String files) {
			this.files = files;
		}

		/**
		 * @return Returns the template_name.
		 */
		public String getTemplate_name() {
			return template_name;
		}

		/**
		 * @param template_name
		 *            The template_name to set.
		 */
		public void setTemplate_name(String template_name) {
			this.template_name = template_name;
		}

		/**
		 * @return Returns the version.
		 */
		public String getVersion() {
			return version;
		}

		/**
		 * @param version
		 *            The version to set.
		 */
		public void setVersion(String version) {
			this.version = version;
		}
	}

	public static class ImagesObject {

		private String image_name;

		/**
		 * @return Returns the image_name.
		 */
		public String getImage_name() {
			return image_name;
		}

		/**
		 * @param image_name
		 *            The image_name to set.
		 */
		public void setImage_name(String image_name) {
			this.image_name = image_name;
		}
	}

	/**
	 * Prints the xml information to standard output.
	 * 
	 * @param templateObject
	 *            - a templateObject object
	 * 
	 *            TODO this just writes stuff out to stdout We'll want to
	 *            actually do something with this...
	 */
	public void DisplayTemplateXMLValues(TemplateObject templateObject) {
		System.out.println("Template Name: "
				+ templateObject.getTemplate_name());
		System.out.println("Author:        " + templateObject.getAuthor());
		System.out
				.println("Author email:  " + templateObject.getAuthor_email());
		System.out.println("Author url:    " + templateObject.getAuthor_url());
		System.out.println("Copyright:     " + templateObject.getCopyright());
		System.out.println("CSS filename:  " + templateObject.getCss());
		System.out.println("HTML filename: "
				+ templateObject.getHtml_filename());
	}

	public void DisplayTemplateImageNames(ImagesObject imageObject) {
		System.out.println("   Image Name: " + imageObject.getImage_name());
	}
}