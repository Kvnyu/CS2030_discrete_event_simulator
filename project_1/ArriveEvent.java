class ArriveEvent extends Event {
    ArriveEvent(Customer customer) {
        super(customer, LOW_PRIORITY);
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        if (serverBalancer.isThereAServerFreeAt(customer.getArrivalTime())) {
            Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer.serve(customer);
            return new Pair<Event, ServerBalancer>(
                    new ServeEvent(customer, serverWithBalancer.first(),
                            customer.getArrivalTime(), false),
                    serverWithBalancer.second());
        } else if (serverBalancer.isThereServerWithSpaceInQueue()) {
            Pair<Server, ServerBalancer> serverWithBalancer = serverBalancer.addToQueue(customer);
            return new Pair<Event, ServerBalancer>(new WaitEvent(
                    customer, serverWithBalancer.first(),
                    serverWithBalancer.first().getAvailableTime() - customer.getServiceTime()),
                    serverWithBalancer.second());
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
