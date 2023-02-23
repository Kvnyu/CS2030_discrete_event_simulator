import java.util.function.Supplier;

class Server {
    private static final double THRESHOLD = 1E-15;
    private final ImList<Customer> customers;
    private final int maxQSize;
    private final int serverNumber;
    private final int totalCustomersServed;
    private final double totalCustomerWaitTime;
    private final boolean isAvailable;
    private final double nextAvailableAt;
    private final Supplier<Double> restTimes;
    private final boolean isSC;

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes) {
        this(serverNumber, maxQSize, restTimes, 0.0);
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes, double nextAvailableAt) {
        this(serverNumber, maxQSize, restTimes, 0, nextAvailableAt);
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double nextAvailableAt) {
        this(serverNumber, maxQSize, restTimes,
                totalCustomersServed, 0.0, nextAvailableAt);
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes, int totalCustomersServed,
            double totalCustomerWaitTime, double nextAvailableAt) {
        this(serverNumber, maxQSize, restTimes, totalCustomersServed,
                0.0, true, nextAvailableAt);
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt) {
        this(serverNumber, maxQSize, restTimes, totalCustomersServed,
                totalCustomerWaitTime, isAvailable, nextAvailableAt,
                new ImList<Customer>(), false);
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double totalCustomerWaitTime,
            boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers, boolean isSC) {
        this.nextAvailableAt = nextAvailableAt;
        this.serverNumber = serverNumber;
        this.maxQSize = maxQSize;
        this.totalCustomersServed = totalCustomersServed;
        this.totalCustomerWaitTime = totalCustomerWaitTime;
        this.isAvailable = isAvailable;
        this.customers = customers;
        this.restTimes = restTimes;
        this.isSC = isSC;
    }

    boolean isAvailableAt(double eventTime) {
        return this.isAvailable && this.nextAvailableAt <= eventTime;
    }

    boolean hasSpaceInQueueAt(double eventTime) {
        if (this.nextAvailableAt <= eventTime) {
            return this.maxQSize - this.getQueueSize() > 0;
        } else {
            return this.maxQSize - this.getQueueSize() + 1 > 0;
        }
    }

    int getQueueSize() {
        return this.customers.size();
    }

    int getServerIndexNumber() {
        return this.serverNumber - 1;
    }

    int getServerNumber() {
        return this.serverNumber;
    }

    Server startServing(Customer customer, double serviceTime,
            boolean serveFromQueue, double eventTime) {
        double startServingTime;
        double currentCustomerWaitTime = eventTime - customer.getArrivalTime();
        double newTotalCustomerWaitTime = this.totalCustomerWaitTime + currentCustomerWaitTime;
        startServingTime = eventTime;
        ImList<Customer> customers = this.customers;
        if (!serveFromQueue) {
            customers = this.customers.add(customer);
        }
        if (this.totalCustomersServed == 0) {
            return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                    this.totalCustomersServed, newTotalCustomerWaitTime,
                    false, customer.getArrivalTime() + serviceTime, customers, this.isSC);
        }

        return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                this.totalCustomersServed, newTotalCustomerWaitTime,
                false, startServingTime + serviceTime, customers, this.isSC);
    }

    Server popScQueue() {
        return this;
    }

    Server addCustomerToQueue(Customer customer) {
        ImList<Customer> customers = this.customers.add(customer);
        return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                this.totalCustomersServed, this.totalCustomerWaitTime,
                this.isAvailable, this.nextAvailableAt, customers, this.isSC);
    }

    double getNextAvailableAt() {
        return this.nextAvailableAt;
    }

    Pair<Server, Double> finishServing() {
        if (!this.isSC) {
            double restTime = this.restTimes.get();
            double newNextAvailableAt = restTime + this.nextAvailableAt;

            return new Pair<Server, Double>(new Server(this.serverNumber,
                    this.maxQSize, this.restTimes,
                    this.totalCustomersServed, this.totalCustomerWaitTime,
                    false, newNextAvailableAt, this.customers,
                    this.isSC), restTime);

        }
        return new Pair<Server, Double>(this, 0.0);
    }

    boolean hasCustomersInQueue() {
        return !this.customers.isEmpty();
    }

    int getTotalCustomersServed() {
        return this.totalCustomersServed;
    }

    double getTotalCustomerWaitTime() {
        return this.totalCustomerWaitTime;
    }

    Customer getNextCustomerInQueue() {
        return this.customers.get(0);
    }

    ImList<Customer> getCustomers() {
        return this.customers;
    }

    Server returnFromRest() {
        ImList<Customer> newCustomers = this.customers.remove(0);
        int newTotalCustomersServed = this.totalCustomersServed + 1;

        return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                newTotalCustomersServed,
                this.totalCustomerWaitTime, true,
                this.nextAvailableAt, newCustomers, this.isSC);
    }

    static Server of(int serverNumber, int maxQSize, Supplier<Double> restTimes, boolean isSC) {
        return new Server(serverNumber, maxQSize, restTimes,
                0, 0.0, true,
                0.0, new ImList<Customer>(), isSC);
    }

    Server getIfAvailable(double eventTime) {
        return this;
    }

    Server updateServer(Server server) {
        return this;
    }

    int getMaxQSize() {
        return this.maxQSize;
    }

    Supplier<Double> getRestTimes() {
        return this.restTimes;
    }

    boolean isAvailable() {
        return this.isAvailable;
    }

    Server get(int serverNumber) {
        return this;
    }

    boolean getIsSc() {
        return this.isSC;
    }

    @Override
    public String toString() {
        return this.isSC
                ? String.format("self-check %d", this.serverNumber - 1)
                : String.format("%d", this.serverNumber);
    }
}
