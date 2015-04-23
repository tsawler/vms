//------------------------------------------------------------------------------
//Copyright (c) 2003 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-09-17
//Revisions
//------------------------------------------------------------------------------
//$Log: SmtpMail.java,v $
//Revision 1.1.6.1.6.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.1.6.1  2005/08/21 15:37:16  tcs
//Removed unused membres, code cleanup
//
//Revision 1.1  2004/05/23 04:52:52  tcs
//Initial entry into CVS
//
//------------------------------------------------------------------------------
package com.verilion.object.mail;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * Allows creation and sending of smtp mail, including attachments. Does not
 * require any third party jars for talking to smtp server
 * 
 * <P>
 * Dec 8, 2003
 * <P>
 * 
 * @author tsawler
 * 
 */
public class SmtpMail {

	public static final int okay = 1;
	public static final int theError = 0;
	private String DELIMITER;
	private String SEPARATOR;
	private Socket so;
	private BufferedReader recv;
	private PrintWriter send;
	private String from;
	private String to;
	private String domain;
	private Vector x_set;
	private Vector body;
	private Vector attach;
	private String user;
	private String password;

	/**
	 * Constructor
	 */
	public SmtpMail() {
		DELIMITER = "";
		SEPARATOR = "";
		so = null;
		recv = null;
		send = null;
		user = null;
		password = null;
		from = "";
		to = "";
		domain = "";
		x_set = new Vector();
		body = new Vector();
		attach = new Vector();
		DELIMITER = getId();
		SEPARATOR = System.getProperty("file.separator");
	}

	/**
	 * @param s
	 *            String - the attachment
	 */
	@SuppressWarnings("unchecked")
	public void addAttachment(String s) {
		attach.addElement(s);
	}

	/**
	 * @param s
	 *            String - data to add
	 */
	@SuppressWarnings("unchecked")
	public void addData(String s) {
		body.addElement("1");
		body.addElement(s);
	}

	/**
	 * @param s
	 *            - first header
	 * @param s1
	 *            - second header
	 */
	@SuppressWarnings("unchecked")
	public void addHeader(String s, String s1) {
		x_set.addElement(s);
		x_set.addElement(s1);
	}

	/**
	 * @param s
	 *            - the text file to add
	 */
	@SuppressWarnings("unchecked")
	public void addTextFile(String s) {
		body.addElement("2");
		body.addElement(s);
	}

	/**
	 * Removes all attachments
	 */
	public void clearAttachment() {
		attach.removeAllElements();
	}

	/**
	 * Removes all data
	 */
	public void clearData() {
		body.removeAllElements();
	}

	/**
	 * Clears all header information
	 */
	public void clearHeaders() {
		x_set.removeAllElements();
	}

	public int close() {
		int i = 0;
		try {
			i += sendString("QUIT");
			so.close();
		} catch (Exception _ex) {
			return 0;
		}
		return i == 0 ? 1 : 0;
	}

