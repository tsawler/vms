// ------------------------------------------------------------------------------
//Copyright (c) 2005 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2003-02-23
//Revisions
//------------------------------------------------------------------------------
//$Log: CacheFilter.java,v $
//Revision 1.1.2.4.6.1  2005/11/22 03:38:24  tcs
//Added javadoc
//
//Revision 1.1.2.4  2005/02/24 13:18:04  tcs
//changed to get timeout from singleton
//
//Revision 1.1.2.3  2005/02/23 19:39:33  tcs
//hardcoded cache lifecycle. Going to have to put somewhere else,
//as the config init parameter doesn't work.
//
//Revision 1.1.2.2  2005/02/23 16:38:19  tcs
//Hardcoded timeout, as config init parameter seems not to work...
//
//Revision 1.1.2.1  2005/02/23 16:30:35  tcs
//Initial entry
//
//------------------------------------------------------------------------------
package filters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.verilion.database.SingletonObjects;

import filters.cachewrappers.CacheResponseWrapper;


/**
 * Servlet filter to cache pages. Pages that do have 'nocache' in the 
 * request URI are skipped.
 * 
 * @author Trevor
 *
 */
public class CacheFilter implements Filter {

   ServletContext sc;
   FilterConfig fc;
   public long cacheTimeout = 15 * 60 * 1000;

   public void doFilter(
         ServletRequest req,
         ServletResponse res,
         FilterChain chain) throws IOException, ServletException {
      HttpServletRequest request = (HttpServletRequest) req;
      HttpServletResponse response = (HttpServletResponse) res;

      // check uri
      String uri = request.getRequestURI();
      if (uri == null || uri.equals("") || uri.equals("/")) {
         chain.doFilter(request, response);
         return;
      }
      // check if was a resource that shouldn't be cached.
      String r = sc.getRealPath("");
      String path = fc.getInitParameter(uri);
      if (path != null && path.equals("nocache")) {
         chain.doFilter(request, response);
         return;
      }
      path = r + path;

      // customize to match parameters
      String id = request.getRequestURI() + request.getQueryString();
      
      // optionally append i18n sensitivity
      String localeSensitive = fc.getInitParameter("locale-sensitive");
      if (localeSensitive != null) {
         StringWriter ldata = new StringWriter();
         Enumeration locales = request.getLocales();
         while (locales.hasMoreElements()) {
            Locale locale = (Locale) locales.nextElement();
            ldata.write(locale.getISO3Language());
         }
         id = id + ldata.toString();
      }
      File tempDir = (File) sc.getAttribute("javax.servlet.context.tempdir");

      // get possible cache
      String temp = tempDir.getAbsolutePath();
      File file = new File(temp + id);

      // get current resource
      if (path == null) {
         path = sc.getRealPath(request.getRequestURI());
      }
      File current = new File(path);

      try {
         long now = Calendar.getInstance().getTimeInMillis();

         //set timestamp check
         if (!file.exists()
               || (file.exists() && current.lastModified() > file
                     .lastModified())
               || cacheTimeout < now - file.lastModified()) {
            String name = file.getAbsolutePath();

            name = name.substring(
                  0,
                  name.lastIndexOf(File.separatorChar) == -1 ? 0 : name
                        .lastIndexOf(File.separatorChar));
            new File(name).mkdirs();

            FileOutputStream fos = new FileOutputStream(file);
            CacheResponseWrapper wrappedResponse = new CacheResponseWrapper(
                  response, fos);
            chain.doFilter(req, wrappedResponse);

            fos.flush();
            fos.close();
         }
      } catch (ServletException e) {
         if (!file.exists()) {
            throw new ServletException(e);
         }
      } catch (IOException e) {
         if (!file.exists()) {
            throw e;
         }
      }

      FileInputStream fis = new FileInputStream(file);
      String mt = sc.getMimeType(request.getRequestURI());
      response.setContentType(mt);
      ServletOutputStream sos = res.getOutputStream();
      for (int i = fis.read(); i != -1; i = fis.read()) {
         sos.write((byte) i);
      }
   }

   public void init(FilterConfig filterConfig) {
      this.fc = filterConfig;
      // set the inital timeout
      String ct = SingletonObjects.getInstance().getCacheTimeout() + "";
      if (ct != null) {
         cacheTimeout = 60 * 1000 * Long.parseLong(ct);
      }
      // set reference to servlet context
      this.sc = filterConfig.getServletContext();
   }

   public void destroy() {
      this.sc = null;
      this.fc = null;
   }
}