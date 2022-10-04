abstract class Event {
    protected final Customer customer;
    protected final boolean hasNextEvent;
    protected final int priority;
    protected static final int LOW_PRIORITY = 0;
    protected static final int HIGH_PRIORITY = 1;

    Event(Customer customer) {
        this(customer, false, HIGH_PRIORITY);
    }

    Event(Customer customer, int priority) {
        this(customer, false, priority);
    }

    Event(Customer customer, boolean hasNextEvent) {
        this(customer, hasNextEvent, HIGH_PRIORITY);
    }

    Event(Customer customer, boolean hasNextEvent, int priority) {
        this.priority = priority;
        this.customer = customer;
        this.hasNextEvent = hasNextEvent;
    }

    abstract double getEventTime();

    protected Customer getCustomer() {
        return this.customer;
    }

    protected boolean hasNextEvent() {
        return this.hasNextEvent;
    }

    protected int getPriority() {
        return this.priority;
    }
}
