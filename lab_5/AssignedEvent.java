abstract class AssignedEvent extends Event {
    private final Server server;

    AssignedEvent(Customer customer, Server server, boolean isTerminalEvent, int priority) {
        super(customer, isTerminalEvent, priority);
        this.server = server;
    }

    protected Server getServer() {
        return this.server;
    }
}
