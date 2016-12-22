/**
 * 
 */
package com.palmlink.core.database.ibatis.mapper;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.util.Assert;

import com.palmlink.core.SpringTest;

/**
 * @author Shihai.Fu
 * 
 */
public class IbatisTest extends SpringTest {
    @Inject
    private UserMapper userMapper;
    
    @Inject
    private DataSource dataSource;

    @Test
    public void testUserMapper() {
        Assert.notNull(dataSource);
        Assert.notNull(userMapper);
        User user = userMapper.getUser(24);
        System.out.println(user);
    }
}
