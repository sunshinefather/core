package com.palmlink.core.database;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

/**
 * Factory object which held one master JDBCAccess and several slave
 * JDBCAccess(es), and determine which slave to return from multi slaves
 * 
 * @author Shihai.Fu
 * 
 */
public class JDBCAccessFactory {

    /**
     * the master jdbcAccess
     */
    private final JDBCAccess masterAccess = new JDBCAccess();

    /**
     * the slave jdbcAccesses
     */
    private final List<JDBCAccess> slaveAccesses = new ArrayList<JDBCAccess>();

    /**
     * use to record the times of invoking slaveAccess, and balance it to multi
     * slaves with mod
     */
    private final AtomicInteger identity = new AtomicInteger();

    /**
     * construct the JDBCAccessFactory
     * 
     * @param masterDataSource
     * @param slaveDatasources
     */
    public JDBCAccessFactory(DataSource masterDataSource, DataSource... slaveDatasources) {
        initialize(masterDataSource, slaveDatasources);
    }

    /**
     * @param masterDataSource
     * @param slaveDatasources
     */
    private void initialize(DataSource masterDataSource, DataSource... slaveDatasources) {
        slaveAccesses.clear();
        masterAccess.setDataSource(masterDataSource);
        if (null != slaveDatasources && slaveDatasources.length > 0) {
            for (int i = 0; i < slaveDatasources.length; i++)
                slaveAccesses.add(JDBCAccess.generate(slaveDatasources[i]));
        }
    }

    public JDBCAccess getMasterJDBCAccess() {
        return masterAccess;
    }

    /**
     * return jdbcAccess using mod to balance the slave pressures
     * 
     * @return
     */
    public JDBCAccess getSlaveJDBCAccess() {
        return slaveAccesses.get(mod(slaveAccesses.size()));
    }

    private int mod(int length) {
        return identity.getAndIncrement() % length;
    }

}
