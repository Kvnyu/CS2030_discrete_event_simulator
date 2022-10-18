abstract class Event {
    protected final Customer customer;
    protected final int priority;
    protected final boolean isTerminalEvent;
    protected final double eventTime;
    protected final boolean readyToExecute;
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
        this(customer, isTerminalEvent, priority, 0.0, true);
    }

    Event(Customer customer, boolean isTerminalEvent, int priority, double eventTime) {
        this(customer, isTerminalEvent, priority, eventTime, true);
    }

    Event(Customer customer, boolean isTerminalEvent, int priority,
            double eventTime, boolean readyToExecute) {
        this.priority = priority;
        this.customer = customer;
        this.isTerminalEvent = isTerminalEvent;
        this.eventTime = eventTime;
        this.readyToExecute = readyToExecute;
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

    void maybePrintEvent() {
        if (this.readyToExecute && !this.isTerminalEvent) {
            System.out.println(this);
        }
    }

    boolean isReadyToExecute() {
        return this.readyToExecute;
    }
}