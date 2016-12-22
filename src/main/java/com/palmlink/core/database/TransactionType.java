/**
 * 
 */
package com.palmlink.core.database;

/**
 * Transaction type to qualify the TransactionManager
 * 
 * @author Shihai.Fu
 * 
 */
public final class TransactionType {
    /**
     * DataSource Transaction
     */
    public static final String DATA_SOURCE = "dataSourceTransaction";

    /**
     * Hibernate Transaction
     */
    public static final String HIBERNATE = "hibernateTransaction";

    /**
     * JTA Transaction
     */

    public static final String JTA = "jtaTransaction";

}
