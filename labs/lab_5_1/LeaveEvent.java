class LeaveEvent extends Event {

    LeaveEvent(Customer customer) {
        super(customer, HIGH_PRIORITY);
    }

    @Override
    public String toString() {
        return String.format("%s %s leaves",
                this.getCustomer().getArrivalTime(),
                this.getCustomer());
    }

    double getEventTime() {
        return this.getCustomer().getArrivalTime();
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        return new Pair<Event, ServerBalancer>(new TerminalEvent(customer), serverBalancer);
    }
}
