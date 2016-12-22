package com.palmlink.core.database;

import com.palmlink.core.SpringTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

/**
 * @author Shihai.Fu
 */
public class JDBCAccessTest extends SpringTest {
    @Inject
    JDBCAccess jdbcAccess;

    @Before
    public void createTestTable() {
        jdbcAccess.execute("create table db_sql_test (value varchar(20))");
        jdbcAccess.execute("insert into db_sql_test (value) values (?)", "value");
    }

    @After
    public void dropTestTable() {
        jdbcAccess.execute("drop table db_sql_test");
    }

    @Test
    public void findStringBySQL() {
        String value = jdbcAccess.findString("select value from db_sql_test");

        Assert.assertEquals("value", value);
    }

    @Test
    public void findIntegerBySQL() {
        Integer count = jdbcAccess.findInteger("select count(*) from db_sql_test");

        Assert.assertEquals(1, (int) count);
    }
}
