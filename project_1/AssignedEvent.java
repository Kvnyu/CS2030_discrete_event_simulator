abstract class AssignedEvent extends Event {
    protected final Server server;

    AssignedEvent(Customer customer, Server server, boolean hasNextEvent, int priority) {
        super(customer, hasNextEvent, priority);
        this.server = server;
    }

}
