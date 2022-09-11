import java.util.Optional;

class Event {
    protected final Customer customer;
    protected final Server server;
    protected final boolean hasNextEvent;
    protected final Priority priority;

    enum Priority {
        High,
        Low
    }

    Event(Customer customer, Server server) {
        this(customer, server, false, Priority.High);
    }

    Event(Customer customer, Server server, boolean hasNextEvent) {
        this(customer, server, hasNextEvent, Priority.High);
    }

    Event(Customer customer, Server server, boolean hasNextEvent, Priority priority) {
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

    protected Priority getPriority() {
        return this.priority;
    }
}
