package ua.in.smartajva.singleTable;

/**
 * Transient class in hierarchy
 */
public abstract class CachedEntity {
    private final long createTime;

    protected CachedEntity() {
        this.createTime = System.currentTimeMillis();
    }

    public long getCachedAge() {
        return System.currentTimeMillis() - createTime;
    }
}
