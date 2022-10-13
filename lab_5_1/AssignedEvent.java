abstract class AssignedEvent extends Event {
    protected final int serverNumber;

    AssignedEvent(Customer customer, int serverNumber, boolean isTerminalEvent, int priority) {
        this(customer, serverNumber, isTerminalEvent, priority, 0.0);
    }

    AssignedEvent(Customer customer, int serverNumber, boolean isTerminalEvent, int priority, double eventTime) {
        super(customer, isTerminalEvent, priority, eventTime);
        this.serverNumber = serverNumber;
    }

    protected int getServerIndexNumber() {
        return this.serverNumber - 1;
    }

    protected int getServerNumber() {
        return this.serverNumber;
    }
}
