class ServeEvent extends Event {
    private final Server server;

    ServeEvent(Customer customer, Server server) {
        super(customer);
        this.server = server;
    }

    @Override
    public String toString() {
        return String.format("%s served by %s", this.getCustomer(), this.server);
    }
}
