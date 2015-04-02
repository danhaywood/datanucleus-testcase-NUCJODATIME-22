package com.danhaywood.datanucleus.test.jdo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import org.apache.log4j.BasicConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

public class AbstractEntityTest
{

    PersistenceManagerFactory pmf;
    PersistenceManager pm;
    Transaction tx;

    @BeforeClass
    public static void setUpLogging() throws Exception {
        BasicConfigurator.configure();
    }

    @Before
    public void setUp() throws Exception {

        pmf = JDOHelper.getPersistenceManagerFactory("MyTest");

        pm = pmf.getPersistenceManager();
        tx = pm.currentTransaction();

    }

    @After
    public void tearDown() throws Exception {

        if(tx != null) {
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        if(pm != null) {
            pm.close();
        }
        if(pmf != null) {
            pmf.close();
        }
    }

}
