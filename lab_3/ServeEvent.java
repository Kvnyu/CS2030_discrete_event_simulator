class ServeEvent extends NonTerminalEvent {
    ServeEvent(Customer customer, Server server) {
        super(customer, server);
    }

    @Override
    Event getNextEvent() {
        return new DoneEvent(customer, server);
    }

    @Override
    public String toString() {
        return String.format("%s served by %s", this.getCustomer(), this.server);
    }
}
