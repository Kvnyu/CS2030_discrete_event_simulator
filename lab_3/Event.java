import java.util.Optional;

class Event {
    protected final Customer customer;
    protected final Server server;
    protected final boolean hasNextEvent;
    protected final int priority;
    static final int LOW_PRIORITY = 0;
    static final int HIGH_PRIORITY = 1;

    Event(Customer customer, Server server) {
        this(customer, server, false, HIGH_PRIORITY);
    }

    Event(Customer customer, Server server, boolean hasNextEvent) {
        this(customer, server, hasNextEvent, HIGH_PRIORITY);
    }

    Event(Customer customer, Server server, boolean hasNextEvent, int priority) {
        this.priority = priority;
        this.customer = customer;
        this.server = server;
        this.hasNextEvent = hasNextEvent;
    }

    protected Customer getCustomer() {
        return this.customer;
    }

    protected Server getServer() {
        return this.server;
    }

    protected boolean hasNextEvent() {
        return this.hasNextEvent;
    }

    Optional<Event> getNextEvent() {
        return Optional.empty();
    }

    protected int getPriority() {
        return this.priority;
    }
}
