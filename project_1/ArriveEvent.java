class ArriveEvent extends Event {
    ArriveEvent(Customer customer) {
        super(customer, LOW_PRIORITY);
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        if (serverBalancer.isThereAServerFreeAt(customer.getArrivalTime())) {
            return serverBalancer.serve(customer);
        } else if (serverBalancer.isThereServerWithSpaceInQueue()) {
            return serverBalancer.addToQueue(customer);
        }
        return new Pair<Event, ServerBalancer>(new LeaveEvent(this.customer), serverBalancer);
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
