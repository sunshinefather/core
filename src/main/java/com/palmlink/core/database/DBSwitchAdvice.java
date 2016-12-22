package com.palmlink.core.database;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.transaction.annotation.Transactional;

import com.palmlink.core.database.Switch.SwitchType;

/**
 * Switch DataSource depend on @Transactional annotation, if @Switch was
 * specified, change to the corresponding JDBCAccess
 * 
 * @author Shihai.Fu
 * 
 */

public class DBSwitchAdvice implements MethodBeforeAdvice {

    private JDBCAccessContext jdbcAccessContext;

    private JDBCAccessFactory factory;

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        Transactional transactional = method.getAnnotation(Transactional.class);
        Switch zwitch = method.getAnnotation(Switch.class);

        if (null != transactional) {
            jdbcAccessContext.setJdbcAccess(factory.getMasterJDBCAccess());
        } else {
            if (null != zwitch) {
                SwitchType switchType = zwitch.value();
                switch (switchType) {
                    case MASTER:
                        jdbcAccessContext.setJdbcAccess(factory.getMasterJDBCAccess());
                        break;

                    case SLAVE:
                        jdbcAccessContext.setJdbcAccess(factory.getSlaveJDBCAccess());
                        break;

                    default:
                        break;
                }
            } else {
                jdbcAccessContext.setJdbcAccess(factory.getSlaveJDBCAccess());
            }
        }
    }

    @Inject
    public void setJdbcAccessContext(JDBCAccessContext jdbcAccessContext) {
        this.jdbcAccessContext = jdbcAccessContext;
    }

    @Inject
    public void setFactory(JDBCAccessFactory factory) {
        this.factory = factory;
    }

}
