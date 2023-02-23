class ServeEvent extends Event {
    ServeEvent(Customer customer, Server server) {
        super(customer, server);
    }

    @Override
    public String toString() {
        return String.format("%s served by %s", this.getCustomer(), this.server);
    }
}
