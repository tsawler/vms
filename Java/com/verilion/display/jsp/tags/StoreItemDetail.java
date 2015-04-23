//------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2005-11-29
//Revisions
//------------------------------------------------------------------------------
//$Log: StoreItemDetail.java,v $
//Revision 1.1.2.3  2005/12/03 03:20:54  tcs
//in progress
//
//Revision 1.1.2.2  2005/12/01 18:39:18  tcs
//Crumbtrail completed; removed unused parms
//
//Revision 1.1.2.1  2005/11/30 19:46:19  tcs
//Initial entry (in progress)
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.Category;
import com.verilion.database.ItemCategory;
import com.verilion.database.objects.CategoryObject;
import com.verilion.object.html.store.Store;

/**
 * TagLib to display item (browse or detail).
 * 
 * @author tsawler
 * 
 */
public class StoreItemDetail extends BaseTag {

	private static final long serialVersionUID = -3612208152592664317L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.Tag#doStartTag()
	 */
	public int doStartTag() throws JspException {

		int item_id = 0;
		int cat_id = 0;
		XDisconnectedRowSet drs = new XDisconnectedRowSet();

		HashMap hm = (HashMap) pc.getSession().getAttribute("pHm");

		if (hm.get("itemdetail") != null) {
			item_id = Integer.parseInt((String) hm.get("itemdetail"));
		}
		if (hm.get("cat") != null) {
			cat_id = Integer.parseInt(hm.get("cat").toString());
		}

		if (item_id > 0) {
			// displaying item detail

			// build the top crumbtrail over the table of items
			String theTable = "<span class=\"content\"><a href=\""
					+ "/store/jpage/1/p/Home/content.do\">Store</a> &gt; ";

			try {
				Category myCategory = new Category();
				myCategory.setConn(conn);
				myCategory.setCategory_id(cat_id);

				List myList = new ArrayList();
				myList = myCategory.GetAllParentsForCatId();

				Iterator itr = myList.iterator();

				while (itr.hasNext()) {
					CategoryObject co = new CategoryObject();
					co = (CategoryObject) itr.next();
					theTable += "<a href=\"/store/jpage/1/p/Home/cat/"
							+ co.getCategory_id() + "/content.do\">"
							+ co.getCategory_name() + "</a> &gt; ";
				}

				myCategory.setCustomWhere("category_id = '" + cat_id + "'");
				drs = myCategory.GetRows();
				if (drs.next()) {
					theTable += "<a href=\"/store/jpage/1/p/Home/cat/"
							+ drs.getInt("category_id") + "/content.do\">"
							+ drs.getString("category_name") + "</a>";
				}
				drs.close();
				drs = null;
				theTable += "</span><br /><br />";

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				String theMenu = Store.GetItemDetail(conn, item_id, cat_id,
						pc.getSession());
				pc.getOut().write(theTable + theMenu);
			} catch (Exception e) {
				throw new JspTagException("An IOException occurred.");
			}
		} else {
			// displaying item browse level

			// first get all items for this category
			ItemCategory myItemCategory = new ItemCategory();
			myItemCategory.setConn(conn);
			myItemCategory.setCategory_id(cat_id);

			if (cat_id > 0) {
				// we are displaying some specific category

				// build the top crumbtrail over the table of items
				String theTable = "<span class=\"content\"><a href=\""
						+ "/store/jpage/1/p/Home/content.do\">Store</a> &gt; ";

				try {
					Category myCategory = new Category();
					myCategory.setConn(conn);
					myCategory.setCategory_id(cat_id);

					List myList = new ArrayList();
					myList = myCategory.GetAllParentsForCatId();

					Iterator itr = myList.iterator();

					while (itr.hasNext()) {
						CategoryObject co = new CategoryObject();
						co = (CategoryObject) itr.next();
						theTable += "<a href=\"/store/jpage/1/p/Home/cat/"
								+ co.getCategory_id() + "/content.do\">"
								+ co.getCategory_name() + "</a> &gt; ";
					}

					myCategory.setCustomWhere("category_id = '" + cat_id + "'");
					drs = myCategory.GetRows();
					if (drs.next()) {
						theTable += "<a href=\"/store/jpage/1/p/Home/cat/"
								+ drs.getInt("category_id") + "/content.do\">"
								+ drs.getString("category_name") + "</a>";
					}
					drs.close();
					drs = null;
					theTable += "</span><br /><br />";

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

				myItemCategory.setCategory_id(cat_id);
				try {
					drs = myItemCategory.GetAllItemsForCategory();
					boolean hasItems = false;
					while (drs.next()) {
						hasItems = true;
						theTable += Store.GetItemDetail(conn,
								drs.getInt("item_id"), cat_id, pc.getSession());
					}
					if (!hasItems) {
						theTable += "No items in this category.";
					}
					pc.getOut().write(theTable);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
		return SKIP_BODY;
	}
}