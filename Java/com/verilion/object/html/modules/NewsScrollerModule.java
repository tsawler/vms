//------------------------------------------------------------------------------
//Copyright (c) 2006 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2006-11-16
//Revisions
//------------------------------------------------------------------------------
//$Log: NewsScrollerModule.java,v $
//Revision 1.1.2.4  2009/07/22 16:29:32  tcs
//*** empty log message ***
//
//Revision 1.1.2.3  2008/03/07 12:03:23  tcs
//*** empty log message ***
//
//Revision 1.1.2.2  2007/06/11 15:37:05  tcs
//*** empty log message ***
//
//Revision 1.1.2.1  2007/03/30 17:19:49  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package com.verilion.object.html.modules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.verilion.database.JspTemplate;
import com.verilion.object.Errors;
import com.verilion.utility.TextUtils;

/**
 * Displays scrolling news
 * 
 * @author tsawler
 * 
 */
public class NewsScrollerModule implements ModuleInterface {

	/**
	 * displays scrolling news headlines
	 * 
	 * @return String - fully formatted news headlines as html/javascript
	 * @throws Exception
	 */
	public String getHtmlOutput(Connection conn, HttpSession session,
			HttpServletRequest request) throws Exception {

		String theText = "";
		String query = "";
		String theTemplate = "";

		JspTemplate myJspTemplate = new JspTemplate();
		myJspTemplate.setConn(conn);
		theTemplate = myJspTemplate.returnJspTemplateIdForPageName("News");

		try {
			query = "select news_id, news_title, news_start_date, news_teaser_text from news where news_id in "
					+ "(select news_id from news_ticker) and news_active_yn = 'y' and news_end_date >= NOW() order by news_start_date desc";
			ResultSet rs = null;
			Statement st = null;
			st = conn.createStatement();
			rs = st.executeQuery(query);
			theText = "<script type=\"text/javascript\">\n"
					+ "/* <![CDATA[ */\n" + "var delay = 5000;\n"
					+ "var maxsteps=30;\n" + "var stepdelay=40\n"
					+ "var startcolor= new Array(255,255,255);\n"
					+ "var endcolor=new Array(0,0,0);\n"
					+ "var fcontent=new Array();\n" + "begintag='<div>';\n";
			int i = 0;
			while (rs.next()) {
				String theBody = rs.getString("news_teaser_text");
				String theTitle = rs.getString("news_title");
				theBody = TextUtils.StripHtml(theBody);
				if (theBody.length() > 300) {
					theBody = theBody.substring(0, 249) + "...";
				}
				theTitle = TextUtils.StripHtml(theTitle);
				theText += "fcontent[" + i + "]=\"<a href='/" + theTemplate
						+ "/jpage/1/p/News/a/newsitem/id/"
						+ rs.getInt("news_id") + "/content.do'>" + theTitle
						+ "</a><br /><br />" + theBody + "\"\n";
				i++;
			}
			rs.close();
			rs = null;
			st.close();
			st = null;
			theText += "closetag='</div>';\n"
					+ "var fwidth='150px';\n"
					+ "var fheight='150px';\n"
					+ "var fadelinks=1;\n"
					+ "var ie4=document.all&&!document.getElementById;\n"
					+ "var DOM2=document.getElementById;\n"
					+ "var faderdelay=0;\n"
					+ "var index=0;\n"
					+ "function changecontent(){\n"
					+ "if (index>=fcontent.length)\n"
					+ "index=0\n"
					+ "if (DOM2){\n"
					+ "document.getElementById(\"fscroller\").style.color=\"rgb(\"+startcolor[0]+\", \"+startcolor[1]+\", \"+startcolor[2]+\")\"\n"
					+ "document.getElementById(\"fscroller\").innerHTML=begintag+fcontent[index]+closetag\n"
					+ "if (fadelinks)\n"
					+ "linkcolorchange(1);\n"
					+ "colorfade(1, 15);\n"
					+ "}\n"
					+ "else if (ie4)\n"
					+ "document.all.fscroller.innerHTML=begintag+fcontent[index]+closetag;\n"
					+ "index++\n"
					+ "}\n"
					+ "function linkcolorchange(step){\n"
					+ "var obj=document.getElementById(\"fscroller\").getElementsByTagName(\"A\");\n"
					+ "if (obj.length>0){\n"
					+ "for (i=0;i<obj.length;i++)\n"
					+ "obj[i].style.color=getstepcolor(step);\n"
					+ "}\n"
					+ "}\n"
					+ "var fadecounter;\n"
					+ "function colorfade(step) {\n"
					+ "if(step<=maxsteps) {\n"
					+ "document.getElementById(\"fscroller\").style.color=getstepcolor(step);\n"
					+ "if (fadelinks)\n"
					+ "linkcolorchange(step);\n"
					+ "step++;\n"
					+ "fadecounter=setTimeout(\"colorfade(\"+step+\")\",stepdelay);\n"
					+ "}else{\n"
					+ "clearTimeout(fadecounter);\n"
					+ "document.getElementById(\"fscroller\").style.color=\"rgb(\"+endcolor[0]+\", \"+endcolor[1]+\", \"+endcolor[2]+\")\";\n"
					+ "setTimeout(\"changecontent()\", delay);\n"
					+ "}\n"
					+ "}\n"
					+ "function getstepcolor(step) {\n"
					+ "var diff\n"
					+ "var newcolor=new Array(3);\n"
					+ "for(var i=0;i<3;i++) {\n"
					+ "diff = (startcolor[i]-endcolor[i]);\n"
					+ "if(diff > 0) {\n"
					+ "newcolor[i] = startcolor[i]-(Math.round((diff/maxsteps))*step);\n"
					+ "} else {\n"
					+ "newcolor[i] = startcolor[i]+(Math.round((Math.abs(diff)/maxsteps))*step);\n"
					+ "}\n"
					+ "}\n"
					+ "return (\"rgb(\" + newcolor[0] + \", \" + newcolor[1] + \", \" + newcolor[2] + \")\");\n"
					+ "}\n"
					+ "if (ie4||DOM2)\n"
					+ "document.write('<div id=\"fscroller\" style=\"border:none;width:'+fwidth+';height:'+fheight+'\"></div>');\n"
					+ "if (window.addEventListener)\n"
					+ "window.addEventListener(\"load\", changecontent, false)\n"
					+ "else if (window.attachEvent)\n"
					+ "window.attachEvent(\"onload\", changecontent)\n"
					+ "else if (document.getElementById)\n"
					+ "window.onload=changecontent\n" + "/* ]]> */\n"
					+ "</script>\n";
		} catch (Exception e) {
			Errors.addError("NewsScrollerModule.getHtmlOutput:Exception:"
					+ e.toString());
		}
		// System.out.println("the text is " + theText);
		return theText;
	}
}