package com.palmlink.core.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.palmlink.core.util.StopWatch;

/**
 * Common jdbc operations encapsulation
 * 
 * @author Shihai.Fu
 * 
 */
public class JDBCAccess {

    private final Logger logger = LoggerFactory.getLogger(JDBCAccess.class);

    private JdbcTemplate jdbcTemplate;

    /**
     * find list
     * 
     * @param sql
     * @param rowMapper
     * @param params
     */
    public <T> List<T> find(String sql, RowMapper<T> rowMapper, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.query(sql, params, rowMapper);
        } finally {
            logger.debug("find, sql={}, params={}, elapsedTime={}", new Object[] { sql, params, watch.elapsedTime() });
        }
    }

    /**
     * find unique result, null will be returned if not exists
     * 
     * @param sql
     * @param rowMapper
     * @param params
     */
    public <T> T findUniqueResult(String sql, RowMapper<T> rowMapper, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            List<T> list = jdbcTemplate.query(sql, params, rowMapper);
            return list.isEmpty() ? null : list.get(0);
        } finally {
            logger.debug("findUniqueResult, sql={}, params={}, elapsedTime={}", new Object[] { sql, params, watch.elapsedTime() });
        }
    }

    /**
     * 
     * find Integer, if the record doesn't exist, an
     * IncorrectResultSizeDataAccessException will be threw
     * 
     * @param sql
     * @param params
     */
    public int findInteger(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForInt(sql, params);
        } finally {
            logger.debug("findInteger, sql={}, params={}, elapsedTime={}", new Object[] { sql, params, watch.elapsedTime() });
        }
    }

    /**
     * find String, if the record doesn't exist, an
     * IncorrectResultSizeDataAccessException will be threw
     * 
     * @param sql
     * @param params
     */
    public String findString(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.queryForObject(sql, params, new RowMapper<String>() {
                @Override
                public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
                    return resultSet.getString(1);
                }
            });
        } finally {
            logger.debug("findString, sql={}, params={}, elapsedTime={}", new Object[] { sql, params, watch.elapsedTime() });
        }
    }

    /**
     * execute sql
     * 
     * @param sql
     * @param params
     */
    public int execute(String sql, Object... params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.update(sql, params);
        } finally {
            logger.debug("execute, sql={}, params={}, elapsedTime={}", new Object[] { sql, params, watch.elapsedTime() });
        }
    }

    /**
     * batch execute sql
     * 
     * @param sql
     * @param params
     */
    public int[] batchExecute(String sql, List<Object[]> params) {
        StopWatch watch = new StopWatch();
        try {
            return jdbcTemplate.batchUpdate(sql, params);
        } finally {
            logger.debug("batchExecute, sql={}, params={}, elapsedTime={}", new Object[] { sql, params, watch.elapsedTime() });
        }
    }

    /**
     * build JdbcTemplate with DataSource
     * 
     * @param dataSource
     */
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * using static method instead of constructor to initialize object
     * 
     * @param dataSource
     * @return
     */
    public static JDBCAccess generate(DataSource dataSource) {
        JDBCAccess jdbcAccess = new JDBCAccess();
        jdbcAccess.setDataSource(dataSource);
        return jdbcAccess;
    }

    public JdbcTemplate jdbcTemplate() {
        return jdbcTemplate;
    }

}