	/**
	 * Encodes message
	 * 
	 * @param abyte0
	 * @param i
	 * @return String - the byte encoded as binary string
	 */
	private String encode(byte abyte0[], int i) {
		StringBuffer stringbuffer = new StringBuffer();
		for (int j = 0; j < i; j += 3) {
			if (j + 2 < i) {
				String s = toBinaryString(abyte0[j])
						+ toBinaryString(abyte0[j + 1])
						+ toBinaryString(abyte0[j + 2]);
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s.substring(0, 6), 2)));
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s.substring(6, 12), 2)));
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s.substring(12, 18), 2)));
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s.substring(18, 24), 2)));
			} else if (j + 1 < i) {
				String s1 = toBinaryString(abyte0[j])
						+ toBinaryString(abyte0[j + 1]) + "00";
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s1.substring(0, 6), 2)));
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s1.substring(6, 12), 2)));
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s1.substring(12, 18), 2)));
				stringbuffer.append('=');
			} else {
				String s2 = toBinaryString(abyte0[j]) + "0000";
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s2.substring(0, 6), 2)));
				stringbuffer
						.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
								.charAt(Integer.parseInt(
										"00" + s2.substring(6, 12), 2)));
				stringbuffer.append('=');
				stringbuffer.append('=');
			}
		}

		return stringbuffer.toString();
	}

	/**
	 * @return String - the message id
	 */
	private String getId() {
		String s = "";
		long l = System.currentTimeMillis();
		Random random = new Random();
		s = String.valueOf(l);
		for (int i = 1; i <= 6; i++) {
			s = s + (int) (1.0D + 6D * random.nextDouble());
		}

		return s;
	}

	/**
	 * Opens file for reading
	 * 
	 * @param s
	 * @param i
	 * @return int - 1 if success, 0 if failure
	 */
	public int open(String s, int i) {
		try {
			so = new Socket(s, i);
			send = new PrintWriter(so.getOutputStream(), true);
			recv = new BufferedReader(
					new InputStreamReader(so.getInputStream()));
			String s1 = recv.readLine();
			char c = s1.charAt(0);
			if ((c == '4') | (c == '5')) {
				return 0;
			}
		} catch (Exception _ex) {
			return 0;
		}
		return 1;
	}

	/**
	 * Removes a header from message
	 * 
	 * @param s
	 */
	public void removeHeader(String s) {
		int i = x_set.indexOf(s);
		if (i >= 0) {
			x_set.removeElementAt(i + 1);
			x_set.removeElementAt(i);
		}
	}

	/**
	 * Sends a binary file
	 * 
	 * @param s
	 */
	private void sendBinaryFile(String s) {
		byte abyte0[] = new byte[48];
		boolean flag = true;
		String s1 = s;
		try {
			DataInputStream datainputstream = new DataInputStream(
					new FileInputStream(s));
			int i = s.lastIndexOf(SEPARATOR);
			if (i >= 0) {
				s1 = s1.substring(i + 1);
			}
			send.println("--" + DELIMITER);
			send.println("Content-Type: APPLICATION/octet-stream; name=\"" + s1
					+ "\";");
			send.println("Content-Transfer-Encoding: BASE64");
			send.println("Content-Description: " + s1);
			send.print("\n");
			while (flag) {
				int j = datainputstream.available();
				int k;
				if (j >= abyte0.length) {
					k = datainputstream.read(abyte0, 0, abyte0.length);
				} else {
					k = j;
					datainputstream.readFully(abyte0, 0, k);
					flag = false;
				}
				send.println(encode(abyte0, k));
			}
			datainputstream.close();
		} catch (Exception _ex) {
		}
	}

	/**
	 * Sends a string
	 * 
	 * @param s
	 * @return int
	 */
	private int sendString(String s) {
		String s1 = "";
		try {
			send.println(s);
			s1 = recv.readLine();
		} catch (Exception _ex) {
			return 0;
		}
		if (s1.length() == 0) {
			return 0;
		}
		char c = s1.charAt(0);
		return c != '4' && c != '5' ? 1 : 0;
	}

	/**
	 * Sends a text file
	 * 
	 * @param s
	 */
	private void sendTextFile(String s) {
		try {
			BufferedReader bufferedreader = new BufferedReader(
					new InputStreamReader(new FileInputStream(s)));
			String s1;
			while ((s1 = bufferedreader.readLine()) != null) {
				send.println(s1);
			}
			bufferedreader.close();
		} catch (Exception _ex) {
		}
	}

	/**
	 * Sets the domain
	 * 
	 * @param s
	 */
	public void setDomain(String s) {
		domain = s;
	}

	/**
	 * Sets the from address
	 * 
	 * @param s
	 */
	@SuppressWarnings("unchecked")
	public void setFrom(String s) {
		from = s;
		x_set.addElement("From");
		x_set.addElement(s);
	}

	/**
	 * Sets password (for smtp auth)
	 * 
	 * @param s
	 */
	public void setPassword(String s) {
		password = s;
	}

	/**
	 * Sets the to address
	 * 
	 * @param s
	 */
	@SuppressWarnings("unchecked")
	public void setTo(String s) {
		to = s;
		x_set.addElement("To");
		x_set.addElement(s);
	}

	/**
	 * Sets user name (for smtp auth)
	 * 
	 * @param s
	 */
	public void setUser(String s) {
		user = s;
	}

	/**
	 * Changes byte to binary string
	 * 
	 * @param byte0
	 * @return String
	 */
	private String toBinaryString(byte byte0) {
		byte byte1 = byte0;
		String s = Integer.toBinaryString(byte1);
		if (byte1 < 0) {
			s = s.substring(s.length() - 8);
		}
		for (; s.length() < 8; s = "0" + s) {
		}
		return s;
	}

	/**
	 * Talks to smtp server
	 * 
	 * @return int
	 */
	public int transmit() {
		if (domain.length() != 0) {
			int i = sendString("HELO " + domain);
			if (i != 1) {
				return 0;
			}
		}
		if (user != null && password != null) {
			int j = sendString("AUTH LOGIN");
			if (j == 1) {
				int k = sendString(encode(user.getBytes(), user.length()));
				if (k != 1) {
					return 0;
				}
				k = sendString(encode(password.getBytes(), password.length()));
				if (k != 1) {
					return 0;
				}
			}
		}
		if (from.length() != 0) {
			int l = sendString("MAIL FROM:" + from);
			if (l != 1) {
				return 0;
			}
		}
		if (to.length() != 0) {
			for (StringTokenizer stringtokenizer = new StringTokenizer(to,
					", \t"); stringtokenizer.hasMoreTokens();) {
				int i1 = sendString("RCPT TO:" + stringtokenizer.nextToken());
				if (i1 != 1) {
					return 0;
				}
			}

		}
		if (sendString("DATA") != 1) {
			return 0;
		}
		for (int j1 = 0; j1 < x_set.size(); j1 += 2) {
			String s = (String) x_set.elementAt(j1);
			send.println(s + ": " + x_set.elementAt(j1 + 1));
		}

		if (x_set.indexOf("Date") < 0) {
			send.println("Date: " + (new Date()).toString());
		}
		if (x_set.indexOf("Mime-Version") < 0) {
			send.println("Mime-Version: 1.0");
		}
		send.println("X-Mailer: Coldjava 1999-2001 ver 1.5");
		if (attach.size() > 0) {
			send.println("Content-Type: MULTIPART/mixed; BOUNDARY=" + DELIMITER);
		}
		try {
			send.print("\n\n");
			int k1 = 0;
			if (attach.size() > 0) {
				send.println("--" + DELIMITER);
				send.println("Content-Type: TEXT/plain;");
				send.print("\n");
			}
			for (; k1 < body.size(); k1 += 2) {
				String s1 = (String) body.elementAt(k1);
				if (s1.compareTo("1") == 0) {
					send.println((String) body.elementAt(k1 + 1));
				} else if (s1.compareTo("2") == 0) {
					sendTextFile((String) body.elementAt(k1 + 1));
				}
			}

			for (int l1 = 0; l1 < attach.size(); l1++) {
				sendBinaryFile((String) attach.elementAt(l1));
			}

			if (attach.size() > 0) {
				send.println("--" + DELIMITER + "--");
			}
			return sendString("\n.\n");
		} catch (Exception _ex) {
			return 0;
		}
	}
}