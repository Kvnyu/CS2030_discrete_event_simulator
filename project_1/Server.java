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
        this(name, qmax, 0.0);
    }

    Server(String name, int qmax, int qsize, double availableTime) {
        this.name = name;
        this.qmax = qmax;
        this.qsize = qmax;
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
        return new Server(this.name, customer.getDoneTime());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
