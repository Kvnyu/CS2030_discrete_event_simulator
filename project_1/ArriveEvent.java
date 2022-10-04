class ArriveEvent extends Event {
    ArriveEvent(Customer customer) {
        super(customer, LOW_PRIORITY);
    }

    @Override
    Event getNextEvent() {
        if (server.isFreeAt(customer.getArrivalTime())) {
            server = server.serve(customer);
            return new ServeEvent(customer, server);
        }
        return new LeaveEvent(this.customer, server);
    }

    @Override
    public String toString() {
        return String.format("%s %s arrives", this.getCustomer().getFormattedArrivalTime(),
                this.getCustomer());
    }

    double getEventTime() {
        return this.getCustomer().getArrivalTime();
    }
}
