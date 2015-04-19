//------------------------------------------------------------------------------
//Copyright (c) 2003-2004 Verilion Inc.
//------------------------------------------------------------------------------
//Created on 2004-07-08
//Revisions
//------------------------------------------------------------------------------
//$Log: ArchivePageRoutines.java,v $
//Revision 1.2  2004/09/17 14:06:12  tcs
//Corrected typo in column names
//
//Revision 1.1  2004/07/08 18:15:30  tcs
//Added a few attributes
//
//------------------------------------------------------------------------------
package com.verilion.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.verilion.object.Errors;

/**
 * Archive Page related routines
 * <P>
 * July 08  2004
 * <P>
 * 
 * @author tsawler
 *  
 */
public class ArchivePageRoutines {

    private String archive_page_name = "";
    private int archive_page_id = 0;
    private String archive_page_title = "";
    private String archive_page_contents = "";
    private int template_id = 0;
    private int ct_language_id = 0;
    private Statement st = null;
    private ResultSet rs = null;
    private Connection conn = null;
    private String template_contents = "";
    private int ct_access_level_id = 0;
    private String archive_page_active_yn = "n";

    public ArchivePageRoutines() {
        super();
    }

    /**
     * Gets archive page contents, template, and other info. Results
     * are set, and retreived using assocated getter.
     * 
     * @throws SQLException
     * @throws Exception
     */
    public void GetArchivePageInfo() throws SQLException, Exception {
        try {
            String query = "select "
                    + "a.archive_page_id, "
                    + "a.archive_page_title, "
                    + "a.archive_page_content, "
                    + "a.archive_page_active_yn, "
                    + "a.ct_access_level_id, "
                    + "t.template_contents, "
                    + "ct_access_level_id, "
                    + "parent_id "
                    + "from "
                    + "archive_page as a, "
                    + "template as t "
                    + "where "
                    + "a.archive_page_name = '"
                    + archive_page_name
                    + "' "
                    + "and "
                    + "t.template_id = a.template_id";

            st = conn.createStatement();
            rs = st.executeQuery(query);

            while (rs.next()) {
                archive_page_id = rs.getInt("archive_page_id");
                archive_page_title = rs.getString("archive_page_title");
                archive_page_contents = rs.getString("archive_page_content");
                template_contents = rs.getString("template_contents");
                archive_page_active_yn = rs.getString("archive_page_active_yn");
                ct_access_level_id = rs.getInt("ct_access_level_id");
            }

            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            Errors
                    .addError("ArchivePageRoutines:GetArchivePageInfo:SQLException:"
                            + e.toString());
        } catch (Exception e) {
            e.printStackTrace();
            Errors.addError("ArchivePageRoutines:GetArchivePageInfo:Exception:"
                    + e.toString());
        } finally {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (st != null) {
                st.close();
                st = null;
            }
        }

    }

    /**
     * @return Returns the archive_page_active_yn.
     */
    public String getArchive_page_active_yn() {
        return archive_page_active_yn;
    }

    /**
     * @param archive_page_active_yn
     *            The archive_page_active_yn to set.
     */
    public void setArchive_page_active_yn(String archive_page_active_yn) {
        this.archive_page_active_yn = archive_page_active_yn;
    }

    /**
     * @return Returns the archive_page_contents.
     */
    public String getArchive_page_contents() {
        return archive_page_contents;
    }

    /**
     * @param archive_page_contents
     *            The archive_page_contents to set.
     */
    public void setArchive_page_contents(String archive_page_contents) {
        this.archive_page_contents = archive_page_contents;
    }

    /**
     * @return Returns the archive_page_name.
     */
    public String getArchive_page_name() {
        return archive_page_name;
    }

    /**
     * @param archive_page_name
     *            The archive_page_name to set.
     */
    public void setArchive_page_name(String archive_page_name) {
        this.archive_page_name = archive_page_name;
    }

    /**
     * @return Returns the archive_page_title.
     */
    public String getArchive_page_title() {
        return archive_page_title;
    }

    /**
     * @param archive_page_title
     *            The archive_page_title to set.
     */
    public void setArchive_page_title(String archive_page_title) {
        this.archive_page_title = archive_page_title;
    }

    /**
     * @return Returns the conn.
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn
     *            The conn to set.
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return Returns the ct_access_level_id.
     */
    public int getCt_access_level_id() {
        return ct_access_level_id;
    }

    /**
     * @param ct_access_level_id
     *            The ct_access_level_id to set.
     */
    public void setCt_access_level_id(int ct_access_level_id) {
        this.ct_access_level_id = ct_access_level_id;
    }

    /**
     * @return Returns the ct_language_id.
     */
    public int getCt_language_id() {
        return ct_language_id;
    }

    /**
     * @param ct_language_id
     *            The ct_language_id to set.
     */
    public void setCt_language_id(int ct_language_id) {
        this.ct_language_id = ct_language_id;
    }

    /**
     * @return Returns the template_contents.
     */
    public String getTemplate_contents() {
        return template_contents;
    }

    /**
     * @param template_contents
     *            The template_contents to set.
     */
    public void setTemplate_contents(String template_contents) {
        this.template_contents = template_contents;
    }
    /**
     * @return Returns the archive_page_id.
     */
    public int getArchive_page_id() {
        return archive_page_id;
    }
    /**
     * @param archive_page_id The archive_page_id to set.
     */
    public void setArchive_page_id(int archive_page_id) {
        this.archive_page_id = archive_page_id;
    }
    /**
     * @return Returns the template_id.
     */
    public int getTemplate_id() {
        return template_id;
    }
    /**
     * @param template_id The template_id to set.
     */
    public void setTemplate_id(int template_id) {
        this.template_id = template_id;
    }
}