package com.palmlink.core.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

/**
 * RowMapper encapsulation to enable column cache
 * 
 * @author Shihai.Fu
 * 
 * @param <T>
 */
public abstract class RowMapper<T> implements ParameterizedRowMapper<T> {

    /**
     * the column - column_index cache
     */
    private Map<String, Integer> columnIndexes;

    /**
     * JDBC doesn't support map to not-existed column, we cache column indexes
     * to make row mapper can be reused while multi columns getting
     * 
     * @param resultSet
     * @param columnName
     * @throws SQLException
     */
    protected int findColumn(ResultSet resultSet, String columnName) throws SQLException {
        if (columnIndexes == null) {
            buildIndexes(resultSet);
        }
        Integer index = columnIndexes.get(columnName);
        if (index == null)
            return -1;
        return index;
    }

    /**
     * init the cache
     * 
     * @param resultSet
     * @throws SQLException
     */
    private void buildIndexes(ResultSet resultSet) throws SQLException {
        columnIndexes = new HashMap<String, Integer>();
        ResultSetMetaData meta = resultSet.getMetaData();
        int count = meta.getColumnCount();
        for (int i = 1; i < count + 1; i++) {
            String column = meta.getColumnName(i);
            columnIndexes.put(column.toLowerCase(), i);
        }
    }

    /**
     * get String with column name
     * 
     * @param resultSet
     * @param column
     * @throws SQLException
     */
    protected String getString(ResultSet resultSet, String column) throws SQLException {
        return getString(resultSet, column, null);
    }

    /**
     * get String with column name, default value will be returned if column not
     * exists
     * 
     * @param resultSet
     * @param column
     * @param defaultValue
     * @throws SQLException
     */
    protected String getString(ResultSet resultSet, String column, String defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0)
            return resultSet.getString(columnIndex);
        return defaultValue;
    }

    /**
     * get Date with column name
     * 
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException
     */
    protected Date getDate(ResultSet resultSet, String column) throws SQLException {
        return getDate(resultSet, column, null);
    }

    /**
     * get Date with column name, default value will be returned if column not
     * exists
     * 
     * @param resultSet
     * @param column
     * @return
     * @throws SQLException
     */
    protected Date getDate(ResultSet resultSet, String column, Date defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0)
            return resultSet.getTimestamp(columnIndex);
        return defaultValue;
    }

    /**
     * get Integer with column name exists
     * 
     * @param resultSet
     * @param column
     * @throws SQLException
     */
    protected int getInt(ResultSet resultSet, String column) throws SQLException {
        return getInt(resultSet, column, 0);
    }

    /**
     * get Integer with column name, default value will be returned if column
     * not exists
     * 
     * @param resultSet
     * @param column
     * @param defaultValue
     * @return
     * @throws SQLException
     */
    protected int getInt(ResultSet resultSet, String column, int defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0)
            return resultSet.getInt(columnIndex);
        return defaultValue;
    }

    /**
     * get Double with column name
     * 
     * @param resultSet
     * @param column
     * @throws SQLException
     */
    protected double getDouble(ResultSet resultSet, String column) throws SQLException {
        return getDouble(resultSet, column, 0);
    }

    /**
     * get Double with column name, default value will be returned if column not
     * exists
     * 
     * @param resultSet
     * @param column
     * @param defaultValue
     * @return
     * @throws SQLException
     */
    protected double getDouble(ResultSet resultSet, String column, double defaultValue) throws SQLException {
        int columnIndex = findColumn(resultSet, column);
        if (columnIndex > 0)
            return resultSet.getDouble(columnIndex);
        return defaultValue;
    }

    public abstract T mapRow(ResultSet resultSet, int rowNum) throws SQLException;
}
