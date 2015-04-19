//------------------------------------------------------------------------------
//Copyright (c) 2006 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-06-07
//Revisions
//------------------------------------------------------------------------------
//$Log: LatestNewsTag.java,v $
//Revision 1.1.2.2  2006/06/11 22:38:04  tcs
//Code cleanup
//
//Revision 1.1.2.1  2006/04/07 18:18:50  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.display.jsp.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.NewsItem;
import com.verilion.utility.TextUtils;

/**
 * TagLib to display latest news.
 * 
 * @author tsawler
 * 
 */
public class LatestNewsTag extends BaseTag {

   private static final long serialVersionUID = -4965726296134383924L;
   private int showTitle = 0;

   public int doStartTag() throws JspException {
      try {

         String theNewsItems = "";
         NewsItem myNews = new NewsItem();
         XDisconnectedRowSet crs = new XDisconnectedRowSet();

         try {
            if (showTitle > 0) {
            theNewsItems = "<table class=\"newsbox\" style=\"text-align: left;\">"
                  + "<tr style=\"width: 100%;\"><td colspan=\"2\" class=\"moduletitle\">"
                  + "Latest News</td></tr>";
            } else {
               theNewsItems = "<ul class=\"newslist\" style=\"text-align: left;\">";
            }

            myNews.setConn(conn);
            crs = myNews.getNewsHeadlines();

            if (crs.next()) {
               crs.previous();
               while (crs.next()) {
                  // read in the news items

                  String theTitleText = TextUtils.StripHtml(crs
                        .getString("news_teaser_text"));
                  String theNewsItem = crs.getString("news_teaser_text");
                  
                  if (theTitleText.length() > 100) {
                     theTitleText = theTitleText.substring(0, 99);
                  }
                  
                  if (theNewsItem.length() > 200) {
                     theNewsItem = theNewsItem.substring(0, 199) + "...";
                  }
                  theNewsItems += "<li><a class=\"moduletext\" "
                        + "title=\""
                        + theTitleText
                        + "..."
                        + "\" style=\"font-weight: bold;\" href=\"/public/jpage/1/p/News/a/newsitem/id/"
                        + crs.getInt("news_id")
                        + "/content.do\">"
                        + crs.getString("news_title")
                        + "</a><span class=\"content\">:&nbsp;"
                        + theNewsItem
                        + "</span></li>";

               }
            } else {
               // No news items to display
               theNewsItems += "<li>"
                     + "<span class=\"moduletext\">"
                     + "Currently unavailable"
                     + "</li>";
            }
            theNewsItems += "</ul>";

            crs.close();
            crs = null;
            pc.getOut().write(theNewsItems);
         } catch (Exception e) {
            e.printStackTrace();
         }
      } catch (Exception e) {
         throw new JspTagException("An IOException occurred.");
      }
      return SKIP_BODY;
   }

   /**
    * @return Returns the showTitle.
    */
   public int getShowTitle() {
      return showTitle;
   }

   /**
    * @param showTitle The showTitle to set.
    */
   public void setShowTitle(int showTitle) {
      this.showTitle = showTitle;
   }
}