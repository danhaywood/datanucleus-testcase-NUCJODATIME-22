package com.danhaywood.datanucleus.test.jdo;

import java.util.List;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import org.apache.log4j.BasicConfigurator;
import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EntityWithLocalDateTest
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

    @Test
    public void persist_then_retrieve()
    {
        //
        // given
        //
        final String name = "fred";
        final LocalDate localDate = new LocalDate(2014, 1, 1);

        tx.begin();

        final EntityWithLocalDate anEntity = new EntityWithLocalDate();

        anEntity.setLocalDate(localDate);

        pm.makePersistent(anEntity);

        assertThat(anEntity.getLocalDate(), is(localDate));

        tx.commit();

        // log shows:
        // [main] DEBUG DataNucleus.Datastore.Native  - INSERT INTO PERSON (STARTDATE) VALUES (<3914-02-01>)

        // I think that LocalDate is being interpreted as a java.util.Date, because
        // java.util.Date(0,0,0) = 1-Jan-2014 and so java.util.Date(1,1,2014) = 1-Feb-3914


        //
        // when
        //
        tx.begin();

        final Query query = pm.newQuery(EntityWithLocalDate.class);

        List<EntityWithLocalDate> entities = (ForwardQueryResult) query.execute();

        EntityWithLocalDate anEntityRetrieved = entities.get(0);


        //
        // then
        //
        assertThat(anEntityRetrieved.getLocalDate(), is(localDate));

        //        Expected: is <2014-01-01>
        //            but: was <3914-02-01>

            tx.commit();
    }
}
