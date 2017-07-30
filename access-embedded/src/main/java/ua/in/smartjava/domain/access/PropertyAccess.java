package ua.in.smartjava.domain.access;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * create table PropertyAccess (
 *      id bigint not null,
 *      PHONE varchar(255),
 *      primary key (id))
 */
@Entity
@Access(AccessType.FIELD)
public class PropertyAccess {
    public static final String AREA_CODE = "+380";
    @Id
    private long id;

    @Transient
    private transient String phone;


    public long getId() {
        return id;
    }

    /*
        This method is called to read the value that will be stored in DB
    */
    @Access(AccessType.PROPERTY)
    @Column(name = "PHONE")
    public String getPhoneFromDb() {
        if (phone.length() == 13) {
            return phone;
        } else {
            return AREA_CODE + phone;
        }
    }

    /*
        This setter method must follow convention to access defined by getter property phoneFromDb.
        Otherwise org.hibernate.PropertyNotFoundException will be thrown.
     */
    public void setPhoneFromDb(String phone) {
        if (phone.startsWith(AREA_CODE)) {
            this.phone = phone.substring(3);
        } else {
            this.phone = phone;
        }
    }
}