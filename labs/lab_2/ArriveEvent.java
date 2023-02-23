class ArriveEvent extends Event {
    ArriveEvent(Customer customer, Server server) {
        super(customer, server);
    }

    Event getNextEvent() {
        if (this.server.isFreeAt(customer.getArrivalTime())) {
            Server server = this.server.serve(customer);
            return new ServeEvent(this.customer, server);
        }
        return new LeaveEvent(this.customer, server);
    }

    @Override
    public String toString() {
        return String.format("%s arrives", this.getCustomer());
    }
}
