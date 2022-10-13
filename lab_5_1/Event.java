abstract class Event {
    protected final Customer customer;
    protected final int priority;
    protected final boolean isTerminalEvent;
    protected final double eventTime;
    protected static final int LOW_PRIORITY = 0;
    protected static final int MID_PRIORITY = 1;
    protected static final int HIGH_PRIORITY = 2;

    Event(Customer customer) {
        this(customer, false, LOW_PRIORITY);
    }

    Event(Customer customer, boolean isTerminalEvent) {
        this(customer, isTerminalEvent, LOW_PRIORITY);
    }

    Event(Customer customer, int priority) {
        this(customer, false, priority);
    }

    Event(Customer customer, boolean isTerminalEvent, int priority) {
        this(customer, isTerminalEvent, priority, 0.0);
    }

    Event(Customer customer, boolean isTerminalEvent, int priority, double eventTime) {
        this.priority = priority;
        this.customer = customer;
        this.isTerminalEvent = isTerminalEvent;
        this.eventTime = eventTime;
    }

    abstract Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer);

    double getEventTime() {
        return this.eventTime;
    }

    protected Customer getCustomer() {
        return this.customer;
    }

    protected boolean isTerminalEvent() {
        return this.isTerminalEvent;
    }

    protected int getPriority() {
        return this.priority;
    }

    String getFormattedEventTime() {
        return String.format("%.3f", this.eventTime);
    }
}
