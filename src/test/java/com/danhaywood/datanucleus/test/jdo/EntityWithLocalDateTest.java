package com.danhaywood.datanucleus.test.jdo;

import java.util.List;
import javax.jdo.Query;
import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import org.joda.time.LocalDate;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EntityWithLocalDateTest extends AbstractEntityTest
{

    @Test
    public void persist_then_retrieve()
    {
        //
        // given
        //
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
