class Server {
    private final String name;
    private final Double availableTime;
    private final int qmax;
    private final int qsize;
    private static final double THRESHOLD = 1E-15;

    Server(String name, int qmax) {
        this(name, qmax, 0);
    }

    Server(String name, int qmax, int qsize) {
        this(name, qmax, qsize, 0.0);
    }

    Server(String name, int qmax, int qsize, double availableTime) {
        this.name = name;
        this.qmax = qmax;
        this.qsize = qsize;
        this.availableTime = availableTime;
    }

    boolean isFreeAt(double arrivalTime) {
        return availableTime - arrivalTime < THRESHOLD;
    }

    Server handleCustomer(Customer customer) {
        if (this.isFreeAt(customer.getArrivalTime())) {
            return this.serve(customer);
        }
        return this;
    }

    Server serve(Customer customer) {
        return new Server(this.name, this.qmax, this.qsize, customer.getDoneTime());
    }

    Server serveFromQueue(Customer customer) {
        return new Server(this.name, this.qmax, this.qsize + 1, this.availableTime + customer.getServiceTime());
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

    Server addCustomerToQueue(double serviceTime) {
        return new Server(this.name, this.qmax, this.qsize + 1, this.availableTime + serviceTime);
    }

    Server removeCustomerFromQueue() {
        return new Server(this.name, this.qmax, this.qsize - 1, this.availableTime);
    }

    @Override
    public String toString() {
        return this.name;
    }
}