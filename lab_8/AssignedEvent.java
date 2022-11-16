abstract class AssignedEvent extends Event {
    protected final int serverNumber;
    protected final String serverName;

    AssignedEvent(Customer customer, int serverNumber, String serverName,
            boolean isTerminalEvent, int priority) {
        super(customer, isTerminalEvent, priority, 0.0);
        this.serverNumber = serverNumber;
        this.serverName = serverName;
    }

    AssignedEvent(Customer customer, int serverNumber, String serverName,
            boolean isTerminalEvent, int priority, double eventTime) {
        super(customer, isTerminalEvent, priority, eventTime);
        this.serverNumber = serverNumber;
        this.serverName = serverName;
    }

    AssignedEvent(Customer customer, int serverNumber, String serverName,
            boolean isTerminalEvent, int priority, double eventTime, boolean readyToExecute) {
        super(customer, isTerminalEvent, priority, eventTime, readyToExecute);
        this.serverName = serverName;
        this.serverNumber = serverNumber;
    }

    protected int getServerIndexNumber() {
        return this.serverNumber - 1;
    }

    protected int getServerNumber() {
        return this.serverNumber;
    }

    protected String getServerName() {
        return this.serverName;
    }
}
