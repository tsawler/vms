//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-11-12
//Revisions
//------------------------------------------------------------------------------
//$Log: RSSFeedBoxXMLSpec.java,v $
//Revision 1.1.2.1  2005/04/27 14:59:58  tcs
//In progress...
//
//Revision 1.1  2004/11/10 19:45:19  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

/**
 * Contains concrete class that specifies structure of XML file formats for RDF
 * RSS news feeds etc.
 * <p>
 * November 10, 2004
 * <p>
 * 
 * @author tsawler
 * @see com.verilion.object.html.modules.RSSFeedBoxModule
 * 
 */
public class RSSFeedBoxXMLSpec {

	/**
	 * Corresponds to default RSS Feed xml file format
	 * 
	 * November 10, 2004
	 * 
	 * @author tsawler
	 * 
	 */
	public static class RSSFeedSpec {

		private String sTitle = "";
		private String sLink = "";
		private String sLanguage = "";
		private String sLastBuildDate = "";
		private String sImageURL = "";
		private String sImageTitle = "";
		private String sImageWidth = "";
		private String sItemTitle = "";
		private String sItemLink = "";
		private String sItemDescription = "";
		private String sItemPubDate = "";

		/**
		 * @return Returns the sImageTitle.
		 */
		public String getSImageTitle() {
			return sImageTitle;
		}

		/**
		 * @param imageTitle
		 *            The sImageTitle to set.
		 */
		public void setSImageTitle(String imageTitle) {
			sImageTitle = imageTitle;
		}

		/**
		 * @return Returns the sImageURL.
		 */
		public String getSImageURL() {
			return sImageURL;
		}

		/**
		 * @param imageURL
		 *            The sImageURL to set.
		 */
		public void setSImageURL(String imageURL) {
			sImageURL = imageURL;
		}

		/**
		 * @return Returns the sImageWidth.
		 */
		public String getSImageWidth() {
			return sImageWidth;
		}

		/**
		 * @param imageWidth
		 *            The sImageWidth to set.
		 */
		public void setSImageWidth(String imageWidth) {
			sImageWidth = imageWidth;
		}

		/**
		 * @return Returns the sItemDescription.
		 */
		public String getSItemDescription() {
			return sItemDescription;
		}

		/**
		 * @param itemDescription
		 *            The sItemDescription to set.
		 */
		public void setSItemDescription(String itemDescription) {
			sItemDescription = itemDescription;
		}

		/**
		 * @return Returns the sItemLink.
		 */
		public String getSItemLink() {
			return sItemLink;
		}

		/**
		 * @param itemLink
		 *            The sItemLink to set.
		 */
		public void setSItemLink(String itemLink) {
			sItemLink = itemLink;
		}

		/**
		 * @return Returns the sItemPubDate.
		 */
		public String getSItemPubDate() {
			return sItemPubDate;
		}

		/**
		 * @param itemPubDate
		 *            The sItemPubDate to set.
		 */
		public void setSItemPubDate(String itemPubDate) {
			sItemPubDate = itemPubDate;
		}

		/**
		 * @return Returns the sItemTitle.
		 */
		public String getSItemTitle() {
			return sItemTitle;
		}

		/**
		 * @param itemTitle
		 *            The sItemTitle to set.
		 */
		public void setSItemTitle(String itemTitle) {
			sItemTitle = itemTitle;
		}

		/**
		 * @return Returns the sLanguage.
		 */
		public String getSLanguage() {
			return sLanguage;
		}

		/**
		 * @param language
		 *            The sLanguage to set.
		 */
		public void setSLanguage(String language) {
			sLanguage = language;
		}

		/**
		 * @return Returns the sLastBuildDate.
		 */
		public String getSLastBuildDate() {
			return sLastBuildDate;
		}

		/**
		 * @param lastBuildDate
		 *            The sLastBuildDate to set.
		 */
		public void setSLastBuildDate(String lastBuildDate) {
			sLastBuildDate = lastBuildDate;
		}

		/**
		 * @return Returns the sLink.
		 */
		public String getSLink() {
			return sLink;
		}

		/**
		 * @param link
		 *            The sLink to set.
		 */
		public void setSLink(String link) {
			sLink = link;
		}

		/**
		 * @return Returns the sTitle.
		 */
		public String getSTitle() {
			return sTitle;
		}

		/**
		 * @param title
		 *            The sTitle to set.
		 */
		public void setSTitle(String title) {
			sTitle = title;
		}
	}

}