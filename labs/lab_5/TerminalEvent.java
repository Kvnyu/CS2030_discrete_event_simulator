class TerminalEvent extends Event {
    TerminalEvent(Customer customer) {
        super(customer, true);
    }

    @Override
    double getEventTime() {
        return -1;
    }

    @Override
    Pair<Event, ServerBalancer> getNextEvent(ServerBalancer serverBalancer) {
        return new Pair<Event, ServerBalancer>(new TerminalEvent(this.customer), serverBalancer);
    }
}
