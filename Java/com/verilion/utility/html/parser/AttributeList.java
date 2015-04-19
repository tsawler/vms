//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-10-26
//Revisions
//------------------------------------------------------------------------------
//$Log: AttributeList.java,v $
//Revision 1.1.12.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.1  2004/10/26 12:23:17  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.utility.html.parser;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Represents the attribute list of an tag.
 * 
 * @see TagToken
 */
public class AttributeList {

   private Hashtable list; // Stores the attributes.

   public AttributeList() {

      // Create a new hashtable to store the attributes.
      list = new Hashtable();
   }

   /**
    * returns the number of attributes currently defined.
    */
   public int size() {
      return list.size();
   }

   /**
    * returns the value of the attribute with the specified name.
    * 
    * @param name
    */
   public String get(String name) {

      // Check the name of the attribute is not null.
      if (name == null)
         return null;

      // Check that the attribute list is there.
      if (list == null)
         return null;

      // Return the value associated with the attribute name.
      return (String) list.get(name.toLowerCase());
   }

   /**
    * Sets the attribute with the specified name to the specified value. If the
    * attribute already has a value it will be overwritten.
    * 
    * @param name
    * @param value
    */
   @SuppressWarnings("unchecked")
public void set(String name, String value) {

      // Check that the name is not null.
      if (name == null)
         return;

      // Replace a null value with an empty string.
      if (value == null)
         value = "";

      // Return if the list of attributes is not defined.
      if (list == null)
         return;

      // Otherwise, add the attribute and value to the list.
      list.put(name.toLowerCase(), value);
   }

   /**
    * Returns true if the specified attribute name exists within the list.
    * 
    * @param name of the attribute to check.
    */
   public boolean exists(String name) {

      // Return false if the name is not null.
      if (name == null)
         return false;

      // Return false of the list is not defined.
      if (list == null)
         return false;

      // Check the list to see if the attribute exists.
      return list.containsKey(name.toLowerCase());
   }

   /**
    * Removes the specified attribute from the list.
    * 
    * @param name
    */
   public void unset(String name) {

      // Return if the attribute name is null.
      if (name == null)
         return;

      // Return if the attribute list is not defined.
      if (list == null)
         return;

      // Otherwise, remove the attribute from the list.
      list.remove(name.toLowerCase());
   }

   /**
    * Returns an enumeration of defined attributes.
    */
   public Enumeration names() {

      // Check that the attribute table has been defined.
      if (list == null)
         return null;

      // Return an enumeration of all of the defined attributes.
      return list.keys();
   }

   /**
    * Returns an attribute with all double quote characters escaped with a
    * backslash.
    * 
    * @param name
    */
   public String getQuoted(String name) {

      String value; // Stores the value of the attribute.
      char[] array; // Character array from 'value'.
      StringBuffer quoted; // Stores the quoted version of the value.
      int i; // Loop variable.

      // Check the name of the attribute is not null.
      if (name == null)
         return null;

      // Check that the attribute list is there.
      if (list == null)
         return null;

      // Get the value of the attribute.
      value = (String) list.get(name.toLowerCase());

      // Return nothing if there is no such attribute.
      if (value == null)
         return null;

      // Return an empty string if that is what is stored.
      if (value.length() == 0)
         return "";

      // Convert the value into a character array.
      array = value.toCharArray();

      // Create a new StringBuffer to store the quoted value.
      quoted = new StringBuffer(array.length);

      // Loop round the characters in the array.
      for (i = 0; i < array.length; i++) {

         // Escape any quotation marks.
         if (array[i] == '"') {
            quoted.append("\\\"");
            continue;
         }

         // Escape any additional backslash characters.
         if (array[i] == '\\') {
            quoted.append("\\\\");
            continue;
         }

         // Otherwise append the character without an escape.
         quoted.append(array[i]);
      }

      // Return a string version of the buffer.
      return quoted.toString();
   }

   /**
    * Returns a string version of the attribute and its value.
    * 
    * @param name
    *           the name of the attribute.
    */
   public String toString(String name) {

      String value; // The value of the attribute.

      // Return an empty string if the name is null.
      if (name == null)
         return "";

      // Return an empty string if the attribute is not defined.
      if (!exists(name))
         return "";

      // Get a quoted version of the value.
      value = getQuoted(name);

      // If the value is null return the attribute name by itself.
      if (value == null)
         return name;

      // Otherwise return the complete string.
      if (value.length() > 0)
         return name + "=\"" + value + '"';
      else
         return name;
   }

   /**
    * Returns a string version of the attribute list.
    */
   public String toString() {

      StringBuffer buffer; // Stores the string version of the list.
      Enumeration nameList; // Stores a list of attribute names.
      String name; // Name of the current attribute.
      String attr; // String version of a single attribute.

      // Create a new StringBuffer.
      buffer = new StringBuffer();

      // Get a list of all of the attribute names.
      nameList = names();

      while (nameList.hasMoreElements()) {

         // Get the next attribute name from the list.
         name = (String) nameList.nextElement();

         // Get the string version of the attribute.
         attr = toString(name);

         // Add it to the buffer.
         buffer.append(attr);

         // Add whitespace if there are more attributes.
         if (nameList.hasMoreElements())
            buffer.append(' ');
      }

      // Return a string version of the buffer.
      return buffer.toString();
   }
}