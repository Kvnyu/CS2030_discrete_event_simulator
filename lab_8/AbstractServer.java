import java.util.function.Supplier;

abstract class AbstractServer {
    private final ImList<Customer> customers;
    private final int maxQSize;
    private final int serverNumber;
    private final int totalCustomersServed;
    private final double totalCustomerWaitTime;
    private final boolean isAvailable;
    private final double nextAvailableAt;
    private final Supplier<Double> restTimes;
    private final boolean isSelfCheckServer;

    AbstractServer(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, 0.0);
    }

    AbstractServer(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, 0, nextAvailableAt);
    }

    AbstractServer(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes,
                totalCustomersServed, 0.0, nextAvailableAt);
    }

    AbstractServer(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed,
            double totalCustomerWaitTime, double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, totalCustomersServed,
                0.0, true, nextAvailableAt);
    }

    AbstractServer(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt) {
        this(isSelfCheckServer, serverNumber, maxQSize, restTimes, totalCustomersServed,
                totalCustomerWaitTime, isAvailable, nextAvailableAt,
                new ImList<Customer>());
    }

    AbstractServer(boolean isSelfCheckServer, int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers) {
        this.nextAvailableAt = nextAvailableAt;
        this.serverNumber = serverNumber;
        this.maxQSize = maxQSize;
        this.totalCustomersServed = totalCustomersServed;
        this.totalCustomerWaitTime = totalCustomerWaitTime;
        this.isAvailable = isAvailable;
        this.customers = customers;
        this.restTimes = restTimes;
        this.isSelfCheckServer = isSelfCheckServer;
    }

    abstract boolean isAvailableAt(double eventTime);

    abstract boolean hasSpaceInQueueAt(double eventTime);

    abstract AbstractServer returnFromRest(int serverNumber);

    abstract AbstractServer startServing(Customer customer, int serverNumber, double serviceTime,
            boolean serveFromQueue, double eventTime);

    abstract AbstractServer addCustomerToQueue(Customer customer);

    abstract Pair<AbstractServer, Double> finishServing();

    abstract Customer getNextCustomerInQueue();

    protected boolean hasCustomersInQueue() {
        return !this.customers.isEmpty();
    }

    protected int getTotalCustomersServed() {
        return this.totalCustomersServed;
    }

    protected double getTotalCustomerWaitTime() {
        return this.totalCustomerWaitTime;
    }

    protected ImList<Customer> getCustomers() {
        return this.customers;
    }

    double getNextAvailableAt() {
        return this.nextAvailableAt;
    };

    protected int getServerNumber() {
        return this.serverNumber;
    }

    protected int getQueueSize() {
        return this.customers.size();
    }

    protected int getServerIndexNumber() {
        return this.serverNumber - 1;
    }

    protected int getMaxQSize() {
        return this.maxQSize;
    }

    protected boolean isAvailable() {
        return this.isAvailable;
    }

    protected double nextAvailableAt() {
        return this.nextAvailableAt;
    }

    protected Supplier<Double> getRestTimes() {
        return this.restTimes;
    }

    String getServerName(int serverNumber) {
        if (this.isSelfCheckServer) {
            return String.format("self-check %d", this.serverNumber);
        }
        return Integer.toString(this.serverNumber);
    };

    AbstractServer getAvailableServer() {
        return this;
    }

    AbstractServer getServerWithSpaceInQueueAt(double time) {
        return this;
    }

    boolean isSelfCheckServer() {
        return this.isSelfCheckServer;
    }

    @Override
    public String toString() {
        return String.format("%d: %s", this.serverNumber, this.customers.toString());
    }
}
