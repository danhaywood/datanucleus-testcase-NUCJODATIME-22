package com.danhaywood.datanucleus.test.jdo;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import org.joda.time.LocalDate;

@PersistenceCapable(
        identityType = IdentityType.DATASTORE
)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
public class EntityWithLocalDate
{
    public EntityWithLocalDate() {
    }

    // //////////////////////////////////////

    private LocalDate localDate;

    @Column(allowsNull = "true")
    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

}
