class Server {
    private static final double THRESHOLD = 1E-15;
    private final ImList<Customer> customers;
    private final int maxQSize;
    private final int serverNumber;
    private final int totalCustomersServed;
    private final double totalCustomerWaitTime;
    private final boolean isAvailable;
    private final double nextAvailableAt;

    Server(int serverNumber, int maxQSize) {
        this(serverNumber, maxQSize, 0.0);
    }

    Server(int serverNumber, int maxQSize, double nextAvailableAt) {
        this(serverNumber, maxQSize, 0, nextAvailableAt);
    }

    Server(int serverNumber, int maxQSize, int customersServed, double nextAvailableAt) {
        this(serverNumber, maxQSize, customersServed, 0.0, nextAvailableAt);
    }

    Server(int serverNumber, int maxQSize, int customersServed,
            double totalCustomerWaitTime, double nextAvailableAt) {
        this(serverNumber, maxQSize, customersServed, 0.0, true, nextAvailableAt);
    }

    Server(int serverNumber, int maxQSize,
            int totalCustomersServed, double totalCustomerWaitTime, boolean isAvailable, double nextAvailableAt) {
        this(serverNumber, maxQSize, totalCustomersServed, totalCustomerWaitTime, isAvailable, nextAvailableAt,
                new ImList<Customer>());
    }

    Server(int serverNumber, int maxQSize,
            int totalCustomersServed, double totalCustomerWaitTime, boolean isAvailable, double nextAvailableAt,
            ImList<Customer> customers) {
        this.nextAvailableAt = nextAvailableAt;
        this.serverNumber = serverNumber;
        this.maxQSize = maxQSize;
        this.totalCustomersServed = totalCustomersServed;
        this.totalCustomerWaitTime = totalCustomerWaitTime;
        this.isAvailable = isAvailable;
        this.customers = customers;
    }

    boolean isAvailable() {
        return this.isAvailable;
    }

    boolean hasSpaceInQueue() {
        return this.maxQSize - this.getQueueSize() + 1 > 0;
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

    Server startServing(Customer customer, double serviceTime) {
        // TODO: Update the totalCustomersServed and totalCustomerWaitTime in the
        // DoneEvent
        ImList<Customer> customers = this.customers.add(customer);
        if (this.totalCustomersServed == 0) {
            return new Server(this.serverNumber, this.maxQSize, this.totalCustomersServed, this.totalCustomerWaitTime,
                    false, customer.getArrivalTime() + serviceTime, customers);
        }
        return new Server(this.serverNumber, this.maxQSize, this.totalCustomersServed, this.totalCustomerWaitTime,
                false, this.nextAvailableAt + serviceTime, customers);
    }

    Server addCustomerToQueue(Customer customer) {
        ImList<Customer> customers = this.customers.add(customer);
        // System.out.println(customers);
        return new Server(this.serverNumber, this.maxQSize, this.totalCustomersServed, this.totalCustomerWaitTime,
                this.isAvailable, this.nextAvailableAt, customers);
    }

    double getNextAvailableAt() {
        return this.nextAvailableAt;
    }

    Server finishServing() {
        Customer finishedCustomer = this.customers.get(0);
        ImList<Customer> newCustomers = this.customers.remove(0);
        double newTotalCustomerWaitTime = this.totalCustomerWaitTime
                + (this.nextAvailableAt - finishedCustomer.getArrivalTime());
        int newTotalCustomersServed = this.totalCustomersServed + 1;
        // System.out.println(newCustomers);
        return new Server(this.serverNumber, this.maxQSize, newTotalCustomersServed, newTotalCustomerWaitTime,
                true, this.nextAvailableAt, newCustomers);
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
}
