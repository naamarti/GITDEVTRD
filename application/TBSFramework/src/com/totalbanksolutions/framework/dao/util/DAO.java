package com.totalbanksolutions.framework.dao.util;

import javax.sql.DataSource;

/**
 * Data Access Object (DAO) interface.   This is an empty interface
 * used to tag our DAO classes.  Common methods for each interface
 * could be added here.
 */
public interface DAO
{
    /**
     * Set datasource for DAO
     * @param ds is the datasource to connect to back end database(s)
     */
    public void setDataSource(DataSource ds);
}