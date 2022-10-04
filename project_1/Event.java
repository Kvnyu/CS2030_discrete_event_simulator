abstract class Event {
    protected final Customer customer;
    protected final int priority;
    protected final boolean isTerminalEvent;
    protected static final int LOW_PRIORITY = 0;
    protected static final int HIGH_PRIORITY = 1;

    Event(Customer customer) {
        this(customer, false, HIGH_PRIORITY);
    }

    Event(Customer customer, boolean isTerminalEvent) {
        this(customer, isTerminalEvent, HIGH_PRIORITY);
    }

    Event(Customer customer, int priority) {
        this(customer, false, priority);
    }

    Event(Customer customer, boolean isTerminalEvent, int priority) {
        this.priority = priority;
        this.customer = customer;
        this.isTerminalEvent = isTerminalEvent;
    }

    abstract double getEventTime();

    abstract Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer);

    protected Customer getCustomer() {
        return this.customer;
    }

    protected boolean isTerminalEvent() {
        return this.isTerminalEvent;
    }

    protected int getPriority() {
        return this.priority;
    }
}
