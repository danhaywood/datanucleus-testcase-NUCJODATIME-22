package com.danhaywood.datanucleus.test.jdo;

import java.util.List;
import javax.jdo.Query;
import org.datanucleus.store.rdbms.query.ForwardQueryResult;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EntityWithDateTimeTest extends AbstractEntityTest
{

    @Test
    public void persist_then_retrieve_for_utc() {

        final DateTime dateTime = new DateTime(2014, 1,1,10,45,15,123, DateTimeZone.UTC);

        persist_then_retrieve(dateTime);
    }

    @Test
    public void persist_then_retrieve_for_default() {

        final DateTime dateTime = new DateTime(2014, 1,1,10,45,15,123, DateTimeZone.getDefault());

        persist_then_retrieve(dateTime);
    }

    @Test
    public void persist_then_retrieve_with_offset() {

        final DateTime dateTime = new DateTime(2014, 1,1,10,45,15,123, DateTimeZone.forOffsetHours(2));

        persist_then_retrieve(dateTime);
    }

    private void persist_then_retrieve(final DateTime dateTime) {
        tx.begin();

        final EntityWithDateTime anEntity = new EntityWithDateTime();

        anEntity.setDateTime(dateTime);

        pm.makePersistent(anEntity);

        assertThat(anEntity.getDateTime(), is(dateTime));

        tx.commit();


        //
        // when
        //
        tx.begin();

        final Query query = pm.newQuery(EntityWithDateTime.class);

        List<EntityWithDateTime> entities = (ForwardQueryResult) query.execute();

        EntityWithDateTime anEntityRetrieved = entities.get(0);


        //
        // then
        //
        assertThat(anEntityRetrieved.getDateTime() , is(equalTo(dateTime)));

        // loses the timezone offset

        tx.commit();
    }

}
