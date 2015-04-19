//------------------------------------------------------------------------------
//Copyright (c) 2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-11
//Revisions
//------------------------------------------------------------------------------
//$Log: RssModule.java,v $
//Revision 1.13.2.2.2.1.8.1  2006/12/22 15:19:24  tcs
//added <guid>
//
//Revision 1.13.2.2.2.1  2005/07/31 10:42:00  tcs
//Changed date format to one that validates for rss spec
//
//Revision 1.13.2.2  2005/02/25 19:01:25  tcs
//added generated year for comments
//
//Revision 1.13.2.1  2004/12/14 17:14:47  tcs
//Added explicit serial uid
//
//Revision 1.13  2004/11/05 02:13:29  tcs
//Changed working name to vMaintain
//
//Revision 1.12  2004/10/26 18:38:07  tcs
//Removed use of deprecated .toGMTString()
//
//Revision 1.11  2004/10/21 18:30:20  tcs
//Updated to remove old url writing scheme
//
//Revision 1.10  2004/10/07 11:37:38  tcs
//Removed check for length of item, and made feed have complete news article.
//
//Revision 1.9  2004/07/15 16:13:27  tcs
//Modified to use TextUtils to strip HTML
//
//Revision 1.8  2004/07/13 18:24:59  tcs
//Made the xml a bit prettier...
//
//Revision 1.7  2004/07/13 17:54:19  tcs
//Changed to rss 2.0 spec
//
//Revision 1.6  2004/07/12 18:54:10  tcs
//Added stripping of html tags
//
//Revision 1.5  2004/07/12 18:39:19  tcs
//Complete and functional
//
//Revision 1.4  2004/07/12 16:28:24  tcs
//Collapsed two methods
//
//Revision 1.3  2004/07/12 15:51:53  tcs
//Changed content type to application/xml
//
//Revision 1.2  2004/07/12 15:42:19  tcs
//Corrected xml format
//
//Revision 1.1  2004/07/11 20:07:06  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.xml.modules;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sourceforge.jxutil.sql.XDisconnectedRowSet;

import com.verilion.database.DbBean;
import com.verilion.database.NewsItem;
import com.verilion.database.SingletonObjects;
import com.verilion.object.Errors;
import com.verilion.utility.TextUtils;

/**
 * Produces RSS news feed
 * <P>
 * July 11, 2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class RssModule extends HttpServlet {

   private static final long serialVersionUID = 3256721796979177269L;

   public static SimpleDateFormat RFC822DATEFORMAT = new SimpleDateFormat(
         "EEE', 'dd' 'MMM' 'yyyy' 'HH:mm:ss' 'Z", Locale.US);

   public static String getDateAsRFC822String(Date date) {
      return RFC822DATEFORMAT.format(date);
   }

   public void doGet(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      this.doPost(request, response);
   }

   /*
    * (non-Javadoc)
    * 
    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
    *      javax.servlet.http.HttpServletResponse)
    */
   public void doPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      PrintWriter out;
      response.setContentType("application/xml");
      out = response.getWriter();
      try {
         DisplayResult(out, request, response);
      } catch (SQLException e) {
         e.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      }
      out.close();
   }

   public PrintWriter DisplayResult(
         PrintWriter out,
         HttpServletRequest request,
         HttpServletResponse response) throws SQLException, Exception {
      PrintWriter PWtemp;
      PWtemp = out;
      String theXML = "";
      String siteName = SingletonObjects.getInstance().getHost_name();
      String hostName = SingletonObjects.getInstance().getHost_name();
      String theNewsItems = "";
      String siteDescription = SingletonObjects.getInstance()
            .getSite_description();
      String tempString = "";
      NewsItem myNews = new NewsItem();
      XDisconnectedRowSet crs = new XDisconnectedRowSet();
      GregorianCalendar x = new GregorianCalendar();
      Date today = new Date();
      Connection conn = null;
      conn = DbBean.getDbConnection();

      int year = x.get(Calendar.YEAR);

      try {
         theXML = "<?xml version=\"1.0\" encoding=\"iso-8859-1\" standalone=\"yes\" ?>\n"
               + "<!-- vMaintain RSS Generator -->\n"
               + "<!-- Copyright (C) 2003-"
               + year
               + " Verilion Inc. http://www.verilion.com -->\n"
               + "<rss version=\"2.0\">\n"
               + "<channel>\n"
               + "<title>"
               + siteName
               + "</title>\n"
               + "<link>"
               + "http://"
               + hostName
               + "</link>\n"
               + "<description>"
               + siteDescription
               + "</description>\n"
               + "<language>en-us</language>\n"
               + "<lastBuildDate>"
               + RssModule.getDateAsRFC822String(today)
               + "</lastBuildDate>\n"
               + "<image>\n"
               + "<title>Powered by vCMS</title>\n"
               + "<url>"
               + "http://"
               + hostName
               + "/images/vlogorss.png</url>\n"
               + "<width>144</width>\n"
               + "<link>http://www.verilion.com</link>\n"
               + "</image>\n";

         myNews.setConn(conn);
         crs = myNews.getNewsHeadlinesForRss();
         theNewsItems = "";

         while (crs.next()) {
        	 String tempTitle = crs.getString("news_title");
        	 tempTitle = tempTitle.replaceAll("\n", " ");
        	 tempTitle = tempTitle.replaceAll("\r", " ");
        	 tempTitle = tempTitle.replaceAll("\'", "&#8217;");
        	 tempTitle = tempTitle.replaceAll("&", "&amp;");
            theNewsItems += "<item>\n"
                  + "<title>"
                  + tempTitle
                  + "</title>\n"
                  + "<link>"
                  + "http://"
                  + hostName
                  + "/News/newsitem/1/id/"
                  + crs.getInt("news_id")
                  + "/content.do</link>\n"
                  + "<guid>"
                  + "http://"
                  + hostName
                  + "/News/newsitem/1/id/"
                  + crs.getInt("news_id")
                  + "/content.do</guid>\n"
                  + "<description>";
            tempString = TextUtils.StripHtml(crs.getString("news_teaser_text"));
            tempString = tempString.replaceAll("\n", " ");
            tempString = tempString.replaceAll("\r", " ");
            tempString = tempString.replaceAll("\'", "&#8217;");
            tempString = tempString.replaceAll("&", "&amp;");
            theNewsItems += tempString;
            Date d = new Date();
            d = crs.getDate("news_start_date");
            theNewsItems += "...</description>\n"
                  + "<pubDate>"
                  + RssModule.getDateAsRFC822String(d)
                  + "</pubDate>\n"
                  + "</item>\n";
         }

         theXML = theXML + theNewsItems + "</channel>\n</rss>";
         crs.close();
         crs = null;
         conn.close();
         conn = null;

      } catch (Exception e) {
         e.printStackTrace();
         Errors.addError("RssModule.getXMLOutput:Exception:" + e.toString());
      } finally {
         if (crs != null) {
            crs.close();
            crs = null;
         }
         if (conn != null) {
            conn.close();
            conn = null;
         }
      }

      out.print(theXML);
      return PWtemp;
   }
}