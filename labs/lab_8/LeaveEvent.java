class LeaveEvent extends Event {

    LeaveEvent(Customer customer, double eventTime) {
        super(customer, false, HIGH_PRIORITY, eventTime);
    }

    @Override
    public String toString() {
        return String.format("%s %s leaves",
                this.getFormattedEventTime(),
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
