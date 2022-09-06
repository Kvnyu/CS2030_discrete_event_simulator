class Event {
    protected final Customer customer;
    protected final Server server;

    Event(Customer customer, Server server) {
        this.customer = customer;
        this.server = server;
    }

    protected Customer getCustomer() {
        return customer;
    }

    protected Server getServer() {
        return server;
    }
}
