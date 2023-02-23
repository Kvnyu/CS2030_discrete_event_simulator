class Server {
    private final String name;
    private final Double availableTime;
    private static final double THRESHOLD = 1E-15;

    Server(String name) {
        this(name, 0.0);
    }

    Server(String name, double availableTime) {
        this.name = name;
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
