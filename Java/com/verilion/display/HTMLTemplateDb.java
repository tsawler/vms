package com.verilion.display;

import java.io.IOException;

/**
 * Used by most classes in com.verilion.display to search and replace $tags$ in
 * html templates.
 * <P>
 * Sep 9, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class HTMLTemplateDb {

	private static final long serialVersionUID = -1696471723526281651L;
	byte b[] = new byte[1024];
	int nbytes;
	String recordSet = "";

	String recordTag = "";

	StringBuffer sb = new StringBuffer();
	String ServerPort = "";
	StringBuffer tmp;
	int tmpInsertIndex = 0;

	/**
	 * Reads in HTML template from filesystem.
	 * 
	 * @param filename
	 *            String
	 * @throws IOException
	 */
	public HTMLTemplateDb(String filename) throws IOException {
		sb = new StringBuffer(filename);
	}

	/**
	 * For recordsets in html templates
	 * 
	 * @param locatorF
	 *            String
	 * @param locatorT
	 *            String
	 * @return String
	 */
	public String getRecordSet(String locatorF, String locatorT) {
		int indexF;
		int indexT;
		indexF = sb.toString().indexOf(locatorF);
		indexT = sb.toString().indexOf(locatorT, indexF);
		recordTag = sb.toString().substring(indexF, indexT + locatorT.length());
		recordSet = sb.toString().substring(indexF, indexT + locatorT.length());

		tmp = new StringBuffer(recordSet);

		return recordSet;
	}

	/**
	 * inserts record set into html template
	 */
	public void insertRecordSet() {
		tmpInsertIndex = tmp.toString().length();
		tmp = new StringBuffer(tmp.toString() + recordTag);
	}

	/**
	 * Append supplied string to the stringbuffer
	 * 
	 * @param psAppendString
	 */
	public void appendString(String psAppendString) {
		sb = sb.append(psAppendString);
	}

	public void replaceFullValue(String newValue) {
		sb = new StringBuffer(newValue);
	}

	/**
	 * Sets default replace string, used in html templates to ensure assets from
	 * web site are correctly rendered in html
	 * 
	 * @param replaceString
	 *            String
	 */
	public void replaceDefault(String replaceString) {

		if (getServerPort().length() >= 1) {
			String fullServerPort = ":" + getServerPort();
			int index = replaceString.indexOf(fullServerPort);
			StringBuffer theString = new StringBuffer(replaceString);
			if (index > 0) {
				theString.replace(index, index + fullServerPort.length(), "");
				replaceString = theString.toString();

			}
		}

		searchReplace("ServerAddress", replaceString + "servlet/");
		// used for Forms
		if (getServerPort().length() >= 1) {
			StringBuffer theReplace = new StringBuffer(replaceString);
			int index2 = replaceString.indexOf("http:");
			if (index2 >= 0) {
				theReplace.replace(index2, index2 + 5, "https:");
			}
			replaceString = theReplace.toString();
		}
	}

	/**
	 * replaces recordset
	 */
	public void replaceRecordSet() {
		searchReplace(recordTag, tmp.toString());
		searchReplace(recordTag, "");
	}

	/**
	 * Replaces recordset
	 * 
	 * @param inString
	 *            String
	 */
	public void replaceRecordSet(String inString) {
		searchReplace(recordTag, inString);
	}

	/**
	 * Replaces tag value "from" in html template to value in "to"
	 * 
	 * @param from
	 *            String
	 * @param to
	 *            String
	 */
	public void searchReplace(String from, String to) {
		int index = 0;
		if (to != null) {
			while ((index = sb.toString().indexOf(from, index)) >= 0) {
				sb.replace(index, index + from.length(), to);
				index = index + to.length();
			}
		}
	}

	/**
	 * Replaces tag value "from" in html template to value in "to" for
	 * recordsets
	 * 
	 * @param from
	 *            String
	 * @param to
	 *            String
	 */
	public void searchReplaceRecordSet(String from, String to) {
		int index = 0;
		while ((index = tmp.toString().indexOf(from, index)) >= 0) {
			tmp.replace(index, index + from.length(), to);
			index = index + to.length();
		}
	}

	/**
	 * Sets base value in html template
	 * 
	 * @param url
	 *            String
	 */
	public void setBase(String url) {

		StringBuffer theURL = new StringBuffer(url);

		if (getServerPort().length() >= 1) {
			String fullServerPort = ":" + getServerPort();
			int index = url.indexOf(fullServerPort);
			if (index > 0) {
				theURL.replace(index, index + fullServerPort.length(), "");
				int index2 = url.indexOf("http:");
				if (index2 >= 0) {
					theURL.replace(index2, index2 + 5, "https:");
				}
			}
		}

		url = theURL.toString();
		searchReplace("<html>", "<html>\n<base href=\"" + url + "\">");
	}

	/**
	 * @return Returns the sb.
	 */
	public StringBuffer getSb() {
		return sb;
	}

	/**
	 * @param sb
	 *            The sb to set.
	 */
	public void setSb(StringBuffer sb) {
		this.sb = sb;
	}

	/**
	 * @return Returns the serverPort.
	 */
	public String getServerPort() {
		return ServerPort;
	}

	/**
	 * @param serverPort
	 *            The serverPort to set.
	 */
	public void setServerPort(String serverPort) {
		ServerPort = serverPort;
	}

}