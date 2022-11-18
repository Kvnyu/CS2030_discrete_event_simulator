abstract class AssignedEvent extends Event {
    protected final int serverNumber;
    protected final String serverName;

    AssignedEvent(String serverName, Customer customer, int serverNumber,
            boolean isTerminalEvent, int priority) {
        this(serverName, customer, serverNumber, isTerminalEvent, priority, 0.0);
    }

    AssignedEvent(String serverName, Customer customer, int serverNumber,
            boolean isTerminalEvent, int priority, double eventTime) {
        super(customer, isTerminalEvent, priority, eventTime);
        this.serverName = serverName;
        this.serverNumber = serverNumber;
    }

    AssignedEvent(String serverName, Customer customer, int serverNumber,
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
