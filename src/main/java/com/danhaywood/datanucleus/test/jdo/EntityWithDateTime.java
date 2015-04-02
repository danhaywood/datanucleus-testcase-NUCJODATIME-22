package com.danhaywood.datanucleus.test.jdo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import org.joda.time.DateTime;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
public class EntityWithDateTime
{
    public EntityWithDateTime() {
    }

    // //////////////////////////////////////

    private DateTime dateTime;

    @Column(allowsNull = "true")
    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

}
