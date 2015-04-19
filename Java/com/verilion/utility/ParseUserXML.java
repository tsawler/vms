//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-06-14
//Revisions
//------------------------------------------------------------------------------
//$Log: ParseUserXML.java,v $
//Revision 1.2.6.1  2005/08/21 15:37:15  tcs
//Removed unused membres, code cleanup
//
//Revision 1.2  2004/06/14 18:47:33  tcs
//Corrected javadoc
//
//Revision 1.1  2004/06/14 18:30:01  tcs
//Initial entry into cvs
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
public class ParseUserXML {

	public static void parseFile(String myFileName) throws IOException,
			SAXException, Exception {

		try {
			// instantiate Digester and disable XML validation
			Digester digester = new Digester();
			digester.setValidating(false);

			// instantiate parser class
			digester.addObjectCreate("upload", ParseUserXML.class);

			// instantiate Contact class
			digester.addObjectCreate("upload/user", UserObject.class);

			// set type property of Contact instance when 'type' attribute is
			// found
			digester.addSetProperties("upload/user", "type", "type");

			// set different properties of Contact instance using specified
			// methods
			digester.addCallMethod("upload/user/first-name", "setFirst_name", 0);
			digester.addCallMethod("upload/user/last-name", "setLast_name", 0);
			digester.addCallMethod("upload/user/address", "setAddress", 0);
			digester.addCallMethod("upload/user/city", "setCity", 0);
			digester.addCallMethod("upload/user/province", "setProvince", 0);
			digester.addCallMethod("upload/user/postalcode", "setPostalcode", 0);

			// call appropriate method when we see upload/user
			digester.addSetNext("upload/user", "AddIndividual");

			// now that rules and actions are configured, start the parse
			//ParseUserXML abp = (ParseUserXML) digester.parse(new File(myFileName));
			
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * JavaBean class which holds properties on each entry
	 * in the uploaded XML file. Must be static and public.
	 * 
	 * Jun 14, 2004
	 * 
	 * @author tsawler
	 *
	 */
	public static class UserObject {
		private String type;
		private String first_name;
		private String last_name;
		private String address;
		private String city;
		private String province;
		private String postalcode;

		public void setType(String newType) {
			type = newType;
		}
		public String getType() {
			return type;
		}

		public void setFirst_name(String newName) {
			first_name = newName;
		}
		public String getFirst_name() {
			return first_name;
		}
		
		public void setLast_name(String newName) {
			last_name = newName;
		}
		public String getLast_name() {
			return last_name;
		}

		public void setAddress(String newAddress) {
			address = newAddress;
		}
		public String getAddress() {
			return address;
		}

		public void setCity(String newCity) {
			city = newCity;
		}
		public String getCity() {
			return city;
		}

		public void setProvince(String newProvince) {
			province = newProvince;
		}
		public String getProvince() {
			return province;
		}

		public void setPostalcode(String newPostalcode) {
			postalcode = newPostalcode;
		}
		public String getPostalcode() {
			return postalcode;
		}
	}
	
	/**
	 * Prints the xml information to standard output.
	 * 
	 * @param user  - a UserObject object
	 * 
	 * TODO this just writes stuff out to stdout
	 * We'll want to actually do something with this...
	 */
	public void AddIndividual(UserObject user) {
		System.out.println("TYPE: " + user.getType());
		System.out.println("FIRST NAME: " + user.getFirst_name());
		System.out.println("LAST NAME: " + user.getLast_name());
		System.out.println("    ADDRESS:    " + user.getAddress());
		System.out.println("    CITY:       " + user.getCity());
		System.out.println("    PROVINCE:   " + user.getProvince());
		System.out.println("    POSTALCODE: " + user.getPostalcode());
	}
}