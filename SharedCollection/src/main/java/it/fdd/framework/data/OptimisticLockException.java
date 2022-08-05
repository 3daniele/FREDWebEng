package it.fdd.framework.data;

public class OptimisticLockException extends DataException {

    private DataItem item;

    public OptimisticLockException(DataItem item) {
        super("Version mismatch (optimistic locking) for instance " + item.getKey() + " of class " + item.getClass().getCanonicalName());
        this.item = item;
    }

    /**
     * @return the item
     */
    public DataItem getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(DataItem item) {
        this.item = item;
    }
}
