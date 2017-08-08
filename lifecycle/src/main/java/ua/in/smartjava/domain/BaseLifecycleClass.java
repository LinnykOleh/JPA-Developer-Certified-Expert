package ua.in.smartjava.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MappedSuperclass
public abstract class BaseLifecycleClass {

    @Id
    @GeneratedValue
    @Getter
    private long id;

    @PrePersist
    private void prePersist() {
        log.info("PrePersist {}, {}", this.getClass().getSimpleName(), this.id);
    }

    @PostPersist
    void postPersist() {
        log.info("PostPersist {}, {}", this.getClass().getSimpleName(), this.id);
    }

    @PreUpdate
    public final void preUpdate() {
        log.info("PreUpdate {}, {}", this.getClass().getSimpleName(), this.id);
    }

    @PostUpdate
    public void postUpdate() {
        log.info("PostUpdate {}, {}", this.getClass().getSimpleName(), this.id);
    }

    @PreRemove
    public void preRemove() {
        log.info("PreRemove {}, {}", this.getClass().getSimpleName(), this.id);
    }

    @PostRemove
    public void postRemove() {
        log.info("PostRemove {}, {}", this.getClass().getSimpleName(), this.id);
    }

    @PostLoad
    public void load() {
        log.info("Load {}, {}", this.getClass().getSimpleName(), this.id);
    }
}
