abstract class Event {
    protected final Customer customer;
    protected final int priority;
    protected final boolean isTerminalEvent;
    protected final boolean canOutput;
    protected static final int LOW_PRIORITY = 0;
    protected static final int MID_PRIORITY = 1;
    protected static final int HIGH_PRIORITY = 2;

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
        this(customer, isTerminalEvent, priority, true);
    }

    Event(Customer customer, boolean isTerminalEvent, int priority, boolean canOutput) {
        this.priority = priority;
        this.customer = customer;
        this.isTerminalEvent = isTerminalEvent;
        this.canOutput = canOutput;
    }

    abstract double getEventTime();

    abstract Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer);

    boolean canExecute(ImList<Server> servers) {
        return true;
    }

    boolean canOutput() {
        return this.canOutput;
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
}
