//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-12-02
//Revisions
//------------------------------------------------------------------------------
//$Log: StoreShoppingCartTag.java,v $
//Revision 1.1.2.1.2.1  2006/09/06 15:00:50  tcs
//Added Java 5 specific tags for type safety and warning suppression
//
//Revision 1.1.2.1  2005/12/03 03:21:06  tcs
//in progress
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.sql.Connection;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DbBean;
import com.verilion.database.Item;

/**
 * TagLib to display menu for shopping.
 * 
 * @author tsawler
 * 
 */
public class StoreShoppingCartTag extends BaseTag {

	private static final long serialVersionUID = -1471796768882025764L;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		try {
			HashMap hm = new HashMap();
			HashMap hmCart = new HashMap();

			if (pc.getSession().getAttribute("hmCart") != null) {
				hmCart = (HashMap) pc.getSession().getAttribute("hmCart");
			}

			if (pc.getSession().getAttribute("pHm") != null) {
				hm = (HashMap) pc.getSession().getAttribute("pHm");
				if (hm.get("add") != null) {
					// a request to add an item to the cart has been made
					int item_id = Integer.parseInt(hm.get("itemdetail")
							.toString());
					// check to see if it's in the cart already
					if (hmCart.get(item_id + "") != null) {
						int itemCount = Integer.parseInt(hmCart.get(
								item_id + "").toString());
						itemCount++;
						hmCart.put(item_id + "", itemCount + "");
						pc.getSession().setAttribute("hmCart", hmCart);
					} else {
						hmCart.put(item_id + "", "1");
						pc.getSession().setAttribute("hmCart", hmCart);
					}
				}
				if (hm.get("remove") != null) {
					// a request to remove an item from the cart is received
					int item_id = Integer.parseInt(hm.get("itemdetail")
							.toString());
					if (hmCart.get(item_id + "") != null) {
						int itemCount = Integer.parseInt(hmCart.get(
								item_id + "").toString());
						itemCount--;
						if (itemCount > 0) {
							hmCart.put(item_id + "", itemCount + "");
						} else {
							hmCart.remove(item_id + "");
						}
						pc.getSession().setAttribute("hmCart", hmCart);
					} else {
						hmCart.put(item_id + "", "1");
						pc.getSession().setAttribute("hmCart", hmCart);
					}
				}
			}
			String theMenu = "<table class=\"newsbox\" style=\"text-align: left;\">"
					+ "<tr style=\"width: 100%;\"><td colspan=\"2\" class=\"moduletitle\">Shopping Cart</td></tr>";

			// check for cart contents
			if (hmCart.size() > 0) {
				Connection conn = DbBean.getDbConnection();
				Item myItem = new Item();
				myItem.setConn(conn);
				XDisconnectedRowSet drs = new XDisconnectedRowSet();
				float totalPrice = 0.0f;
				NumberFormat fmt = NumberFormat.getCurrencyInstance();
				for (Iterator iter = hmCart.entrySet().iterator(); iter
						.hasNext();) {
					Map.Entry entry = (Map.Entry) iter.next();
					String key = (String) entry.getKey();
					String value = (String) entry.getValue();
					myItem.setItem_id(Integer.parseInt(key));
					drs = myItem.GetItemDetail();
					if (drs.next()) {
						theMenu += "<tr><td>" + drs.getString("item_name")
								+ " (" + value + ")</td></tr>";
						totalPrice = totalPrice + drs.getFloat("item_price")
								* Float.parseFloat(value);
					}
					drs.close();
					drs = null;
				}
				theMenu += "<tr><td><br />Total in cart: "
						+ fmt.format(totalPrice) + "</td></tr>";
				DbBean.closeDbConnection(conn);
			} else {
				theMenu += "<tr><td><br />Your cart is empty</td></tr>";
			}
			theMenu += "</table>";
			pc.getOut().write(theMenu);
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
			e.printStackTrace();
			throw new JspTagException("An IOException occurred.");
		}
		return SKIP_BODY;
	}
}