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

    ServerEvent handleCustomer(Customer customer) {
        if (this.isFreeAt(customer.getArrivalTime())) {
            ServeEvent serveEvent = new ServeEvent(customer, this);
            Server server = this.serve(customer);
            return new ServerEvent(server, serveEvent);
        } else {
            LeaveEvent leaveEvent = new LeaveEvent(customer);
            return new ServerEvent(this, leaveEvent);
        }
    }

    Server serve(Customer customer) {
        return new Server(this.name, customer.getLeaveTime());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
