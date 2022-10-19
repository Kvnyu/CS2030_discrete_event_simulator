import java.util.function.Supplier;

import javax.xml.stream.events.StartElement;

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

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes) {
        this(serverNumber, maxQSize, restTimes, 0.0);
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes, double nextAvailableAt) {
        this(serverNumber, maxQSize, restTimes, 0, nextAvailableAt);
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes,
            int totalCustomersServed, double nextAvailableAt) {
        this(serverNumber, maxQSize, restTimes, totalCustomersServed, 0.0, nextAvailableAt);
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
                new ImList<Customer>());
    }

    Server(int serverNumber, int maxQSize, Supplier<Double> restTimes,
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
        // System.out.println(
        // String.format("Server number %d and next available at %f", this.serverNumber,
        // this.nextAvailableAt));
    }

    boolean isAvailableAt(double eventTime) {
        // System.out.println(String.format("Server %d next available at %f",
        // this.serverNumber, this.nextAvailableAt));
        return this.isAvailable && this.nextAvailableAt <= eventTime;
    }

    boolean hasSpaceInQueueAt(double eventTime) {
        // System.out.println(String.format("%d %d", this.maxQSize,
        // this.getQueueSize()));
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
        // TODO: Update the totalCustomersServed and totalCustomerWaitTime in the
        // DoneEvent
        double startServingTime;
        double currentCustomerWaitTime = eventTime - customer.getArrivalTime();
        double newTotalCustomerWaitTime = this.totalCustomerWaitTime + currentCustomerWaitTime;
        // System.out.println("customer " + customer.toString() + " " +
        // customer.getArrivalTime() + " " + eventTime);
        // System.out.println("server wait time: " + this.totalCustomerWaitTime);
        // System.out.println("new server wait time: " + newTotalCustomerWaitTime);
        if (serveFromQueue) {
            startServingTime = this.getNextAvailableAt();
        } else {
            startServingTime = customer.getArrivalTime();
        }
        // System.out.println("Server " + this.serverNumber + " started serving at " +
        // startServingTime);
        ImList<Customer> customers = this.customers;
        if (!serveFromQueue) {
            customers = this.customers.add(customer);
        }
        if (this.totalCustomersServed == 0) {
            return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                    this.totalCustomersServed, newTotalCustomerWaitTime,
                    false, customer.getArrivalTime() + serviceTime, customers);
        }

        // System.out.println("Server " + this.serverNumber + " finishes serving at " +
        // startServingTime + serviceTime);
        return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                this.totalCustomersServed, newTotalCustomerWaitTime,
                false, startServingTime + serviceTime, customers);
    }

    Server addCustomerToQueue(Customer customer) {
        ImList<Customer> customers = this.customers.add(customer);
        // System.out.println(customers);
        return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                this.totalCustomersServed, this.totalCustomerWaitTime,
                this.isAvailable, this.nextAvailableAt, customers);
    }

    double getNextAvailableAt() {
        return this.nextAvailableAt;
    }

    Server finishServing() {
        Customer finishedCustomer = this.customers.get(0);
        ImList<Customer> newCustomers = this.customers.remove(0);
        // double newTotalCustomerWaitTime = this.totalCustomerWaitTime
        // + (this.nextAvailableAt - finishedCustomer.getArrivalTime());
        int newTotalCustomersServed = this.totalCustomersServed + 1;
        // System.out.println(newCustomers);
        double restTime = this.restTimes.get();
        double newNextAvailableAt = restTime + this.nextAvailableAt;
        // System.out.println(
        // "Server will be resting for " + restTime + " and next available at " +
        // newNextAvailableAt);
        return new Server(this.serverNumber, this.maxQSize, this.restTimes,
                newTotalCustomersServed, this.totalCustomerWaitTime,
                true, newNextAvailableAt, newCustomers);
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

    @Override
    public String toString() {
        return String.format("%d: %s", this.serverNumber, this.customers.toString());
    }
}
