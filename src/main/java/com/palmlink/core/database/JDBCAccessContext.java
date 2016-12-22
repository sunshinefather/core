package com.palmlink.core.database;

/**
 * JDBCAccess that hold the JDBCAccess instance
 * 
 * @author Shihai.Fu
 * 
 */
public class JDBCAccessContext {

    /**
     * hold the reference to the jdbcAccess
     */
    private JDBCAccess jdbcAccess;

    public void setJdbcAccess(JDBCAccess jdbcAccess) {
        this.jdbcAccess = jdbcAccess;
    }

    public JDBCAccess getJdbcAccess() {
        return jdbcAccess;
    }

}
