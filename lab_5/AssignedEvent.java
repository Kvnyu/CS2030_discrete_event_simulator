abstract class AssignedEvent extends Event {
    private final Server server;

    AssignedEvent(Customer customer, Server server, boolean isTerminalEvent, int priority) {
        this(customer, server, isTerminalEvent, priority, true);
    }

    AssignedEvent(Customer customer, Server server, boolean isTerminalEvent, int priority, boolean canOutput) {
        super(customer, isTerminalEvent, priority, canOutput);
        this.server = server;

    }

    protected Server getServer() {
        return this.server;
    }
}
