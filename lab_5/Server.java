class Server {
    private final int number;
    private final Double availableTime;
    private final int qmax;
    private final int qsize;
    private final int customersServed;
    private final boolean isAvailable;
    private final double totalCustomerWaitTime;
    private static final double THRESHOLD = 1E-15;

    Server(int number, int qmax) {
        this(number, qmax, 0);
    }

    Server(int number, int qmax, int qsize) {
        this(number, qmax, qsize, 0.0);
    }

    Server(int number, int qmax, int qsize, double availableTime) {
        this(number, qmax, qsize, availableTime, 0);
    }

    Server(int number, int qmax, int qsize, double availableTime, int customersServed) {
        this(number, qmax, qsize, availableTime, customersServed, 0.0);
    }

    Server(int number, int qmax, int qsize, double availableTime, int customersServed, double totalCustomerWaitTime) {
        this(number, qmax, qsize, availableTime, customersServed, 0.0, false);
    }

    Server(int number, int qmax, int qsize, double availableTime,
            int customersServed, double totalCustomerWaitTime, boolean isAvailable) {
        this.number = number;
        this.qmax = qmax;
        this.qsize = qsize;
        this.availableTime = availableTime;
        this.customersServed = customersServed;
        this.totalCustomerWaitTime = totalCustomerWaitTime;
        this.isAvailable = isAvailable;
    }

    boolean isFreeAt(double arrivalTime) {
        return availableTime - arrivalTime < THRESHOLD;
    }

    boolean isAvailable() {
        return this.isAvailable;
    }

    Server serve(Customer customer) {
        return new Server(this.number, this.qmax, this.qsize,
                customer.getDoneTime(), this.customersServed + 1,
                this.totalCustomerWaitTime, false);
    }

    Server serveFromQueue(Customer customer) {
        return new Server(
                this.number, this.qmax, this.qsize,
                this.availableTime,
                this.customersServed + 1, this.totalCustomerWaitTime, false);
    }

    boolean hasSpaceInQueue() {
        return this.qsize < this.qmax;
    }

    protected int getQSize() {
        return this.qsize;
    }

    protected double getAvailableTime() {
        return this.availableTime;
    }

    protected double getTotalCustomerWaitTime() {
        return this.totalCustomerWaitTime;
    }

    protected int getCustomersServed() {
        return this.customersServed;
    }

    // TODO: update the "next available time logic" as we don't knw when they'll
    // next be available now
    Server addCustomerToQueue(Customer customer) {
        return new Server(this.number, this.qmax, this.qsize + 1,
                this.availableTime,
                this.customersServed,
                this.totalCustomerWaitTime + (this.availableTime - customer.getArrivalTime()), this.isAvailable);
    }

    Server removeCustomerFromQueue(Customer customer) {
        int newQsize = Math.max(0, this.qsize - 1);
        return new Server(this.number, this.qmax,
                newQsize, this.availableTime + customer.getServiceTime(), this.customersServed,
                this.totalCustomerWaitTime, true);
    }

    Server finishServingCustomer(Customer customer) {
        return new Server(this.number, this.qmax,
                qsize, this.availableTime, this.customersServed,
                this.totalCustomerWaitTime, true);
    }

    @Override
    public String toString() {
        return Integer.toString(this.number);
    }

    int getServerNumber() {
        return this.number;
    }

    int getServerIndexNumber() {
        return this.number - 1;
    }
}