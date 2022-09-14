class ArriveEvent extends NonTerminalEvent {
    ArriveEvent(Customer customer, Server server) {
        super(customer, server, LOW_PRIORITY);
    }

    @Override
    Event getNextEvent() {
        if (this.server.isFreeAt(customer.getArrivalTime())) {
            Server server = this.server.serve(customer);
            return new ServeEvent(customer, server);
        }
        return new LeaveEvent(this.customer, server);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s arrives", this.getCustomer().getFormattedArrivalTime(),
                this.getCustomer().getArrivalTime(),
                this.getCustomer());
    }
}
