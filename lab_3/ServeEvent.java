class ServeEvent extends NonTerminalEvent {
    ServeEvent(Customer customer, Server server) {
        super(customer, server);
    }

    @Override
    Event getNextEvent(Server server) {
        return new DoneEvent(customer, server);
    }

    @Override
    public String toString() {
        return String.format("%s %s served by %s",
                this.getCustomer().getArrivalTime(),
                this.getCustomer(),
                this.server);
    }

    double getEventTime() {
        return this.getCustomer().getArrivalTime();
    }
}
